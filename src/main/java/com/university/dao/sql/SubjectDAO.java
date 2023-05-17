package com.university.dao.sql;

import com.university.dao.GenericDAO;
import com.university.dao.sql.mapper.SubjectMapper;
import com.university.exception.DAOException;
import com.university.model.Student;
import com.university.model.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class SubjectDAO implements GenericDAO<Subject> {


    private JdbcTemplate jdbcTemplate;
    private Properties properties;
    private SubjectMapper mapper;
    private static final Logger LOG = LoggerFactory.getLogger(SubjectDAO.class);

    @Autowired
    public SubjectDAO(JdbcTemplate jdbcTemplate, Properties properties, SubjectMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.properties = properties;
        this.mapper = mapper;
    }

    @Override
    public List<Subject> getAll() {
        String sql = properties.getProperty("subject_getAll");
        LOG.debug("Try to get all subjects");
        try {
            List<Subject> subjects = jdbcTemplate.query(sql, mapper);
            LOG.debug("All subjects was founded");
            return subjects;
        } catch (DataAccessException e) {
            LOG.error("All subjects was not founded");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Subject> getAll(int limit, int offset) {
        String sql = properties.getProperty("subject_getAll_limit");
        LOG.debug("Try to get {} subjects offset {}", limit, offset);
        try {
            List<Subject> subjects = jdbcTemplate.query(sql, new Object[]{limit, offset},  mapper);
            LOG.debug("{} subjects was gotten offset {}", limit, offset);
            return subjects;
        } catch (DataAccessException e) {
            LOG.error("{} subjects was not founded offset {}", limit, offset);
            throw new DAOException(e);

        }
    }

    @Override
    public void create(Subject subject) {
        String sql = properties.getProperty("subject_create");
        LOG.debug("Try to create subject {}", subject);
        try {
            jdbcTemplate.update(sql, subject.getId(), subject.getName());
            LOG.debug("Subject {} was created", subject);
        } catch (DataAccessException e) {
            LOG.error("Subject {} was not created", subject);
            throw new DAOException(e);
        }
    }

    @Override
    public Subject get(int id) {
        String sql = properties.getProperty("subject_get");
        LOG.debug("Try to get subject by id {}", id);
        try {
            Subject subject = jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
            LOG.debug("Subject with id {} was gotten", id);
            return subject;
        } catch (DataAccessException e) {
            LOG.error("Subject with id {} was not gotten", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Subject subject, int id) {
        String sql = properties.getProperty("subject_update");
        LOG.debug("Try to update subject by id {}", id);
        try {
            jdbcTemplate.update(sql, subject.getName(), id);
            LOG.debug("Subject with id {} was updated", id);
        } catch (DataAccessException e) {
            LOG.error("Subject with id {} was not updated", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = properties.getProperty("subject_delete");
        LOG.debug("Try to delete subject by id {}", id);
        try {
            jdbcTemplate.update(sql, id);
            LOG.debug("Subject with id {} was deleted", id);
        } catch (DataAccessException e) {
            LOG.error("Subject with id {} was not deleted", id);
            throw new DAOException(e);
        }
    }

    public int countAll() {
        String sql = properties.getProperty("subject_count_all");
        LOG.debug("Try to count all subjects");
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            LOG.debug("{} subjects was counted", count);
            return count;
        } catch (DataAccessException e) {
            LOG.error("subjects was not counted", e);
            throw new DAOException(e);
        }
    }
}
