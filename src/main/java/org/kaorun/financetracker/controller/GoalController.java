package org.kaorun.financetracker.controller;

import org.kaorun.financetracker.model.GoalModel;
import org.kaorun.financetracker.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/goals")
public class GoalController {

    private final GoalService service;

    @Autowired
    public GoalController(GoalService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model, @RequestParam(defaultValue = "0") int page) {
        int size = 10;
        List<GoalModel> allGoals = service.findAll();
        int start = page * size;

        List<GoalModel> pagedGoals;
        if (start >= allGoals.size()) {
            pagedGoals = new ArrayList<>();
        } else {
            int end = Math.min(start + size, allGoals.size());
            pagedGoals = allGoals.subList(start, end);
        }

        model.addAttribute("goals", pagedGoals);
        model.addAttribute("currentPage", page);
        model.addAttribute("hasNext", (start + size) < allGoals.size());

        return "goalList";
    }

    @PostMapping("/add")
    public String addGoal(@RequestParam String title,
                          @RequestParam Double targetAmount,
                          @RequestParam Double currentAmount,
                          @RequestParam Integer accountId) {

        GoalModel goal = new GoalModel(title, targetAmount, currentAmount, accountId);
        service.add(goal);

        return "redirect:/goals";
    }

    @PostMapping("/delete/{id}")
    public String deleteGoal(@PathVariable long id) {
        service.delete(id);
        return "redirect:/goals";
    }
}