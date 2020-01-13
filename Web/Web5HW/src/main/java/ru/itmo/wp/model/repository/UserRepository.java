package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    User find(long id) throws SQLException;
    User findByLoginOrEmail(String loginOrEmail) throws SQLException;
    User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) throws SQLException;
    List<User> findAll();
    void save(User user, String passwordSha);
    int findCount();
}
