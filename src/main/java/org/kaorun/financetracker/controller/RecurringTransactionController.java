package org.kaorun.financetracker.controller;

import lombok.RequiredArgsConstructor;
import org.kaorun.financetracker.service.RecurringTransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RecurringTransactionController {

    private final RecurringTransactionService service;

    @GetMapping("/recurring")
    public String getAll(Model model) {
        model.addAttribute("recurring", service.findAll());
        return "recurringList";
    }
}
