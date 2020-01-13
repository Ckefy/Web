package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TalkRepositoryImpl implements TalkRepository {
    private static final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void save(long sourceUserId, long targetUserId, String message) throws SQLException {
        PreparedStatement statement = DatabaseUtils.getStatement("INSERT INTO Talk (sourceUserId, targetUserId, text, creationTime) VALUES (?, ?, ?, NOW())",
                String.valueOf(sourceUserId), String.valueOf(targetUserId), message);
        statement.execute();
    }

    @Override
    public List<Message> findAllMessage(String nowSourceUser, List<User> users) throws SQLException {
        User user = userRepository.findByLoginOrEmail(nowSourceUser);
        PreparedStatement statement = DatabaseUtils.getStatement("SELECT * FROM Talk WHERE ((Talk.targetUserId = ?) OR (Talk.sourceUserId = ?)) ORDER BY creationTime ASC;",
                String.valueOf(user.getId()), String.valueOf(user.getId()));
        List<Message> messages = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData resultSetMetaData = statement.getMetaData();
        while (resultSet.next()) {
            messages.add(toMessage(resultSetMetaData, resultSet));
        }
        for (Message message : messages) {
            message.setSourceUser(userRepository.find(Long.parseLong(message.getSourceUser())).getLogin());
            message.setTargetUser(userRepository.find(Long.parseLong(message.getTargetUser())).getLogin());
        }
        return messages;
    }
    private static Message toMessage(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Message message = new Message();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                message.setId(resultSet.getLong(i));
            } else if ("sourceUserId".equalsIgnoreCase(columnName)) {
                message.setSourceUser(resultSet.getString(i));
            } else if ("targetUserId".equalsIgnoreCase(columnName)) {
                message.setTargetUser(resultSet.getString(i));
            } else if ("text".equalsIgnoreCase(columnName)) {
                message.setTextMessage(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                message.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Can`t find column " + columnName);
            }
        }
        return message;
    }
}
