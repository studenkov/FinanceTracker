package org.kaorun.financetracker.controller.admin;

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
@RequestMapping("/admin/goals")
public class AdminGoalController extends AbstractAdminController {
    private final GoalService service;
    private final AccountService accountService;

    public AdminGoalController(GoalService service, AccountService accountService) {
        this.service = service;
        this.accountService = accountService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String query, Model model) {
        model.addAttribute("data", resolveData(query, page, service::findById, service::findByTitle, () -> service.findPage(page, PAGE_SIZE), service::findAll));

        model.addAttribute("allAccounts", accountService.findAll());

        model.addAttribute("query", query);
        model.addAttribute("activeTab", "goals");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new GoalModel());
        }
        return "admin/goals";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("entityModel") GoalModel entity, BindingResult result, RedirectAttributes redirectAttrs) {
        if (hasErrors(result, redirectAttrs, "цели", entity)) {
            return redirect("/admin/goals", 0, null);
        }
        service.add(entity);
        return redirect("/admin/goals", 0, null);
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("entityModel") GoalModel entity, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (hasErrors(result, redirectAttrs, "цели", entity)) {
            return redirect("/admin/goals", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/goals", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) {
        service.delete(id);
        return redirect("/admin/goals", currentPage, query);
    }
}