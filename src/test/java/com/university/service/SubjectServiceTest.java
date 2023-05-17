package com.university.service;

import com.university.dao.sql.SubjectDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Subject;
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

public class SubjectServiceTest {

    @Mock
    private SubjectDAO subjectDAO;

    @InjectMocks
    private SubjectService subjectService;

    private final Subject test = new Subject(1, "test");

    public SubjectServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_should_pass() {
        List<Subject> expected = new ArrayList<>();
        expected.add(new Subject(1, "test1"));
        expected.add(new Subject(2, "test2"));
        expected.add(new Subject(3, "test3"));
        when(subjectDAO.getAll()).thenReturn(expected);
        assertThat(subjectService.getAll()).isEqualTo(expected);
    }

    @Test
    void findAll_should_return_empty_list() {
        List<Subject> source = new ArrayList<>();
        when(subjectDAO.getAll()).thenReturn(source);
        assertThat(subjectService.getAll()).isEqualTo(source);
    }

    @Test
    void create_should_pass() {
        doNothing().when(subjectDAO).create(test);
        subjectService.create(test);
        verify(subjectDAO, times(1)).create(test);
    }

    @Test
    void create_should_throw_exception() {
        doThrow(new DAOException("test")).when(subjectDAO).create(test);
        assertThrows(EntityIsNotUniqueException.class, () -> subjectService.create(test));
    }

    @Test
    void get_should_pass() {
        when(subjectDAO.get(test.getId())).thenReturn(test);
        assertThat(subjectService.get(test.getId())).isEqualTo(test);
        verify(subjectDAO, times(1)).get(test.getId());
    }

    @Test
    void get_should_throw_exception() {
        when(subjectDAO.get(test.getId())).thenThrow(new EntityDoesNotExistException("test"));
        assertThrows(EntityDoesNotExistException.class, () -> subjectService.get(test.getId()));
    }

    @Test
    void update_should_pass() {
        doNothing().when(subjectDAO).update(test, test.getId());
        subjectService.update(test, test.getId());
        verify(subjectDAO, times(1)).update(test, test.getId());
    }
    @Test
    void update_should_throw_exception() {
        doThrow(new DAOException("test")).when(subjectDAO).update(test, test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> subjectService.update(test, test.getId()));
    }

    @Test
    void delete_should_pass() {
        doNothing().when(subjectDAO).delete(test.getId());
        subjectService.delete(test.getId());
        verify(subjectDAO, times(1)).delete(test.getId());
    }

    @Test
    void delete_should_throw_exception() {
        doThrow(new DAOException("test")).when(subjectDAO).delete(test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> subjectService.delete(test.getId()));
    }

    @Test
    void getAll_limit_should_pass() {
        when(subjectDAO.getAll()).thenReturn(new ArrayList<>());
        subjectService.getAll(1, 5);
        verify(subjectDAO, times(1)).getAll(anyInt(), anyInt());
    }

    @Test
    void count_all_should_pass() {
        when(subjectDAO.countAll()).thenReturn(1);
        assertThat(1).isEqualTo(subjectService.countAll());
        verify(subjectDAO, times(1)).countAll();
    }
}
