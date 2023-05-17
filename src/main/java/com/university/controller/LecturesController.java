package com.university.controller;

import com.university.model.Group;
import com.university.model.Lecture;
import com.university.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lectures")
public class LecturesController {

    private LectureService lectureService;

    private static final int PAGE_SIZE = 5;

    @Autowired
    public LecturesController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("")
    public String getAllLectures(Model model) {
        model.addAttribute("lectures", lectureService.getAll());
        return getPaginated(1, model);
    }

    @GetMapping("page/{pageNo}")
    public String getPaginated(@PathVariable(value = "pageNo") int pageNo,
                               Model model) {
        int totalItems = lectureService.countAll();
        int totalPages = totalItems % PAGE_SIZE == 0 ? totalItems / PAGE_SIZE : (totalItems / PAGE_SIZE) + 1;

        List<Lecture> pagedLectures = lectureService.getAll(PAGE_SIZE, (pageNo - 1) * PAGE_SIZE);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", PAGE_SIZE);
        model.addAttribute("lectures", pagedLectures);
        return "lectures";
    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("lecture", lectureService.get(id));
        return "update/lecture-update";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("group") Lecture lecture) {
        lectureService.create(lecture);
        return "redirect:/lectures";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        lectureService.delete(id);
        return "redirect:/lectures";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("group") Lecture lecture, @PathVariable("id") int id) {
        lectureService.update(lecture, id);
        return "redirect:/lectures";
    }
}
