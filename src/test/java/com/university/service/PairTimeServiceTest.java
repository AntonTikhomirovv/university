package com.university.service;

import com.university.dao.sql.PairTimeDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.PairTime;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PairTimeServiceTest {

    @Mock
    private PairTimeDAO pairTimeDAO;

    @InjectMocks
    private PairTimeService pairTimeService;

    private final PairTime test = new PairTime(1, LocalTime.now(), LocalTime.now());

    public PairTimeServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_should_pass() {
        List<PairTime> expected = new ArrayList<>();
        expected.add(new PairTime(1, LocalTime.now(), LocalTime.now()));
        expected.add(new PairTime(2, LocalTime.now(), LocalTime.now()));
        expected.add(new PairTime(3, LocalTime.now(), LocalTime.now()));
        when(pairTimeDAO.getAll()).thenReturn(expected);
        assertThat(pairTimeService.getAll()).isEqualTo(expected);
    }

    @Test
    void findAll_should_return_empty_list() {
        List<PairTime> source = new ArrayList<>();
        when(pairTimeDAO.getAll()).thenReturn(source);
        assertThat(pairTimeService.getAll()).isEqualTo(source);
    }

    @Test
    void create_should_pass() {
        doNothing().when(pairTimeDAO).create(test);
        pairTimeService.create(test);
        verify(pairTimeDAO, times(1)).create(test);
    }

    @Test
    void create_should_throw_exception() {
        doThrow(new DAOException("test")).when(pairTimeDAO).create(test);
        assertThrows(EntityIsNotUniqueException.class, () -> pairTimeService.create(test));
    }

    @Test
    void get_should_pass() {
        when(pairTimeDAO.get(test.getPairNumber())).thenReturn(test);
        assertThat(pairTimeService.get(test.getPairNumber())).isEqualTo(test);
        verify(pairTimeDAO, times(1)).get(test.getPairNumber());
    }

    @Test
    void get_should_throw_exception() {
        when(pairTimeDAO.get(test.getPairNumber())).thenThrow(new EntityDoesNotExistException("test"));
        assertThrows(EntityDoesNotExistException.class, () -> pairTimeService.get(test.getPairNumber()));
    }

    @Test
    void update_should_pass() {
        doNothing().when(pairTimeDAO).update(test, test.getPairNumber());
        pairTimeService.update(test, test.getPairNumber());
        verify(pairTimeDAO, times(1)).update(test, test.getPairNumber());
    }
    @Test
    void update_should_throw_exception() {
        doThrow(new DAOException("test")).when(pairTimeDAO).update(test, test.getPairNumber());
        assertThrows(EntityDoesNotExistException.class, () -> pairTimeService.update(test, test.getPairNumber()));
    }

    @Test
    void delete_should_pass() {
        doNothing().when(pairTimeDAO).delete(test.getPairNumber());
        pairTimeService.delete(test.getPairNumber());
        verify(pairTimeDAO, times(1)).delete(test.getPairNumber());
    }

    @Test
    void delete_should_throw_exception() {
        doThrow(new DAOException("test")).when(pairTimeDAO).delete(test.getPairNumber());
        assertThrows(EntityDoesNotExistException.class, () -> pairTimeService.delete(test.getPairNumber()));
    }

    @Test
    void getAll_limit_should_pass() {
        when(pairTimeDAO.getAll()).thenReturn(new ArrayList<>());
        pairTimeService.getAll(1, 5);
        verify(pairTimeDAO, times(1)).getAll(anyInt(), anyInt());
    }

    @Test
    void count_all_should_pass() {
        when(pairTimeDAO.countAll()).thenReturn(1);
        assertThat(1).isEqualTo(pairTimeService.countAll());
        verify(pairTimeDAO, times(1)).countAll();
    }
}
