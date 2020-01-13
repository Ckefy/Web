package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface EventRepository {
    void save (Event.type type, long userId) throws SQLException;
}
