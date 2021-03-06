package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    private User findCommon(String sql, String... parameters) throws SQLException {
        PreparedStatement statement = DatabaseUtils.getStatement(sql, parameters);
        ResultSet resultSet = statement.executeQuery();
        return toUser(statement.getMetaData(), resultSet);
    }

    @Override
    public User find(long id) throws SQLException {
        return findCommon("SELECT * FROM User WHERE id=?", String.valueOf(id));
    }

    @Override
    public User findByLoginOrEmail(String loginOrEmail) throws SQLException {
        return findCommon("SELECT * FROM User WHERE (login=? OR email=?)", loginOrEmail, loginOrEmail);
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) throws SQLException {
        return findCommon("SELECT * FROM User WHERE (login=? OR email=?) AND passwordSha=?", loginOrEmail, loginOrEmail, passwordSha);
    }

    @Override
    public List<User> findAll() {
        PreparedStatement statement = DatabaseUtils.getStatement("SELECT * FROM User ORDER BY id DESC");
        try (ResultSet resultSet = statement.executeQuery()) {
            List<User> users = new ArrayList<>();
            User user;
            while ((user = toUser(statement.getMetaData(), resultSet)) != null) {
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }

    @Override
    public void save(User user, String passwordSha) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `User` (`login`, `email`, `passwordSha`, `creationTime`) VALUES (?, ?, ?, NOW())", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getEmail());
                statement.setString(3, passwordSha);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save User.");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                        user.setCreationTime(find(user.getId()).getCreationTime());
                    } else {
                        throw new RepositoryException("Can't save User [no autogenerated fields].");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User.", e);
        }
    }

    @Override
    public int findCount(){
        return findAll().size();
    }
}
