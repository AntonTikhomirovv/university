package com.university.service;

import com.university.dao.sql.AudienceDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Audience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudienceService implements GenericService<Audience> {

    private AudienceDAO audienceDAO;
    private static final Logger LOG = LoggerFactory.getLogger(AudienceService.class);

    @Autowired
    public AudienceService(AudienceDAO audienceDAO) {
        this.audienceDAO = audienceDAO;
    }

    public List<Audience> getAll() {
        try {
            LOG.debug("Try to get all audiences");
            List<Audience> audiences = audienceDAO.getAll();
            LOG.debug("All audiences was gotten");
            return audiences;
        } catch (DAOException e) {
            LOG.error("All audiences was not founded");
            throw new EntityDoesNotExistException("Cannot find all audiences", e);
        }
    }

    @Override
    public List<Audience> getAll(int limit, int offset) {
        try {
            LOG.debug("Try to get {} audiences offset {}", limit, offset);
            List<Audience> audiences = audienceDAO.getAll(limit, offset);
            LOG.debug("{} audiences was gotten offset {}", limit, offset);
            return audiences;
        } catch (DAOException e) {
            LOG.error("{} audiences was not founded offset {}", limit, offset);
            throw new EntityDoesNotExistException("Cannot find all audiences", e);
        }
    }


    public void create(Audience audience) {
        try {
            LOG.debug("Try to create audience {}", audience);
            audienceDAO.create(audience);
            LOG.debug("Audience {} was created", audience);
        } catch (DAOException e) {
            LOG.error("Audience {} was not created", audience);
            throw new EntityIsNotUniqueException(String.format("Cannot create new audience," +
                    "audience with id %d is already exists", audience.getId()), e);
        }
    }

    public Audience get(int id) {
        try {
            LOG.debug("Try to get audience by id {}", id);
            Audience audience =  audienceDAO.get(id);
            LOG.debug("Audience with id {} was gotten", id);
            return audience;
        } catch (DAOException e) {
            LOG.error("Audience with id {} was not gotten", id);
            throw new EntityDoesNotExistException(String.format("Cannot get audience. " +
                    "Audience with id %d doesn't exits", id), e);
        }
    }

    public void update(Audience audience, int id) {
        try {
            LOG.debug("Try to update audience by id {}", id);
            audienceDAO.update(audience, id);
            LOG.debug("Audience with id {} was updated", id);
        } catch (DAOException e) {
            LOG.error("Audience with id {} was not updated", id);
            throw new EntityDoesNotExistException(String.format("Cannot update audience. " +
                    "Audience with id %d doesn't exits", id), e);
        }
    }

    public void delete(int id) {
        try {
            LOG.debug("Try to delete audience by id {}", id);
            audienceDAO.delete(id);
            LOG.debug("Audience with id {} was deleted", id);
        } catch (DAOException e) {
            LOG.error("Audience with id {} was not deleted", id);
            throw new EntityDoesNotExistException(String.format("Cannot delete audience. " +
                    "audience with id %d doesn't exits", id), e);
        }
    }

    public int countAll() {
        try {
            LOG.debug("Try to count all audiences");
            int count = audienceDAO.countAll();
            LOG.debug("{} audiences was counted", count);
            return count;
        } catch (DAOException e) {
            LOG.error("audiences was not counted", e);
            throw new EntityDoesNotExistException("Cannot count all audiences",e);
        }
    }

}
