package com.university.controller;

import com.university.model.Audience;
import com.university.model.Group;
import com.university.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/groups")
public class GroupsController {

    private static final int PAGE_SIZE = 5;

    private GroupService groupService;

    @Autowired
    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("")
    public String getAllGroups(Model model) {
        model.addAttribute("groups", groupService.getAll());
        return getPaginated(1, model);
    }

    @GetMapping("/page/{pageNo}")
    public String getPaginated(@PathVariable(value = "pageNo") int pageNo,
                               Model model) {
        int totalItems = groupService.countAll();
        int totalPages = totalItems % PAGE_SIZE == 0 ? totalItems / PAGE_SIZE : (totalItems / PAGE_SIZE) + 1;

        List<Group> pagedGroups = groupService.getAll(PAGE_SIZE, (pageNo - 1) * PAGE_SIZE);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", PAGE_SIZE);
        model.addAttribute("groups", pagedGroups);
        return "groups";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupService.get(id));
        return "update/group-update";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("group") Group group) {
        groupService.create(group);
        return "redirect:/groups";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        groupService.delete(id);
        return "redirect:/groups";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("group") Group group, @PathVariable("id") int id) {
        groupService.update(group, id);
        return "redirect:/groups";
    }
}
