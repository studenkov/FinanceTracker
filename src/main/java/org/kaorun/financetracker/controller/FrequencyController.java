package org.kaorun.financetracker.controller;

import lombok.RequiredArgsConstructor;
import org.kaorun.financetracker.service.FrequencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FrequencyController {

    private final FrequencyService service;

    @GetMapping("/frequencies")
    public String getAll(Model model) {
        model.addAttribute("frequencies", service.findAll());
        return "frequencyList";
    }
}
