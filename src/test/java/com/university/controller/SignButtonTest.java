package com.university.controller;

import com.university.model.Audience;
import com.university.model.Group;
import com.university.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

public class SignButtonTest {

    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupsController groupsController;

    private static final Group TEST = new Group(1, 1);

    public SignButtonTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setMocks() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupsController).build();
    }


    @Test
    void getAllGroups_should_return_page_with_all_groups() throws Exception {
        List<Group> source = new ArrayList<>();
        source.add(new Group(1, 1));
        source.add(new Group(2, 2));
        source.add(new Group(3, 3));
        when(groupService.getAll(5, 0)).thenReturn(source);
        when(groupService.countAll()).thenReturn(3);
        mockMvc.perform(get("/groups/"))
                .andExpect(view().name("groups"))
                .andExpect(model().attribute("groups", source));
    }

    @Test
    void getPaginated_should_pass() throws Exception {
        List<Group> source = new ArrayList<>();
        source.add(new Group(1, 1));
        source.add(new Group(2, 2));
        source.add(new Group(3, 3));
        when(groupService.getAll()).thenReturn(source);
        when(groupService.countAll()).thenReturn(3);
        mockMvc.perform(get("/groups/page/1"))
                .andExpect(view().name("groups"))
                .andExpect(model().attribute("currentPage", 1))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("pageSize", 5));
    }

    @Test
    void update_get_method_should_pass() throws Exception {
        when(groupService.get(TEST.getId())).thenReturn(TEST);
        mockMvc.perform(get("/groups/update/" + TEST.getId()))
                .andExpect(view().name("update/group-update"))
                .andExpect(model().attribute("group", TEST));
    }

    @Test
    void update_post_method_should_pass() throws Exception {
        doNothing().when(groupService).update(TEST, TEST.getId());
        mockMvc.perform(post("/groups/update/" + TEST.getId()))
                .andExpect(view().name("redirect:/groups"));
    }

    @Test
    void create_post_method_should_pass() throws Exception {
        doNothing().when(groupService).create(TEST);
        mockMvc.perform(post("/groups/new"))
                .andExpect(view().name("redirect:/groups"));
    }

    @Test
    void delete_post_method_should_pass() throws Exception {
        doNothing().when(groupService).delete(TEST.getId());
        mockMvc.perform(post("/groups/delete/" + TEST.getId()))
                .andExpect(view().name("redirect:/groups"));
    }
}
