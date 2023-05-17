package com.university.service;

import com.university.dao.sql.LectureDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Lecture;
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

public class LectureServiceTest {

    @Mock
    private LectureDAO lectureDAO;

    @InjectMocks
    private LectureService lectureService;

    private final Lecture test = new Lecture(1, 1, 1, 1);

    public LectureServiceTest() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void findAll_should_pass() {
        List<Lecture> expected = new ArrayList<>();
        expected.add(new Lecture(1, 1, 1, 1));
        expected.add(new Lecture(2, 2, 2, 2));
        expected.add(new Lecture(3, 3, 3, 3));
        when(lectureDAO.getAll()).thenReturn(expected);
        assertThat(lectureService.getAll()).isEqualTo(expected);
    }

    @Test
    void findAll_should_return_empty_list() {
        List<Lecture> source = new ArrayList<>();
        when(lectureDAO.getAll()).thenReturn(source);
        assertThat(lectureService.getAll()).isEqualTo(source);
    }

    @Test
    void create_should_pass() {
        doNothing().when(lectureDAO).create(test);
        lectureService.create(test);
        verify(lectureDAO, times(1)).create(test);
    }

    @Test
    void create_should_throw_exception() {
        doThrow(new DAOException("test")).when(lectureDAO).create(test);
        assertThrows(EntityIsNotUniqueException.class, () -> lectureService.create(test));
    }

    @Test
    void get_should_pass() {
        when(lectureDAO.get(test.getId())).thenReturn(test);
        assertThat(lectureService.get(test.getId())).isEqualTo(test);
        verify(lectureDAO, times(1)).get(test.getId());
    }

    @Test
    void get_should_throw_exception() {
        when(lectureDAO.get(test.getId())).thenThrow(new EntityDoesNotExistException("test"));
        assertThrows(EntityDoesNotExistException.class, () -> lectureService.get(test.getId()));
    }

    @Test
    void update_should_pass() {
        doNothing().when(lectureDAO).update(test, test.getId());
        lectureService.update(test, test.getId());
        verify(lectureDAO, times(1)).update(test, test.getId());
    }
    @Test
    void update_should_throw_exception() {
        doThrow(new DAOException("test")).when(lectureDAO).update(test, test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> lectureService.update(test, test.getId()));
    }

    @Test
    void delete_should_pass() {
        doNothing().when(lectureDAO).delete(test.getId());
        lectureService.delete(test.getId());
        verify(lectureDAO, times(1)).delete(test.getId());
    }

    @Test
    void delete_should_throw_exception() {
        doThrow(new DAOException("test")).when(lectureDAO).delete(test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> lectureService.delete(test.getId()));
    }

    @Test
    void getAll_limit_should_pass() {
        when(lectureDAO.getAll()).thenReturn(new ArrayList<>());
        lectureService.getAll(1, 5);
        verify(lectureDAO, times(1)).getAll(anyInt(), anyInt());
    }

    @Test
    void count_all_should_pass() {
        when(lectureDAO.countAll()).thenReturn(1);
        assertThat(1).isEqualTo(lectureService.countAll());
        verify(lectureDAO, times(1)).countAll();
    }
}
