package com.university.service;

import com.university.dao.sql.SubjectDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Student;
import com.university.model.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService implements GenericService<Subject> {

    private SubjectDAO subjectDAO;
    private static final Logger LOG = LoggerFactory.getLogger(SubjectService.class);

    @Autowired
    public SubjectService(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    public List<Subject> getAll() {
        try {
            LOG.debug("Try to get all subjects");
            List<Subject> subjects = subjectDAO.getAll();
            LOG.debug("All subjects was founded");
            return subjects;
        } catch (DataAccessException e) {
            LOG.error("All subjects was not founded");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Subject> getAll(int limit, int offset) {
        try {
            LOG.debug("Try to get {} subjects offset {}", limit, offset);
            List<Subject> subjects = subjectDAO.getAll(limit, offset);
            LOG.debug("{} subjects was gotten offset {}", limit, offset);
            return subjects;
        } catch (DAOException e) {
            LOG.error("{} subjects was not founded offset {}", limit, offset);
            throw new EntityDoesNotExistException("Cannot find all subjects", e);
        }
    }

    public void create(Subject subject) {
        try {
            LOG.debug("Try to create subject {}", subject);
            subjectDAO.create(subject);
            LOG.debug("Subject {} was created", subject);
        } catch (DAOException e) {
            LOG.error("Subject {} was not created", subject);
            throw new EntityIsNotUniqueException(String.format("Cannot create new subject," +
                    "subject with id %d is already exists", subject.getId()), e);
        }
    }

    public Subject get(int id) {
        try {
            LOG.debug("Try to get subject by id {}", id);
            return subjectDAO.get(id);
        } catch (DAOException e) {
            LOG.error("Subject with id {} was not gotten", id);
            throw new EntityDoesNotExistException(String.format("Cannot get subject. " +
                    "Subject with id %d doesn't exits", id), e);
        }
    }

    public void update(Subject subject, int id) {
        try {
            LOG.debug("Try to update subject by id {}", id);
            subjectDAO.update(subject, id);
            LOG.debug("Subject with id {} was updated", id);
        } catch (DAOException e) {
            LOG.error("Subject with id {} was not updated", id);
            throw new EntityDoesNotExistException(String.format("Cannot update subject. " +
                    "Subject with id %d doesn't exits", id), e);
        }
    }

    public void delete(int id) {
        try {
            LOG.debug("Try to delete subject by id {}", id);
            subjectDAO.delete(id);
            LOG.debug("Subject with id {} was deleted", id);
        } catch (DAOException e) {
            LOG.error("Subject with id {} was not deleted", id);
            throw new EntityDoesNotExistException(String.format("Cannot delete subject. " +
                    "subject with id %d doesn't exits", id), e);
        }
    }

    public int countAll() {
        try {
            LOG.debug("Try to count all subjects");
            int count = subjectDAO.countAll();
            LOG.debug("{} subjects was counted", count);
            return count;
        } catch (DAOException e) {
            LOG.error("subjects was not counted", e);
            throw new EntityDoesNotExistException("Cannot count all subjects",e);
        }
    }

}
