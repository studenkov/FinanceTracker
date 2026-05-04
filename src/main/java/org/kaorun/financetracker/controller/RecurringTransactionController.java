package org.kaorun.financetracker.controller;

import org.kaorun.financetracker.service.RecurringTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecurringTransactionController {

    @Autowired
    private RecurringTransactionService service;

    @GetMapping("/recurring")
    public String getAll(Model model) {
        model.addAttribute("recurring", service.findAll());
        return "recurringList";
    }
}
