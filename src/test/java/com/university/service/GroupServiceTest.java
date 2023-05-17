package com.university.service;

import com.university.dao.sql.GroupDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Group;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroupServiceTest {

    @Mock
    private GroupDAO groupDAO;

    @InjectMocks
    private GroupService groupService;

    private final Group test = new Group(1, 1);

    public GroupServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_should_pass() {
        List<Group> expected = new ArrayList<>();
        expected.add(new Group(1, 1));
        expected.add(new Group(2, 2));
        expected.add(new Group(3, 3));
        Mockito.when(groupDAO.getAll()).thenReturn(expected);
        assertThat(groupService.getAll()).isEqualTo(expected);
    }

    @Test
    void findAll_should_return_empty_list() {
        List<Group> source = new ArrayList<>();
        when(groupDAO.getAll()).thenReturn(source);
        assertThat(groupService.getAll()).isEqualTo(source);
    }

    @Test
    void create_should_pass() {
        doNothing().when(groupDAO).create(test);
        groupService.create(test);
        verify(groupDAO, times(1)).create(test);
    }

    @Test
    void create_should_throw_exception() {
        doThrow(new DAOException("test")).when(groupDAO).create(test);
        assertThrows(EntityIsNotUniqueException.class, () -> groupService.create(test));
    }

    @Test
    void get_should_pass() {
        when(groupDAO.get(test.getId())).thenReturn(test);
        assertThat(groupService.get(test.getId())).isEqualTo(test);
        verify(groupDAO, times(1)).get(test.getId());
    }

    @Test
    void get_should_throw_exception() {
        when(groupDAO.get(test.getId())).thenThrow(new EntityDoesNotExistException("test"));
        assertThrows(EntityDoesNotExistException.class, () -> groupService.get(test.getId()));
    }

    @Test
    void update_should_pass() {
        doNothing().when(groupDAO).update(test, test.getId());
        groupService.update(test, test.getId());
        verify(groupDAO, times(1)).update(test, test.getId());
    }
    @Test
    void update_should_throw_exception() {
        doThrow(new DAOException("test")).when(groupDAO).update(test, test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> groupService.update(test, test.getId()));
    }

    @Test
    void delete_should_pass() {
        doNothing().when(groupDAO).delete(test.getId());
        groupService.delete(test.getId());
        verify(groupDAO, times(1)).delete(test.getId());
    }

    @Test
    void delete_should_throw_exception() {
        doThrow(new DAOException("test")).when(groupDAO).delete(test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> groupService.delete(test.getId()));
    }

    @Test
    void getAll_limit_should_pass() {
        when(groupDAO.getAll()).thenReturn(new ArrayList<>());
        groupService.getAll(1, 5);
        verify(groupDAO, times(1)).getAll(anyInt(), anyInt());
    }

    @Test
    void count_all_should_pass() {
        when(groupDAO.countAll()).thenReturn(1);
        assertThat(1).isEqualTo(groupService.countAll());
        verify(groupDAO, times(1)).countAll();
    }
}
