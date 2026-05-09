package org.kaorun.financetracker.controller;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.GoalModel;
import org.kaorun.financetracker.service.AccountService;
import org.kaorun.financetracker.service.GoalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/goals")
public class GoalController {
    private final GoalService goalService;
    private final AccountService accountService;

    public GoalController(GoalService goalService, AccountService accountService) {
        this.goalService = goalService;
        this.accountService = accountService;
    }

    @GetMapping
    public String getAll(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("allAccounts", accountService.findAll());

        model.addAttribute("goals", goalService.findPage(page, 10));
        model.addAttribute("currentPage", page);
        return "goalList";
    }

    @PostMapping("/add")
    public String addGoal(@Valid @ModelAttribute GoalModel goal, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Ошибка ввода данных!");
            return "redirect:/goals";
        }
        goalService.add(goal);
        return "redirect:/goals";
    }

    @PostMapping("/delete/{id}")
    public String deleteGoal(@PathVariable long id) {
        goalService.delete(id);
        return "redirect:/goals";
    }
}