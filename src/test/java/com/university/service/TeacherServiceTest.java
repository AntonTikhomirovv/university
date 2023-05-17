package com.university.service;

import com.university.dao.sql.TeacherDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Teacher;
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

public class TeacherServiceTest {

    @Mock
    private TeacherDAO teacherDAO;

    @InjectMocks
    private TeacherService teacherService;

    private final Teacher test = new Teacher(1, "test", "test", "test");

    public TeacherServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_should_pass() {
        List<Teacher> expected = new ArrayList<>();
        expected.add(new Teacher(1, "test1", "test1", "test1"));
        expected.add(new Teacher(2, "test2", "test2", "test2"));
        expected.add(new Teacher(3, "test3", "test3", "test3"));
        when(teacherDAO.getAll()).thenReturn(expected);
        assertThat(teacherService.getAll()).isEqualTo(expected);
    }

    @Test
    void findAll_should_return_empty_list() {
        List<Teacher> source = new ArrayList<>();
        when(teacherDAO.getAll()).thenReturn(source);
        assertThat(teacherService.getAll()).isEqualTo(source);
    }

    @Test
    void create_should_pass() {
        doNothing().when(teacherDAO).create(test);
        teacherService.create(test);
        verify(teacherDAO, times(1)).create(test);
    }

    @Test
    void create_should_throw_exception() {
        doThrow(new DAOException("test")).when(teacherDAO).create(test);
        assertThrows(EntityIsNotUniqueException.class, () -> teacherService.create(test));
    }

    @Test
    void get_should_pass() {
        when(teacherDAO.get(test.getId())).thenReturn(test);
        assertThat(teacherService.get(test.getId())).isEqualTo(test);
        verify(teacherDAO, times(1)).get(test.getId());
    }

    @Test
    void get_should_throw_exception() {
        when(teacherDAO.get(test.getId())).thenThrow(new EntityDoesNotExistException("test"));
        assertThrows(EntityDoesNotExistException.class, () -> teacherService.get(test.getId()));
    }

    @Test
    void update_should_pass() {
        doNothing().when(teacherDAO).update(test, test.getId());
        teacherService.update(test, test.getId());
        verify(teacherDAO, times(1)).update(test, test.getId());
    }
    @Test
    void update_should_throw_exception() {
        doThrow(new DAOException("test")).when(teacherDAO).update(test, test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> teacherService.update(test, test.getId()));
    }

    @Test
    void delete_should_pass() {
        doNothing().when(teacherDAO).delete(test.getId());
        teacherService.delete(test.getId());
        verify(teacherDAO, times(1)).delete(test.getId());
    }

    @Test
    void delete_should_throw_exception() {
        doThrow(new DAOException("test")).when(teacherDAO).delete(test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> teacherService.delete(test.getId()));
    }

    @Test
    void getAll_limit_should_pass() {
        when(teacherDAO.getAll()).thenReturn(new ArrayList<>());
        teacherService.getAll(1, 5);
        verify(teacherDAO, times(1)).getAll(anyInt(), anyInt());
    }

    @Test
    void count_all_should_pass() {
        when(teacherDAO.countAll()).thenReturn(1);
        assertThat(1).isEqualTo(teacherService.countAll());
        verify(teacherDAO, times(1)).countAll();
    }
}
