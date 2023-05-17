package com.university.dao.sql;

import com.university.dao.GenericDAO;
import com.university.dao.sql.mapper.StudentMapper;
import com.university.exception.DAOException;
import com.university.model.PairTime;
import com.university.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class StudentDAO implements GenericDAO<Student> {

    private JdbcTemplate jdbcTemplate;
    private Properties properties;
    private StudentMapper mapper;
    private static final Logger LOG = LoggerFactory.getLogger(StudentDAO.class);

    @Autowired
    public StudentDAO(JdbcTemplate jdbcTemplate, Properties properties, StudentMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.properties = properties;
        this.mapper = mapper;
    }


    @Override
    public List<Student> getAll() {
        String sql = properties.getProperty("student_getAll");
        LOG.debug("Try to get all students");
        try {
            List<Student> students = jdbcTemplate.query(sql, mapper);
            LOG.debug("All students was gotten");
            return students;
        } catch (DataAccessException e) {
            LOG.error("All students was not founded");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Student> getAll(int limit, int offset) {
        String sql = properties.getProperty("student_getAll_limit");
        LOG.debug("Try to get {} student offset {}", limit, offset);
        try {
            List<Student> students = jdbcTemplate.query(sql, new Object[]{limit, offset}, mapper);
            LOG.debug("{} student was gotten offset {}", limit, offset);
            return students;
        } catch (DataAccessException e) {
            LOG.error("{} student was not founded offset {}", limit, offset);
            throw new DAOException(e);

        }
    }

    @Override
    public void create(Student student) {
        String sql = properties.getProperty("student_create");
        LOG.debug("Try to create student {}", student);
        try {
            jdbcTemplate.update(sql, student.getStudentId(), student.getFirstName(), student.getLastName(),
                    student.getEmail(), student.getYear(), student.getGroupId());
            LOG.debug("Student {} was created", student);
        } catch (DataAccessException e) {
            LOG.error("Student {} was not created", student);
            throw new DAOException(e);
        }
    }

    @Override
    public Student get(int id) {
        String sql = properties.getProperty("student_get");
        LOG.debug("Try to get student by id {}", id);
        try {
            Student student = jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
            LOG.debug("Student with id {} was gotten", id);
            return student;
        } catch (DataAccessException e) {
            LOG.error("Student with id {} was not gotten", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Student student, int id) {
        String sql = properties.getProperty("student_update");
        LOG.debug("Try to update student by id {}", id);
        try {
            jdbcTemplate.update(sql, student.getFirstName(), student.getLastName(), student.getEmail(), student.getYear(), id);
            LOG.debug("Student with id {} was updated", id);
        } catch (DataAccessException e) {
            LOG.error("Student with id {} was not updated", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = properties.getProperty("student_delete");
        LOG.debug("Try to delete student by id {}", id);
        try {
            jdbcTemplate.update(sql, id);
            LOG.debug("Student with id {} was deleted", id);
        } catch (DataAccessException e) {
            LOG.error("Student with id {} was not deleted", id);
            throw new DAOException(e);
        }
    }

    public int countAll() {
        String sql = properties.getProperty("student_count_all");
        LOG.debug("Try to count all students");
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            LOG.debug("{} students was counted", count);
            return count;
        } catch (DataAccessException e) {
            LOG.error("students was not counted", e);
            throw new DAOException(e);
        }
    }
}
