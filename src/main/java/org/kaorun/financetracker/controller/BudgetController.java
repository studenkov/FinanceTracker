package org.kaorun.financetracker.controller;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.BudgetModel;
import org.kaorun.financetracker.service.BudgetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService service;

    public BudgetController(BudgetService service) {
        this.service = service;
    }

    @GetMapping
    public String showBudgets(Model model, @RequestParam(defaultValue = "0") int page) {
        int size = 10;
        List<BudgetModel> pagedBudgets = service.findPage(page, size);

        model.addAttribute("budgets", pagedBudgets);
        model.addAttribute("currentPage", page);
        model.addAttribute("hasNext", pagedBudgets.size() == size);

        return "budgetList";
    }

    @PostMapping("/add")
    public String addBudget(@Valid @ModelAttribute BudgetModel budget,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Ошибка ввода данных. Проверьте правильность заполнения полей.");
            return "redirect:/budgets";
        }

        service.add(budget);
        return "redirect:/budgets";
    }

    @PostMapping("/delete/{id}")
    public String deleteBudget(@PathVariable long id) {
        service.delete(id);
        return "redirect:/budgets";
    }
}