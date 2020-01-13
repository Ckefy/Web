package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

import javax.sql.DataSource;
import java.sql.*;

public class EventRepositoryImpl implements EventRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void save(Event.type type, long userId) throws SQLException{
        PreparedStatement statement = DatabaseUtils.getStatement("INSERT INTO Event (type, userId, creationTime) VALUES (?, ?, NOW())",
                String.valueOf(type), String.valueOf(userId));
        statement.execute();
    }
}
