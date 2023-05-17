package com.university.service;

import com.university.dao.sql.StudentDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements GenericService<Student> {

    private StudentDAO studentDAO;
    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public List<Student> getAll() {
        try {
            LOG.debug("Try to get all students");
            List<Student> students = studentDAO.getAll();
            LOG.debug("All students was gotten");
            return students;
        } catch (DAOException e) {
            LOG.error("All students was not founded");
            throw new EntityDoesNotExistException("Cannot find all students");
        }
    }

    @Override
    public List<Student> getAll(int limit, int offset) {
        try {
            LOG.debug("Try to get {} students offset {}", limit, offset);
            List<Student> students = studentDAO.getAll(limit, offset);
            LOG.debug("{} students was gotten offset {}", limit, offset);
            return students;
        } catch (DAOException e) {
            LOG.error("{} students was not founded offset {}", limit, offset);
            throw new EntityDoesNotExistException("Cannot find all students", e);
        }
    }

    public void create(Student student) {
        try {
            LOG.debug("Try to create student {}", student);
            studentDAO.create(student);
            LOG.debug("Student {} was created", student);
        } catch (DAOException e) {
            LOG.error("Student {} was not created", student);
            throw new EntityIsNotUniqueException(String.format("Cannot create new student," +
                    "student with id %d is already exists", student.getStudentId()), e);
        }
    }

    public Student get(int id) {
        try {
            LOG.debug("Try to get student by id {}", id);
            return studentDAO.get(id);
        } catch (DAOException e) {
            LOG.error("Student with id {} was not gotten", id);
            throw new EntityDoesNotExistException(String.format("Cannot get student. " +
                    "Student with id %d doesn't exits", id), e);
        }
    }

    public void update(Student student, int id) {
        try {
            LOG.debug("Try to update student by id {}", id);
            studentDAO.update(student, id);
            LOG.debug("Student with id {} was updated", id);
        } catch (DAOException e) {
            LOG.error("Student with id {} was not updated", id);
            throw new EntityDoesNotExistException(String.format("Cannot update student. " +
                    "Student with id %d doesn't exits", id), e);
        }
    }

    public void delete(int id) {
        try {
            LOG.debug("Try to delete student by id {}", id);
            studentDAO.delete(id);
            LOG.debug("Student with id {} was deleted", id);
        } catch (DAOException e) {
            LOG.error("Student with id {} was not deleted", id);
            throw new EntityDoesNotExistException(String.format("Cannot delete student. " +
                    "student with id %d doesn't exits", id), e);
        }
    }

    public int countAll() {
        try {
            LOG.debug("Try to count all students");
            int count = studentDAO.countAll();
            LOG.debug("{} students was counted", count);
            return count;
        } catch (DAOException e) {
            LOG.error("students was not counted", e);
            throw new EntityDoesNotExistException("Cannot count all students",e);
        }
    }

}