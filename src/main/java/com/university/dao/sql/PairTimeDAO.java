package com.university.dao.sql;

import com.university.dao.GenericDAO;
import com.university.dao.sql.mapper.PairTimeMapper;
import com.university.exception.DAOException;
import com.university.model.Lecture;
import com.university.model.PairTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class PairTimeDAO implements GenericDAO<PairTime> {

    private JdbcTemplate jdbcTemplate;
    private Properties properties;
    private PairTimeMapper mapper;
    private static final Logger LOG = LoggerFactory.getLogger(PairTimeDAO.class);

    @Autowired
    public PairTimeDAO(JdbcTemplate jdbcTemplate, Properties properties, PairTimeMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.properties = properties;
        this.mapper = mapper;
    }

    @Override
    public List<PairTime> getAll() {
        String sql = properties.getProperty("pairTime_getAll");
        LOG.debug("Try to get all pair times");
        try {
            List<PairTime> pairTimes = jdbcTemplate.query(sql, mapper);
            LOG.debug("All pair times was gotten");
            return pairTimes;
        } catch (DataAccessException e) {
            LOG.error("All pair times was not founded");
            throw new DAOException(e);
        }
    }

    @Override
    public List<PairTime> getAll(int limit, int offset) {
        String sql = properties.getProperty("pairTime_getAll_limit");
        LOG.debug("Try to get {} pairTime offset {}", limit, offset);
        try {
            List<PairTime> pairTimes = jdbcTemplate.query(sql, new Object[]{limit, offset},  mapper);
            LOG.debug("{} pairTime was gotten offset {}", limit, offset);
            return pairTimes;
        } catch (DataAccessException e) {
            LOG.error("{} pairTime was not founded offset {}", limit, offset);
            throw new DAOException(e);

        }
    }

    @Override
    public void create(PairTime pairTime) {
        String sql = properties.getProperty("pairTime_create");
        LOG.debug("Try to create pairTime {}", pairTime);
        try {
            jdbcTemplate.update(sql, pairTime.getPairNumber(), pairTime.getStartTime(), pairTime.getEndTime());
            LOG.debug("PairTime {} was created", pairTime);
        } catch (DataAccessException e) {
            LOG.error("PairTime {} was not created", pairTime);
            throw new DAOException(e);
        }
    }

    @Override
    public PairTime get(int id) {
        String sql = properties.getProperty("pairTime_get");
        LOG.debug("Try to get pairTime by id {}", id);
        try {
            PairTime pairTime = jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
            LOG.debug("pair time with id {} was gotten", id);
            return pairTime;
        } catch (DataAccessException e) {
            LOG.error("PairTime with id {} was not gotten", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(PairTime pairTime, int id) {
        String sql = properties.getProperty("pairTime_update");
        LOG.debug("Try to update pairTime by id {}", id);
        try {
            jdbcTemplate.update(sql, pairTime.getStartTime(), pairTime.getEndTime(), id);
            LOG.debug("PairTime with id {} was updated", id);
        } catch (DataAccessException e) {
            LOG.error("PairTime with id {} was not updated", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = properties.getProperty("pairTime_delete");
        LOG.debug("Try to delete pairTime by id {}", id);
        try {
            jdbcTemplate.update(sql, id);
            LOG.debug("PairTime with id {} was deleted", id);
        } catch (DataAccessException e) {
            LOG.error("PairTime with id {} was not deleted", id);
            throw new DAOException(e);
        }
    }

    public int countAll() {
        String sql = properties.getProperty("pairTime_count_all");
        LOG.debug("Try to count all pairTimes");
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            LOG.debug("{} pairTimes was counted", count);
            return count;
        } catch (DataAccessException e) {
            LOG.error("pairTimes was not counted", e);
            throw new DAOException(e);
        }
    }
}
