package com.university.dao.sql;

import com.university.dao.GenericDAO;
import com.university.dao.sql.mapper.LectureMapper;
import com.university.exception.DAOException;
import com.university.model.Group;
import com.university.model.Lecture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class LectureDAO implements GenericDAO<Lecture> {

    private JdbcTemplate jdbcTemplate;
    private Properties properties;
    private LectureMapper mapper;
    private static final Logger LOG = LoggerFactory.getLogger(LectureDAO.class);

    @Autowired
    public LectureDAO(JdbcTemplate jdbcTemplate, Properties properties, LectureMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.properties = properties;
        this.mapper = mapper;
    }

    @Override
    public List<Lecture> getAll() {
        String sql = properties.getProperty("lecture_getAll");
        LOG.debug("Try to get all lectures");
        try {
            List<Lecture> lectures = jdbcTemplate.query(sql, mapper);
            LOG.debug("All lectures was gotten");
            return lectures;
        } catch (DataAccessException e) {
            LOG.error("All lectures was not founded");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Lecture> getAll(int limit, int offset) {
        String sql = properties.getProperty("lecture_getAll_limit");
        LOG.debug("Try to get {} lectures offset {}", limit, offset);
        try {
            List<Lecture> lectures = jdbcTemplate.query(sql, new Object[]{limit, offset},  mapper);
            LOG.debug("{} lectures was gotten offset {}", limit, offset);
            return lectures;
        } catch (DataAccessException e) {
            LOG.error("{} lectures was not founded offset {}", limit, offset);
            throw new DAOException(e);

        }
    }

    @Override
    public void create(Lecture lecture) {
        String sql = properties.getProperty("lecture_create");
        LOG.debug("Try to create lecture {}", lecture);
        try {
            jdbcTemplate.update(sql, lecture.getId(), lecture.getAudience(), lecture.getTeacher(), lecture.getPairTime());
            LOG.debug("Lecture {} was created", lecture);
        } catch (DataAccessException e) {
            LOG.error("Lecture {} was not created", lecture);
            throw new DAOException(e);
        }
    }

    @Override
    public Lecture get(int id) {
        String sql = properties.getProperty("lecture_get");
        LOG.debug("Try to get lecture by id {}", id);
        try {
            Lecture lecture = jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
            LOG.debug("Lecture with id {} was gotten", id);
            return lecture;
        } catch (DataAccessException e) {
            LOG.error("Lecture with id {} was not gotten", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Lecture lecture, int id) {
        String sql = properties.getProperty("lecture_update");
        LOG.debug("Try to update lecture by id {}", id);
        try {
            jdbcTemplate.update(sql, lecture.getAudience(), lecture.getTeacher(), lecture.getPairTime(), id);
            LOG.debug("Lecture with id {} was updated", id);
        } catch (DataAccessException e) {
            LOG.error("Lecture with id {} was not updated", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = properties.getProperty("lecture_delete");
        LOG.debug("Try to delete lecture by id {}", id);
        try {
            jdbcTemplate.update(sql, id);
            LOG.debug("Lecture with id {} was deleted", id);
        } catch (DataAccessException e) {
            LOG.error("Lecture with id {} was not deleted", id);
            throw new DAOException(e);
        }
    }

    public int countAll() {
        String sql = properties.getProperty("lecture_count_all");
        LOG.debug("Try to count all lectures");
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            LOG.debug("{} lectures was counted", count);
            return count;
        } catch (DataAccessException e) {
            LOG.error("lectures was not counted", e);
            throw new DAOException(e);
        }
    }
}
