package org.kaorun.financetracker.controller;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.TransactionModel;
import org.kaorun.financetracker.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) String query,
                         @RequestParam(defaultValue = "0") int page) {
        int size = 10;
        List<TransactionModel> transactions;

        // Логика поиска: пробуем найти по ID, если это не число — ищем по заметке (Note)
        if (query != null && !query.trim().isEmpty()) {
            try {
                long id = Long.parseLong(query.trim());
                TransactionModel t = service.findById(id);
                transactions = (t != null) ? List.of(t) : List.of();
            } catch (NumberFormatException e) {
                transactions = service.findByNote(query.trim());
            }
        } else {
            transactions = service.findPage(page, size);
        }

        model.addAttribute("transactions", transactions);
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);

        return "transactionList";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute TransactionModel transaction,
                      BindingResult result,
                      RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Некорректные данные транзакции!");
            return "redirect:/transactions";
        }

        service.add(transaction);
        return "redirect:/transactions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        service.delete(id);
        return "redirect:/transactions";
    }
}