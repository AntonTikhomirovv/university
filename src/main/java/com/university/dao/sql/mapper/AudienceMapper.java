package com.university.dao.sql.mapper;

import com.university.model.Audience;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AudienceMapper implements RowMapper<Audience> {

    @Override
    public Audience mapRow(ResultSet resultSet, int i) throws SQLException {
        Audience audience = new Audience();
        audience.setId(resultSet.getInt("id"));
        audience.setCampusNumber(resultSet.getInt("campus_number"));
        audience.setRoomNumber(resultSet.getInt("room_number"));
        return audience;
    }
}
