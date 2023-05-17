package com.university.service;

import com.university.dao.sql.LectureDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Audience;
import com.university.model.Lecture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService implements GenericService<Lecture> {

    private LectureDAO lectureDAO;
    private static final Logger LOG = LoggerFactory.getLogger(LectureService.class);

    @Autowired
    public LectureService(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    public List<Lecture> getAll() {
        try {
            LOG.debug("Try to get all lectures");
            List<Lecture> lectures = lectureDAO.getAll();
            LOG.debug("All lectures was gotten");
            return lectures;
        } catch (DAOException e) {
            LOG.error("All lectures was not founded");
            throw new EntityDoesNotExistException("Cannot find all lectures", e);
        }
    }

    @Override
    public List<Lecture> getAll(int limit, int offset) {
        try {
            LOG.debug("Try to get {} lectures offset {}", limit, offset);
            List<Lecture> lectures = lectureDAO.getAll(limit, offset);
            LOG.debug("{} lectures was gotten offset {}", limit, offset);
            return lectures;
        } catch (DAOException e) {
            LOG.error("{} lectures was not founded offset {}", limit, offset);
            throw new EntityDoesNotExistException("Cannot find all lectures", e);
        }
    }

    public void create(Lecture lecture) {
        try {
            LOG.debug("Try to create lecture {}", lecture);
            lectureDAO.create(lecture);
            LOG.debug("Lecture {} was created", lecture);
        } catch (DAOException e) {
            LOG.error("Lecture {} was not created", lecture);
            throw new EntityIsNotUniqueException(String.format("Cannot create new lecture," +
                    "lecture with id %d is already exists", lecture.getId()), e);
        }
    }

    public Lecture get(int id) {
        try {
            LOG.debug("Try to get lecture by id {}", id);
            Lecture lecture = lectureDAO.get(id);
            LOG.debug("Lecture with id {} was gotten", id);
            return lecture;
        } catch (DAOException e) {
            LOG.error("Lecture with id {} was not gotten", id);
            throw new EntityDoesNotExistException(String.format("Cannot get lecture. " +
                    "Lecture with id %d doesn't exits", id), e);
        }
    }

    public void update(Lecture lecture, int id) {
        try {
            LOG.debug("Try to update lecture by id {}", id);
            lectureDAO.update(lecture, id);
            LOG.debug("Lecture with id {} was updated", id);
        } catch (DAOException e) {
            LOG.error("Lecture with id {} was not updated", id);
            throw new EntityDoesNotExistException(String.format("Cannot update lecture. " +
                    "Lecture with id %d doesn't exits", id), e);
        }
    }

    public void delete(int id) {
        try {
            LOG.debug("Try to delete lecture by id {}", id);
            lectureDAO.delete(id);
            LOG.debug("Lecture with id {} was deleted", id);
        } catch (DAOException e) {
            LOG.error("Lecture with id {} was not deleted", id);
            throw new EntityDoesNotExistException(String.format("Cannot delete lecture. " +
                    "lecture with id %d doesn't exits", id), e);
        }
    }

    public int countAll() {
        try {
            LOG.debug("Try to count all lectures");
            int count = lectureDAO.countAll();
            LOG.debug("{} lectures was counted", count);
            return count;
        } catch (DAOException e) {
            LOG.error("lectures was not counted", e);
            throw new EntityDoesNotExistException("Cannot count all lectures",e);
        }
    }
}
