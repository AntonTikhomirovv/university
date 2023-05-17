package com.university.service;

import com.university.dao.sql.StudentDAO;
import com.university.exception.DAOException;
import com.university.exception.EntityDoesNotExistException;
import com.university.exception.EntityIsNotUniqueException;
import com.university.model.Student;
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

public class StudentServiceTest {

    @Mock
    private StudentDAO studentDAO;

    @InjectMocks
    private StudentService studentService;

    private final Student test = new Student(1, "test", "test", "test", 1, 1);

    public StudentServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_should_pass() {
        List<Student> expected = new ArrayList<>();
        expected.add(new Student(1, "test1", "test1", "test1", 1, 1));
        expected.add(new Student(2, "test2", "test2", "test2", 2, 2));
        expected.add(new Student(3, "test3", "test3", "test3", 3, 3));
        when(studentDAO.getAll()).thenReturn(expected);
        assertThat(studentService.getAll()).isEqualTo(expected);
    }

    @Test
    void findAll_should_return_empty_list() {
        List<Student> source = new ArrayList<>();
        when(studentDAO.getAll()).thenReturn(source);
        assertThat(studentService.getAll()).isEqualTo(source);
    }

    @Test
    void create_should_pass() {
        doNothing().when(studentDAO).create(test);
        studentService.create(test);
        verify(studentDAO, times(1)).create(test);
    }

    @Test
    void create_should_throw_exception() {
        doThrow(new DAOException("test")).when(studentDAO).create(test);
        assertThrows(EntityIsNotUniqueException.class, () -> studentService.create(test));
    }

    @Test
    void get_should_pass() {
        when(studentDAO.get(test.getStudentId())).thenReturn(test);
        assertThat(studentService.get(test.getStudentId())).isEqualTo(test);
        verify(studentDAO, times(1)).get(test.getStudentId());
    }

    @Test
    void get_should_throw_exception() {
        when(studentDAO.get(test.getStudentId())).thenThrow(new EntityDoesNotExistException("test"));
        assertThrows(EntityDoesNotExistException.class, () -> studentService.get(test.getStudentId()));
    }

    @Test
    void update_should_pass() {
        doNothing().when(studentDAO).update(test, test.getStudentId());
        studentService.update(test, test.getStudentId());
        Mockito.verify(studentDAO, times(1)).update(test, test.getStudentId());
    }
    @Test
    void update_should_throw_exception() {
        doThrow(new DAOException("test")).when(studentDAO).update(test, test.getStudentId());
        assertThrows(EntityDoesNotExistException.class, () -> studentService.update(test, test.getStudentId()));
    }

    @Test
    void delete_should_pass() {
        doNothing().when(studentDAO).delete(test.getStudentId());
        studentService.delete(test.getStudentId());
        verify(studentDAO, times(1)).delete(test.getStudentId());
    }

    @Test
    void delete_should_throw_exception() {
        doThrow(new DAOException("test")).when(studentDAO).delete(test.getStudentId());
        assertThrows(EntityDoesNotExistException.class, () -> studentService.delete(test.getStudentId()));
    }

    @Test
    void getAll_limit_should_pass() {
        when(studentDAO.getAll()).thenReturn(new ArrayList<>());
        studentService.getAll(1, 5);
        verify(studentDAO, times(1)).getAll(anyInt(), anyInt());
    }

    @Test
    void count_all_should_pass() {
        when(studentDAO.countAll()).thenReturn(1);
        assertThat(1).isEqualTo(studentService.countAll());
        verify(studentDAO, times(1)).countAll();
    }
}
