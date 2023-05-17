package com.university.service;

import com.university.dao.sql.TeacherDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Subject;
import com.university.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService implements GenericService<Teacher> {

    private TeacherDAO teacherDAO;
    private static final Logger LOG = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    public TeacherService(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    public List<Teacher> getAll() {
        try {
            LOG.debug("Try to get all teachers");

            List<Teacher> teachers = teacherDAO.getAll();
            LOG.debug("All teachers was gotten");
            return teachers;
        }
        catch (DataAccessException e) {
            LOG.error("All students was not founded");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Teacher> getAll(int limit, int offset) {
        try {
            LOG.debug("Try to get {} teachers offset {}", limit, offset);
            List<Teacher> teachers = teacherDAO.getAll(limit, offset);
            LOG.debug("{} teachers was gotten offset {}", limit, offset);
            return teachers;
        } catch (DAOException e) {
            LOG.error("{} teachers was not founded offset {}", limit, offset);
            throw new EntityDoesNotExistException("Cannot find all teachers", e);
        }
    }


    public void create(Teacher teacher) {
        try {
            LOG.debug("Try to create teacher {}", teacher);
            teacherDAO.create(teacher);
            LOG.debug("Teacher {} was created", teacher);
        } catch (DAOException e) {
            LOG.error("Teacher {} was not created", teacher);
            throw new EntityIsNotUniqueException(String.format("Cannot create new teacher," +
                    "teacher with id %d is already exists", teacher.getId()), e);
        }
    }

    public Teacher get(int id) {
        try {
            LOG.debug("Try to get teacher by id {}", id);
            return teacherDAO.get(id);
        } catch (DAOException e) {
            LOG.error("Teacher with id {} was not gotten", id);
            throw new EntityDoesNotExistException(String.format("Cannot get teacher. " +
                    "Teacher with id %d doesn't exits", id), e);
        }
    }

    public void update(Teacher teacher, int id) {
        try {
            LOG.debug("Try to update teacher by id {}", id);
            teacherDAO.update(teacher, id);
            LOG.debug("Teacher with id {} was updated", id);
        } catch (DAOException e) {
            LOG.error("Teacher with id {} was not updated", id);
            throw new EntityDoesNotExistException(String.format("Cannot update teacher. " +
                    "Teacher with id %d doesn't exits", id), e);
        }
    }

    public void delete(int id) {
        try {
            LOG.debug("Try to delete teacher by id {}", id);
            teacherDAO.delete(id);
            LOG.debug("Teacher with id {} was deleted", id);
        } catch (DAOException e) {
            LOG.error("Teacher with id {} was not deleted", id);
            throw new EntityDoesNotExistException(String.format("Cannot delete teacher. " +
                    "teacher with id %d doesn't exits", id), e);
        }
    }

    public int countAll() {
        try {
            LOG.debug("Try to count all teachers");
            int count = teacherDAO.countAll();
            LOG.debug("{} teachers was counted", count);
            return count;
        } catch (DAOException e) {
            LOG.error("teachers was not counted", e);
            throw new EntityDoesNotExistException("Cannot count all teachers",e);
        }
    }

}
