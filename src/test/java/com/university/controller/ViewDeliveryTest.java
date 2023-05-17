package com.university.controller;

import com.university.model.Audience;
import com.university.service.AudienceService;
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

class ViewDeliveryTest {

    private MockMvc mockMvc;

    @Mock
    private AudienceService audienceService;

    @InjectMocks
    private AudiencesController audiencesController;

    private static final Audience TEST = new Audience(1, 1, 1);

    public ViewDeliveryTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setMocks() {
        mockMvc = MockMvcBuilders.standaloneSetup(audiencesController).build();
    }

    @Test
    void getPaginated_should_pass() throws Exception {
        List<Audience> source = new ArrayList<>();
        source.add(new Audience(1, 1, 1));
        source.add(new Audience(2, 2, 2));
        source.add(new Audience(3, 3, 3));
        when(audienceService.getAll()).thenReturn(source);
        when(audienceService.countAll()).thenReturn(3);
        mockMvc.perform(get("/audiences/page/1"))
                .andExpect(view().name("audiences"))
                .andExpect(model().attribute("currentPage", 1))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("pageSize", 5));
    }

    @Test
    void getAllAudiences_should_return_page_with_all_audiences() throws Exception {
        List<Audience> source = new ArrayList<>();
        source.add(new Audience(1, 1, 1));
        source.add(new Audience(2, 2, 2));
        source.add(new Audience(3, 3, 3));
        when(audienceService.getAll(5, 0)).thenReturn(source);
        when(audienceService.countAll()).thenReturn(3);
        mockMvc.perform(get("/audiences/"))
                .andExpect(view().name("audiences"))
                .andExpect(model().attribute("audiences", source));
    }

    @Test
    void update_get_method_should_pass() throws Exception {
        when(audienceService.get(TEST.getId())).thenReturn(TEST);
        mockMvc.perform(get("/audiences/update/" + TEST.getId()))
                .andExpect(view().name("update/audience-update"))
                .andExpect(model().attribute("audience", TEST));
    }

    @Test
    void update_post_method_should_pass() throws Exception {
        doNothing().when(audienceService).update(TEST, TEST.getId());
        mockMvc.perform(post("/audiences/update/" + TEST.getId()))
                .andExpect(view().name("redirect:/audiences"));
    }

    @Test
    void create_post_method_should_pass() throws Exception {
        doNothing().when(audienceService).create(TEST);
        mockMvc.perform(post("/audiences/new"))
                .andExpect(view().name("redirect:/audiences"));
    }

    @Test
    void delete_post_method_should_pass() throws Exception {
        doNothing().when(audienceService).delete(TEST.getId());
        mockMvc.perform(post("/audiences/delete/" + TEST.getId()))
                .andExpect(view().name("redirect:/audiences"));
    }
}