package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface TalkRepository {
    void save (long sourceUserId, long targetUserId, String message) throws SQLException;
    List<Message> findAllMessage(String firstChatter, List<User> userList) throws SQLException;
}
