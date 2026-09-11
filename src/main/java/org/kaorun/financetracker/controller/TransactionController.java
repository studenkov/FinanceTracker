package org.kaorun.financetracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kaorun.financetracker.model.TransactionModel;
import org.kaorun.financetracker.service.AccountService;
import org.kaorun.financetracker.service.CategoryService;
import org.kaorun.financetracker.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final AccountService accountService;

    @GetMapping
    public String getTransactions(
            Model model,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page
    ) {
        List<TransactionModel> transactions;
        if (query != null && !query.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(query.trim());
                TransactionModel found = transactionService.findById(id);
                transactions = found != null ? List.of(found) : List.of();
            } catch (NumberFormatException e) {
                transactions = transactionService.findByNote(query.trim());
            }
        } else {
            transactions = transactionService.findAll();
        }

        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("query", query);
        if (!model.containsAttribute("transaction")) {
            model.addAttribute("transaction", new TransactionModel());
        }
        return "transactionList";
    }

    @PostMapping("/add")
    public String addTransaction(
            @Valid @ModelAttribute("transaction") TransactionModel transaction,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Пожалуйста, проверьте корректность заполненных полей!");
            return "redirect:/transactions";
        }
        transactionService.add(transaction);
        return "redirect:/transactions";
    }

    @PostMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return "redirect:/transactions";
    }
}