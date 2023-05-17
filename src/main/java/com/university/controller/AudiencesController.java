package com.university.controller;

import com.university.model.Audience;
import com.university.service.AudienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/audiences")
public class AudiencesController {

    private AudienceService audienceService;

    private static final int PAGE_SIZE = 5;

    @Autowired
    public AudiencesController(AudienceService audienceService) {
        this.audienceService = audienceService;
    }

    @GetMapping("")
    public String getAllAudiences(Model model) {
        return getPaginated(1, model);
    }

    @GetMapping("/page/{pageNo}")
    public String getPaginated(@PathVariable(value = "pageNo") int pageNo,
                               Model model) {
        int totalItems = audienceService.countAll();
        int totalPages = totalItems % PAGE_SIZE == 0 ? totalItems / PAGE_SIZE : (totalItems / PAGE_SIZE) + 1;

        List<Audience> pagedAudience = audienceService.getAll(PAGE_SIZE, (pageNo - 1) * PAGE_SIZE);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", PAGE_SIZE);
        model.addAttribute("audiences", pagedAudience);
        return "audiences";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("audience", audienceService.get(id));
        return "update/audience-update";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("audience") Audience audience) {
        audienceService.create(audience);
        return "redirect:/audiences";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        audienceService.delete(id);
        return "redirect:/audiences";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("audience") Audience audience, @PathVariable("id") int id) {
        audienceService.update(audience, id);
        return "redirect:/audiences";
    }
}
