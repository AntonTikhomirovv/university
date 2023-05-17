package com.university.dao.sql.mapper;

import com.university.model.PairTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

@Component
public class PairTimeMapper implements RowMapper<PairTime> {
    @Override
    public PairTime mapRow(ResultSet resultSet, int i) throws SQLException {
        PairTime pairTime = new PairTime();
        pairTime.setPairNumber(resultSet.getInt("pair_number"));
        pairTime.setStartTime((resultSet.getTime("start_time").toLocalTime()));
        pairTime.setEndTime((resultSet.getTime("end_time").toLocalTime()));
        return pairTime;
    }
}
