package com.university.service;

import com.university.dao.sql.PairTimeDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Lecture;
import com.university.model.PairTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PairTimeService implements GenericService<PairTime> {

    private PairTimeDAO pairTimeDAO;
    private static final Logger LOG = LoggerFactory.getLogger(PairTimeService.class);

    @Autowired
    public PairTimeService(PairTimeDAO pairTimeDAO) {
        this.pairTimeDAO = pairTimeDAO;
    }

    public List<PairTime> getAll() {
        try {
            LOG.debug("Try to get all pair times");
            List<PairTime> pairTimes = pairTimeDAO.getAll();
            LOG.debug("All pair times was gotten");
            return pairTimes;
        } catch (DAOException e) {
            LOG.error("All pair times was not founded");
            throw new EntityDoesNotExistException("Cannot find all pair times");
        }
    }

    @Override
    public List<PairTime> getAll(int limit, int offset) {
        try {
            LOG.debug("Try to get {} pairTimes offset {}", limit, offset);
            List<PairTime> pairTimes = pairTimeDAO.getAll(limit, offset);
            LOG.debug("{} pairTimes was gotten offset {}", limit, offset);
            return pairTimes;
        } catch (DAOException e) {
            LOG.error("{} pairTimes was not founded offset {}", limit, offset);
            throw new EntityDoesNotExistException("Cannot find all pairTimes", e);
        }
    }

    public void create(PairTime pairTime) {
        try {
            LOG.debug("Try to create pairTime {}", pairTime);
            pairTimeDAO.create(pairTime);
            LOG.debug("PairTime {} was created", pairTime);
        } catch (DAOException e) {
            LOG.error("PairTime {} was not created", pairTime);
            throw new EntityIsNotUniqueException(String.format("Cannot create new pairTime," +
                    "pairTime with pairNumber %d is already exists", pairTime.getPairNumber()), e);
        }
    }

    public PairTime get(int pairNumber) {
        try {
            LOG.debug("Try to get pairTime by id {}", pairNumber);
            PairTime pairTime = pairTimeDAO.get(pairNumber);
            LOG.debug("pair time with id {} was gotten", pairNumber);
            return pairTime;
        } catch (DAOException e) {
            LOG.error("PairTime with id {} was not gotten", pairNumber);
            throw new EntityDoesNotExistException(String.format("Cannot get pairTime. " +
                    "PairTime with pairNumber %d doesn't exits", pairNumber), e);
        }
    }

    public void update(PairTime pairTime, int pairNumber) {
        try {
            LOG.debug("Try to update pairTime by id {}", pairNumber);
            pairTimeDAO.update(pairTime, pairNumber);
            LOG.debug("PairTime with id {} was updated", pairNumber);
        } catch (DAOException e) {
            LOG.error("PairTime with id {} was not updated", pairNumber);
            throw new EntityDoesNotExistException(String.format("Cannot update pairTime. " +
                    "PairTime with pairNumber %d doesn't exits", pairNumber), e);
        }
    }

    public void delete(int pairNumber) {
        try {
            LOG.debug("Try to delete pairTime by id {}", pairNumber);
            pairTimeDAO.delete(pairNumber);
            LOG.debug("PairTime with id {} was deleted", pairNumber);
        } catch (DAOException e) {
            LOG.error("PairTime with id {} was not deleted", pairNumber);
            throw new EntityDoesNotExistException(String.format("Cannot delete pairTime. " +
                    "pairTime with pairNumber %d doesn't exits", pairNumber), e);
        }
    }

    public int countAll() {
        try {
            LOG.debug("Try to count all pairTimes");
            int count = pairTimeDAO.countAll();
            LOG.debug("{} pairTimes was counted", count);
            return count;
        } catch (DAOException e) {
            LOG.error("pairTimes was not counted", e);
            throw new EntityDoesNotExistException("Cannot count all pairTimes",e);
        }
    }
}
