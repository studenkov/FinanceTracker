package org.kaorun.financetracker.controller;

import org.kaorun.financetracker.model.TypeModel;
import org.kaorun.financetracker.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TypeController {

    @Autowired
    private TypeService service;

    @GetMapping("/types")
    public String getAll(Model model) {
        model.addAttribute("types", service.findAll());
        return "typeList";
    }

    @GetMapping("/types/search")
    public String search(@RequestParam String title, Model model) {
        model.addAttribute("types", service.findByTitle(title));
        return "typeList";
    }

    @PostMapping("/types/add")
    public String add(@RequestParam String title) {
        service.add(new TypeModel(title));
        return "redirect:/types";
    }
}
