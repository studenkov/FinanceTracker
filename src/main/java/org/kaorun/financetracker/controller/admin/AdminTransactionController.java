package org.kaorun.financetracker.controller.admin;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.TransactionModel;
import org.kaorun.financetracker.service.AccountService;
import org.kaorun.financetracker.service.CategoryService;
import org.kaorun.financetracker.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/transactions")
public class AdminTransactionController extends AbstractAdminController {
    private final TransactionService service;
    private final CategoryService categoryService;
    private final AccountService accountService;

    public AdminTransactionController(TransactionService service, CategoryService categoryService, AccountService accountService) {
        this.service = service;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String query, Model model) {
        model.addAttribute("data", resolveData(query, page, service::findById, service::findByNote, () -> service.findPage(page, PAGE_SIZE), service::findAll));
        model.addAttribute("allCategories", categoryService.findAll());
        model.addAttribute("allAccounts", accountService.findAll());
        model.addAttribute("query", query);
        model.addAttribute("activeTab", "transactions");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new TransactionModel());
        }
        return "admin/transactions";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("entityModel") TransactionModel entity, BindingResult result, RedirectAttributes redirectAttrs) {
        if (hasErrors(result, redirectAttrs, "транзакции", entity)) {
            return redirect("/admin/transactions", 0, null);
        }
        service.add(entity);
        return redirect("/admin/transactions", 0, null);
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("entityModel") TransactionModel entity, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (hasErrors(result, redirectAttrs, "транзакции", entity)) {
            return redirect("/admin/transactions", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/transactions", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) {
        service.delete(id);
        return redirect("/admin/transactions", currentPage, query);
    }
}