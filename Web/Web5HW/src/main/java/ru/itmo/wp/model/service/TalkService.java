package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class TalkService {
    private TalkRepository talkRepository = new TalkRepositoryImpl();
    private UserRepository userRepository = new UserRepositoryImpl();

    public List<Message> findAllMessage(String sourceUser, List<User> targetUsers) throws SQLException {
        return talkRepository.findAllMessage(sourceUser, targetUsers);
    }

    public void sendMessage(long fromUser, String toUser, String message) throws RepositoryException, SQLException {
        User user = userRepository.findByLoginOrEmail(toUser);
        if (user == null) {
            throw new RepositoryException("Can`t find user with this name");
        }
        talkRepository.save(fromUser, user.getId() , message);
    }
}
