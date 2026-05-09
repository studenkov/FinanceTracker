package org.kaorun.financetracker.controller.admin;

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
@RequestMapping("/admin/budgets")
public class AdminBudgetController extends AbstractAdminController {
    private final BudgetService service;
    private final CategoryService categoryService;

    public AdminBudgetController(BudgetService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String query, Model model) {
        model.addAttribute("data", resolveData(query, page, service::findById, q -> service.findByLimit(Double.parseDouble(q)), () -> service.findPage(page, PAGE_SIZE), service::findAll));

        model.addAttribute("allCategories", categoryService.findAll());

        model.addAttribute("query", query);
        model.addAttribute("activeTab", "budgets");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new BudgetModel());
        }
        return "admin/budgets";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("entityModel") BudgetModel entity, BindingResult result, RedirectAttributes redirectAttrs) {
        if (hasErrors(result, redirectAttrs, "бюджета", entity)) {
            return redirect("/admin/budgets", 0, null);
        }
        service.add(entity);
        return redirect("/admin/budgets", 0, null);
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("entityModel") BudgetModel entity, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (hasErrors(result, redirectAttrs, "бюджета", entity)) {
            return redirect("/admin/budgets", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/budgets", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) {
        service.delete(id);
        return redirect("/admin/budgets", currentPage, query);
    }
}