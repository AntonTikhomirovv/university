package com.university.controller;

import com.university.model.Subject;
import com.university.model.Lecture;
import com.university.model.Subject;
import com.university.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/subjects")
public class SubjectsController {

    private SubjectService subjectService;

    private static final int PAGE_SIZE = 5;

    @Autowired
    public SubjectsController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("")
    public String getAllSubjects(Model model) {
        model.addAttribute("subjects", subjectService.getAll());
        return getPaginated(1, model);
    }

    @GetMapping("/page/{pageNo}")
    public String getPaginated(@PathVariable(value = "pageNo") int pageNo,
                               Model model) {
        int totalItems = subjectService.countAll();
        int totalPages = totalItems % PAGE_SIZE == 0 ? totalItems / PAGE_SIZE : (totalItems / PAGE_SIZE) + 1;

        List<Subject> pagedSubjects = subjectService.getAll(PAGE_SIZE, (pageNo - 1) * PAGE_SIZE);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", PAGE_SIZE);
        model.addAttribute("subjects", pagedSubjects);
        return "subjects";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("subject", subjectService.get(id));
        return "update/subject-update";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("subject") Subject subject) {
        subjectService.create(subject);
        return "redirect:/subjects";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        subjectService.delete(id);
        return "redirect:/subjects";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("subject") Subject subject, @PathVariable("id") int id) {
        subjectService.update(subject, id);
        return "redirect:/subjects";
    }
}
