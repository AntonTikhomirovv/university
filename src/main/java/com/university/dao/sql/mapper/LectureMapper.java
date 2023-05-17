package com.university.dao.sql.mapper;

import com.university.model.Lecture;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LectureMapper implements RowMapper<Lecture> {
    @Override
    public Lecture mapRow(ResultSet resultSet, int i) throws SQLException {
        Lecture lecture = new Lecture();
        lecture.setId(resultSet.getInt("lecture_id"));
        lecture.setAudience(resultSet.getInt("audience_id"));
        lecture.setTeacher(resultSet.getInt("teacher_id"));
        lecture.setPairTime(resultSet.getInt("pair_time"));
        return lecture;
    }
}
