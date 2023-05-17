package com.university.controller;

import com.university.model.Audience;
import com.university.model.Student;
import com.university.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ChangeTruckTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentsController studentsController;

    private static final Student TEST = new Student(1, "TEST", "TEST", "TEST", 1, 1);

    public ChangeTruckTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setMocks() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentsController).build();
    }


    @Test
    void getAllStudents_should_return_page_with_all_students() throws Exception {
        List<Student> source = new ArrayList<>();
        source.add(new Student(1, "test1", "test1", "test1", 1, 1));
        source.add(new Student(2, "test2", "test2", "test2", 2, 2));
        source.add(new Student(3, "test3", "test3", "test3", 3, 3));
        when(studentService.getAll(5, 0)).thenReturn(source);
        when(studentService.countAll()).thenReturn(3);
        mockMvc.perform(get("/students/"))
                .andExpect(view().name("students"))
                .andExpect(model().attribute("students", source));
    }

    @Test
    void getPaginated_should_pass() throws Exception {
        List<Student> source = new ArrayList<>();
        source.add(new Student(1, "test1", "test1", "test1", 1, 1));
        source.add(new Student(2, "test2", "test2", "test2", 2, 2));
        source.add(new Student(3, "test3", "test3", "test3", 3, 3));
        when(studentService.getAll()).thenReturn(source);
        when(studentService.countAll()).thenReturn(3);
        mockMvc.perform(get("/students/page/1"))
                .andExpect(view().name("students"))
                .andExpect(model().attribute("currentPage", 1))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("pageSize", 5));
    }

    @Test
    void update_get_method_should_pass() throws Exception {
        when(studentService.get(TEST.getStudentId())).thenReturn(TEST);
        mockMvc.perform(get("/students/update/" + TEST.getStudentId()))
                .andExpect(view().name("update/student-update"))
                .andExpect(model().attribute("student", TEST));
    }

    @Test
    void update_post_method_should_pass() throws Exception {
        doNothing().when(studentService).update(TEST, TEST.getStudentId());
        mockMvc.perform(post("/students/update/" + TEST.getStudentId()))
                .andExpect(view().name("redirect:/students"));
    }

    @Test
    void create_post_method_should_pass() throws Exception {
        doNothing().when(studentService).create(TEST);
        mockMvc.perform(post("/students/new"))
                .andExpect(view().name("redirect:/students"));
    }

    @Test
    void delete_post_method_should_pass() throws Exception {
        doNothing().when(studentService).delete(TEST.getStudentId());
        mockMvc.perform(post("/students/delete/" + TEST.getStudentId()))
                .andExpect(view().name("redirect:/students"));
    }
}
