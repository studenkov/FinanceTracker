package org.kaorun.financetracker.controller;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.TransactionModel;
import org.kaorun.financetracker.service.AccountService;
import org.kaorun.financetracker.service.CategoryService;
import org.kaorun.financetracker.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final AccountService accountService;

    public TransactionController(TransactionService transactionService, CategoryService categoryService, AccountService accountService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @GetMapping
    public String getTransactions(Model model) {
        model.addAttribute("transactions", transactionService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("transaction", new TransactionModel());
        return "transactionList";
    }

    @PostMapping("/add")
    public String addTransaction(@Valid @ModelAttribute("transaction") TransactionModel transaction, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("transactions", transactionService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("accounts", accountService.findAll());
            return "transactionList";
        }
        transactionService.add(transaction);
        return "redirect:/transactions";
    }
}