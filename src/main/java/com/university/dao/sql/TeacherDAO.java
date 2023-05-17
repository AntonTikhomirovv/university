package com.university.dao.sql;

import com.university.dao.GenericDAO;
import com.university.dao.sql.mapper.TeacherMapper;
import com.university.exception.DAOException;
import com.university.model.Subject;
import com.university.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class TeacherDAO implements GenericDAO<Teacher> {


    private JdbcTemplate jdbcTemplate;
    private Properties properties;
    private TeacherMapper mapper;
    private static final Logger LOG = LoggerFactory.getLogger(TeacherDAO.class);

    @Autowired
    public TeacherDAO(JdbcTemplate jdbcTemplate, Properties properties, TeacherMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.properties = properties;
        this.mapper = mapper;
    }

    @Override
    public List<Teacher> getAll() {
        String sql = properties.getProperty("teacher_getAll");
        LOG.debug("Try to get all teachers");
        try {
            List<Teacher> teachers = jdbcTemplate.query(sql, mapper);
            LOG.debug("All teachers was gotten");
            return teachers;
        } catch (DataAccessException e) {
            LOG.error("All teachers was not founded");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Teacher> getAll(int limit, int offset) {
        String sql = properties.getProperty("teacher_getAll_limit");
        LOG.debug("Try to get {} teacher offset {}", limit, offset);
        try {
            List<Teacher> teachers = jdbcTemplate.query(sql, new Object[]{limit, offset}, mapper);
            LOG.debug("{} teacher was gotten offset {}", limit, offset);
            return teachers;
        } catch (DataAccessException e) {
            LOG.error("{} teacher was not founded offset {}", limit, offset);
            throw new DAOException(e);

        }
    }

    @Override
    public void create(Teacher teacher) {
        String sql = properties.getProperty("teacher_create");
        LOG.debug("Try to create teacher {}", teacher);
        try {
            jdbcTemplate.update(sql, teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getEmail());
            LOG.debug("Teacher {} was created", teacher);
        } catch (DataAccessException e) {
            LOG.error("Teacher {} was not created", teacher);
            throw new DAOException(e);
        }
    }

    @Override
    public Teacher get(int id) {
        String sql = properties.getProperty("teacher_get");
        LOG.debug("Try to get teacher by id {}", id);
        try {
            Teacher teacher = jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
            LOG.debug("Teacher with id {} was gotten", id);
            return teacher;
        } catch (DataAccessException e) {
            LOG.error("Teacher with id {} was not gotten", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Teacher teacher, int id) {
        String sql = properties.getProperty("teacher_update");
        LOG.debug("Try to update teacher by id {}", id);
        try {
            jdbcTemplate.update(sql, teacher.getFirstName(), teacher.getLastName(), teacher.getEmail(), id);
            LOG.debug("Teacher with id {} was updated", id);
        } catch (DataAccessException e) {
            LOG.error("Teacher with id {} was not updated", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = properties.getProperty("teacher_delete");
        LOG.debug("Try to delete teacher by id {}", id);
        try {
            jdbcTemplate.update(sql, id);
            LOG.debug("Teacher with id {} was deleted", id);
        } catch (DataAccessException e) {
            LOG.error("Teacher with id {} was not deleted", id);
            throw new DAOException(e);
        }
    }

    public int countAll() {
        String sql = properties.getProperty("teacher_count_all");
        LOG.debug("Try to count all teachers");
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            LOG.debug("{} teachers was counted", count);
            return count;
        } catch (DataAccessException e) {
            LOG.error("teachers was not counted", e);
            throw new DAOException(e);
        }
    }
}
