package com.university.dao.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.university.dao.GenericDAO;
import com.university.dao.sql.mapper.AudienceMapper;
import com.university.exception.DAOException;
import com.university.model.Audience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import static java.lang.String.format;

import java.util.List;
import java.util.Properties;

@Component
public class AudienceDAO implements GenericDAO<Audience> {

    private JdbcTemplate jdbcTemplate;
    private Properties properties;
    private AudienceMapper mapper;
    private static final Logger LOG = LoggerFactory.getLogger(AudienceDAO.class);

    @Autowired
    public AudienceDAO(JdbcTemplate jdbcTemplate, Properties properties, AudienceMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.properties = properties;
        this.mapper = mapper;
    }

    @Override
    public List<Audience> getAll() {
        String sql = properties.getProperty("audience_getAll");
        LOG.debug("Try to get all audiences");
        try {
            List<Audience> audiences = jdbcTemplate.query(sql, mapper);
            LOG.debug("All audiences was gotten");
            return audiences;
        } catch (DataAccessException e) {
            LOG.error("All audiences was not founded");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Audience> getAll(int limit, int offset) {
        String sql = properties.getProperty("audience_getAll_limit");
        LOG.debug("Try to get {} audiences offset {}", limit, offset);
        try {
            List<Audience> audiences = jdbcTemplate.query(sql, new Object[]{limit, offset},  mapper);
            LOG.debug("{} audiences was gotten offset {}", limit, offset);
            return audiences;
        } catch (DataAccessException e) {
            LOG.error("{} audiences was not founded offset {}", limit, offset);
            throw new DAOException(e);
        }
    }

    @Override
    public void create(Audience audience) {
        String sql = properties.getProperty("audience_create");
        LOG.debug("Try to create audience {}", audience);
        try {
            jdbcTemplate.update(sql, audience.getId(), audience.getCampusNumber(), audience.getRoomNumber());
            LOG.debug("Audience {} was created", audience);
        } catch (DataAccessException e) {
            LOG.error("Audience {} was not created", audience);
            throw new DAOException(e);
        }

    }

    @Override
    public Audience get(int id) {
        String sql = properties.getProperty("audience_get");
        LOG.debug("Try to get audience by id {}", id);
        try {
            Audience audience = jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
            LOG.debug("Audience with id {} was gotten", id);
            return audience;
        } catch (DataAccessException e) {
            LOG.error("Audience with id {} was not gotten", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Audience audience, int id) {
        String sql = properties.getProperty("audience_update");
        LOG.debug("Try to update audience by id {}", id);
        try {
            jdbcTemplate.update(sql, audience.getCampusNumber(), audience.getRoomNumber(), id);
            LOG.debug("Audience with id {} was updated", id);
        } catch (DataAccessException e) {
            LOG.error("Audience with id {} was not updated", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = properties.getProperty("audience_delete");
        LOG.debug("Try to delete audience by id {}", id);
        try {
            jdbcTemplate.update(sql, id);
            LOG.debug("Audience with id {} was deleted", id);
        } catch (DataAccessException e) {
            LOG.error("Audience with id {} was not deleted", id);
            throw new DAOException(e);
        }
    }

    public int countAll() {
        String sql = properties.getProperty("audience_count_all");
        LOG.debug("Try to count all audiences");
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            LOG.debug("{} audiences was counted", count);
            return count;
        } catch (DataAccessException e) {
            LOG.error("Audiences was not counted", e);
            throw new DAOException(e);
        }
    }
}
