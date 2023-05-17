package com.university.service;

import com.university.dao.sql.AudienceDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Audience;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class AudienceServiceTest {

    @Mock
    private AudienceDAO audienceDAO;

    @InjectMocks
    private AudienceService audienceService;

    public AudienceServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    private final Audience test = new Audience(1, 1, 1);

    @Test
    void findAll_should_pass() {
        List<Audience> expected = new ArrayList<>();
        expected.add(new Audience(1, 1, 1105));
        expected.add(new Audience(2, 1, 2223));
        expected.add(new Audience(3, 1, 2310));
        when(audienceDAO.getAll()).thenReturn(expected);
        assertThat(audienceService.getAll()).isNotEmpty().isEqualTo(expected);
    }

    @Test
    void findAll_should_return_empty_list() {
        List<Audience> source = new ArrayList<>();
        when(audienceDAO.getAll()).thenReturn(source);
        assertThat(audienceService.getAll()).isEqualTo(source);
    }

    @Test
    void create_should_pass() {
        doNothing().when(audienceDAO).create(test);
        audienceService.create(test);
        verify(audienceDAO, times(1)).create(test);
    }

    @Test
    void create_should_throw_exception() {
        doThrow(new DAOException("test")).when(audienceDAO).create(test);
        String message = String.format("Cannot create new audience," +
                "audience with id %d is already exists", test.getId());
        assertThrows(EntityIsNotUniqueException.class, () -> audienceService.create(test), message);
    }

    @Test
    void get_should_pass() {
        when(audienceDAO.get(test.getId())).thenReturn(test);
        assertThat(audienceService.get(test.getId())).isEqualTo(test);
        verify(audienceDAO, times(1)).get(test.getId());
    }

    @Test
    void get_should_throw_exception() {
        when(audienceDAO.get(test.getId())).thenThrow(new EntityDoesNotExistException("test"));
        String message = String.format("Cannot get audience. " +
                "Audience with id %d doesn't exits", test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> audienceService.get(test.getId()), message);
    }

    @Test
    void update_should_pass() {
        doNothing().when(audienceDAO).update(test, test.getId());
        audienceService.update(test, test.getId());
        verify(audienceDAO, times(1)).update(test, test.getId());
    }
    @Test
    void update_should_throw_exception() {
        doThrow(new DAOException("test")).when(audienceDAO).update(test, test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> audienceService.update(test, test.getId()));
    }

    @Test
    void delete_should_pass() {
        doNothing().when(audienceDAO).delete(test.getId());
        audienceService.delete(test.getId());
        verify(audienceDAO, times(1)).delete(test.getId());
    }

    @Test
    void delete_should_throw_exception() {
        doThrow(new DAOException("test")).when(audienceDAO).delete(test.getId());
        assertThrows(EntityDoesNotExistException.class, () -> audienceService.delete(test.getId()));
    }

    @Test
    void getAll_limit_should_pass() {
        when(audienceDAO.getAll()).thenReturn(new ArrayList<>());
        audienceService.getAll(1, 5);
        verify(audienceDAO, times(1)).getAll(anyInt(), anyInt());
    }

    @Test
    void count_all_should_pass() {
        when(audienceDAO.countAll()).thenReturn(1);
        assertThat(1).isEqualTo(audienceService.countAll());
        verify(audienceDAO, times(1)).countAll();
    }
}