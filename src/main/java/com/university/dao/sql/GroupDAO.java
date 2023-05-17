package com.university.dao.sql;

import com.university.dao.GenericDAO;
import com.university.dao.sql.mapper.GroupMapper;
import com.university.exception.DAOException;
import com.university.model.Audience;
import com.university.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class GroupDAO implements GenericDAO<Group> {

    private JdbcTemplate jdbcTemplate;
    private Properties properties;
    private GroupMapper mapper;
    private static final Logger LOG = LoggerFactory.getLogger(GroupDAO.class);


    @Autowired
    public GroupDAO(JdbcTemplate jdbcTemplate, Properties properties, GroupMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.properties = properties;
        this.mapper = mapper;
    }

    @Override
    public List<Group> getAll() {
        String sql = properties.getProperty("group_getAll");
        LOG.debug("Try to get all groups");
        try {
            List<Group> groups = jdbcTemplate.query(sql, mapper);
            LOG.debug("All groups was gotten");
            return groups;
        } catch (DataAccessException e) {
            LOG.error("All groups was not founded");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Group> getAll(int limit, int offset) {
        String sql = properties.getProperty("group_getAll_limit");
        LOG.debug("Try to get {} groups offset {}", limit, offset);
        try {
            List<Group> groups = jdbcTemplate.query(sql, new Object[]{limit, offset},  mapper);
            LOG.debug("{} groups was gotten offset {}", limit, offset);
            return groups;
        } catch (DataAccessException e) {
            LOG.error("{} groups was not founded offset {}", limit, offset);
            throw new DAOException(e);

        }
    }

    @Override
    public void create(Group group) {
        String sql = properties.getProperty("group_create");
        LOG.debug("Try to create group {}", group);
        try {
            jdbcTemplate.update(sql, group.getId(), group.getNumber());
            LOG.debug("Group {} was created", group);
        } catch (DataAccessException e) {
            LOG.error("Group {} was not created", group);
            throw new DAOException(e);
        }
    }

    @Override
    public Group get(int id) {
        String sql = properties.getProperty("group_get");
        LOG.debug("Try to get group by id {}", id);
        try {
            Group group = jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
            LOG.debug("Group with id {} was gotten", id);
            return group;
        } catch (DataAccessException e) {
            LOG.error("Group with id {} was not gotten", id);
            throw new DAOException(e);
        }
    }


    @Override
    public void update(Group group, int id) {
        String sql = properties.getProperty("group_update");
        LOG.debug("Try to update group by id {}", id);
        try {
            jdbcTemplate.update(sql, group.getNumber(), id);
            LOG.debug("Group with id {} was updated", id);
        } catch (DataAccessException e) {
            LOG.error("Group with id {} was not updated", id);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = properties.getProperty("group_delete");
        LOG.debug("Try to delete group by id {}", id);
        try {
            jdbcTemplate.update(sql, id);
            LOG.debug("Group with id {} was deleted", id);
        } catch (DataAccessException e) {
            LOG.error("Group with id {} was not deleted", id);
            throw new DAOException(e);
        }
    }

    public int countAll() {
        String sql = properties.getProperty("group_count_all");
        LOG.debug("Try to count all groups");
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            LOG.debug("{} groups was counted", count);
            return count;
        } catch (DataAccessException e) {
            LOG.error("groups was not counted", e);
            throw new DAOException(e);
        }
    }
}
