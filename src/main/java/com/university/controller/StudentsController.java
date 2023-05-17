package com.university.controller;

import com.university.model.Student;
import com.university.model.Student;
import com.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentsController {

    private StudentService studentService;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public String getAllStudents(Model model) {
        return getPaginated(1, model);
    }

    @GetMapping("/page/{pageNo}")
    public String getPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int totalItems = studentService.countAll();
        int totalPages = totalItems % PAGE_SIZE == 0 ? totalItems / PAGE_SIZE : (totalItems / PAGE_SIZE) + 1;

        List<Student> pagedStudents = studentService.getAll(PAGE_SIZE, (pageNo - 1) * PAGE_SIZE);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", PAGE_SIZE);
        model.addAttribute("students", pagedStudents);
        return "students";
    }

    @GetMapping("/update/{studentId}")
    public String update(@PathVariable("studentId") int studentId, Model model) {
        model.addAttribute("student", studentService.get(studentId));
        return "update/student-update";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("student") Student student) {
        studentService.create(student);
        return "redirect:/students";
    }

    @PostMapping("/delete/{studentId}")
    public String delete(@PathVariable("studentId") int studentId) {
        studentService.delete(studentId);
        return "redirect:/students";
    }

    @PostMapping("/update/{studentId}")
    public String update(@ModelAttribute("student") Student student, @PathVariable("studentId") int studentId) {
        studentService.update(student, studentId);
        return "redirect:/students";
    }
}
