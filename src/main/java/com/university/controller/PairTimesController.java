package com.university.controller;

import com.university.model.Group;
import com.university.model.Lecture;
import com.university.model.PairTime;
import com.university.service.PairTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pair_times")
public class PairTimesController {

    private PairTimeService pairTimeService;

    private static final int PAGE_SIZE = 5;

    @Autowired
    public PairTimesController(PairTimeService pairTimeService) {
        this.pairTimeService = pairTimeService;
    }

    @GetMapping("")
    public String getAllPairTimes(Model model) {
        model.addAttribute("pair_times", pairTimeService.getAll());
        return "pair_times";
    }

    @GetMapping("/page/{pageNo}")
    public String getPaginated(@PathVariable(value = "pageNo") int pageNo,
                               Model model) {
        int totalItems = pairTimeService.countAll();
        int totalPages = totalItems % PAGE_SIZE == 0 ? totalItems / PAGE_SIZE : (totalItems / PAGE_SIZE) + 1;

        List<PairTime> pagedPairTimes = pairTimeService.getAll(PAGE_SIZE, (pageNo - 1) * PAGE_SIZE);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", PAGE_SIZE);
        model.addAttribute("pair_times", pagedPairTimes);
        return "pair_times";
    }

    @GetMapping("/update/{pairNumber}")
    public String update(@PathVariable("pairNumber") int pairNumber, Model model) {
        model.addAttribute("pair_time", pairTimeService.get(pairNumber));
        return "update/pair-time-update";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("pairTime") PairTime pairTime) {
        pairTimeService.create(pairTime);
        return "redirect:/pair_times";
    }

    @PostMapping("/delete/{pairNumber}")
    public String delete(@PathVariable("pairNumber") int pairNumber) {
        pairTimeService.delete(pairNumber);
        return "redirect:/pair_times";
    }

    @PostMapping("/update/{pairNumber}")
    public String update(@ModelAttribute("pairTime") PairTime pairTime, @PathVariable("pairNumber") int pairNumber) {
        pairTimeService.update(pairTime, pairNumber);
        return "redirect:/pair_times";
    }
}
