package com.university.controller;

import com.university.model.Teacher;
import com.university.model.Subject;
import com.university.model.Teacher;
import com.university.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/teachers")
public class TeachersController {

    private TeacherService teacherService;

    private static final int PAGE_SIZE = 5;

    @Autowired
    public TeachersController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("")
    public String getAllTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAll());
        return getPaginated(1, model);
    }

    @GetMapping("/page/{pageNo}")
    public String getPaginated(@PathVariable(value = "pageNo") int pageNo,
                               Model model) {
        int totalItems = teacherService.countAll();
        int totalPages = totalItems % PAGE_SIZE == 0 ? totalItems / PAGE_SIZE : (totalItems / PAGE_SIZE) + 1;

        List<Teacher> pagedTeachers = teacherService.getAll(PAGE_SIZE, (pageNo - 1) * PAGE_SIZE);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", PAGE_SIZE);
        model.addAttribute("teachers", pagedTeachers);
        return "teachers";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("teacher", teacherService.get(id));
        return "update/teacher-update";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("teacher") Teacher teacher) {
        teacherService.create(teacher);
        return "redirect:/teachers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        teacherService.delete(id);
        return "redirect:/teachers";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("teacher") Teacher teacher, @PathVariable("id") int id) {
        teacherService.update(teacher, id);
        return "redirect:/teachers";
    }
}
