package org.kaorun.financetracker.controller;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.BudgetModel;
import org.kaorun.financetracker.service.BudgetService;
import org.kaorun.financetracker.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/budgets")
public class BudgetController {
    private final BudgetService budgetService;
    private final CategoryService categoryService;

    public BudgetController(BudgetService budgetService, CategoryService categoryService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showBudgets(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("allCategories", categoryService.findAll());
        model.addAttribute("budgets", budgetService.findPage(page, 10));
        model.addAttribute("currentPage", page);
        return "budgetList";
    }

    @PostMapping("/add")
    public String addBudget(@Valid @ModelAttribute BudgetModel budget, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Ошибка ввода данных!");
            return "redirect:/budgets";
        }
        budgetService.add(budget);
        return "redirect:/budgets";
    }

    @PostMapping("/delete/{id}")
    public String deleteBudget(@PathVariable long id) {
        budgetService.delete(id);
        return "redirect:/budgets";
    }
}