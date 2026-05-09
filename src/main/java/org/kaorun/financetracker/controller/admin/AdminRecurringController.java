package org.kaorun.financetracker.controller.admin;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.RecurringTransactionModel;
import org.kaorun.financetracker.service.AccountService;
import org.kaorun.financetracker.service.CategoryService;
import org.kaorun.financetracker.service.FrequencyService;
import org.kaorun.financetracker.service.RecurringTransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/recurring")
public class AdminRecurringController extends AbstractAdminController {
    private final RecurringTransactionService service;
    private final CategoryService categoryService;
    private final FrequencyService frequencyService;
    private final AccountService accountService;

    public AdminRecurringController(RecurringTransactionService service, CategoryService categoryService, FrequencyService frequencyService, AccountService accountService) {
        this.service = service;
        this.categoryService = categoryService;
        this.frequencyService = frequencyService;
        this.accountService = accountService;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String query,
            Model model
    ) {
        model.addAttribute("data", resolveData(query, page, service::findById, q -> service.findByActive(Boolean.parseBoolean(q)), () -> service.findPage(page, PAGE_SIZE), service::findAll));
        model.addAttribute("allCategories", categoryService.findAll());
        model.addAttribute("allFrequencies", frequencyService.findAll());
        model.addAttribute("allAccounts", accountService.findAll());
        model.addAttribute("query", query);
        model.addAttribute("activeTab", "recurring");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new RecurringTransactionModel());
        }
        return "admin/recurring";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("entityModel") RecurringTransactionModel entity,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "повторяющейся транзакции", entity)) {
            return redirect("/admin/recurring", 0, null);
        }
        service.add(entity);
        return redirect("/admin/recurring", 0, null);
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("entityModel") RecurringTransactionModel entity,
            BindingResult result,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "повторяющейся транзакции", entity)) {
            return redirect("/admin/recurring", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/recurring", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam Long id,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query
    ) {
        service.delete(id);
        return redirect("/admin/recurring", currentPage, query);
    }
}