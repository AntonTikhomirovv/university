package com.university.service;

import com.university.dao.sql.GroupDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService implements GenericService<Group> {

    private GroupDAO groupDAO;
    private static final Logger LOG = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public List<Group> getAll() {
        try {
            LOG.debug("Try to get all groups");
            List<Group> groups = groupDAO.getAll();
            LOG.debug("All groups was gotten");
            return groups;
        } catch (DAOException e) {
            LOG.error("All groups was not founded");
            throw new EntityDoesNotExistException("Cannot find all groups", e);
        }
    }

    @Override
    public List<Group> getAll(int limit, int offset) {
        try {
            LOG.debug("Try to get {} groups offset {}", limit, offset);
            List<Group> groups = groupDAO.getAll(limit, offset);
            LOG.debug("{} groups was gotten offset {}", limit, offset);
            return groups;
        } catch (DAOException e) {
            LOG.error("{} groups was not founded offset {}", limit, offset);
            throw new EntityDoesNotExistException("Cannot find all groups", e);
        }
    }

    public void create(Group group) {
        try {
            LOG.debug("Try to create group {}", group);
            groupDAO.create(group);
            LOG.debug("Group {} was created", group);
        } catch (DAOException e) {
            LOG.error("Group {} was not created", group);
            throw new EntityIsNotUniqueException(String.format("Cannot create new group," +
                    "group with id %d is already exists", group.getId()), e);
        }
    }

    public Group get(int id) {
        try {
            LOG.debug("Try to get group by id {}", id);
            Group group = groupDAO.get(id);
            LOG.debug("Group with id {} was gotten", id);
            return group;
        } catch (DAOException e)  {
            LOG.error("Group with id {} was not gotten", id);
            throw new EntityDoesNotExistException(String.format("Cannot get group. " +
                    "Group with id %d doesn't exits", id), e);
        }
    }

    public void update(Group group, int id) {
        try {
            LOG.debug("Try to update group by id {}", id);
            groupDAO.update(group, id);
            LOG.debug("Group with id {} was updated", id);
        } catch (DAOException e)  {
            throw new EntityDoesNotExistException(String.format("Cannot update group. Group with id %d doesn't exits", id), e);
        }
    }

    public void delete(int id) {
        try {
            LOG.debug("Try to delete group by id {}", id);
            groupDAO.delete(id);
            LOG.debug("Group with id {} was deleted", id);
        } catch (DAOException e)  {
            LOG.error("Group with id {} was not deleted", id);
            throw new EntityDoesNotExistException(String.format("Cannot delete group. Group with id %d doesn't exits", id), e);
        }
    }

    public int countAll() {
        try {
            LOG.debug("Try to count all groups");
            int count = groupDAO.countAll();
            LOG.debug("{} groups was counted", count);
            return count;
        } catch (DAOException e) {
            LOG.error("groups was not counted", e);
            throw new EntityDoesNotExistException("Cannot count all groups",e);
        }
    }

}
