package org.kaorun.financetracker.controller.admin;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.AccountModel;
import org.kaorun.financetracker.service.AccountService;
import org.kaorun.financetracker.service.CurrencyService;
import org.kaorun.financetracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/accounts")
public class AdminAccountController extends AbstractAdminController {
    private final AccountService service;
    private final UserService userService;
    private final CurrencyService currencyService;

    public AdminAccountController(AccountService service, UserService userService, CurrencyService currencyService) {
        this.service = service;
        this.userService = userService;
        this.currencyService = currencyService;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String query,
            Model model
    ) {
        model.addAttribute("data", resolveData(query, page, service::findById, service::findByTitle, () -> service.findPage(page, PAGE_SIZE), service::findAll));
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allCurrencies", currencyService.findAll());
        model.addAttribute("query", query);
        model.addAttribute("activeTab", "accounts");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new AccountModel());
        }
        return "admin/accounts";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("entityModel") AccountModel entity,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "счета", entity)) {
            return redirect("/admin/accounts", 0, null);
        }
        service.add(entity);
        return redirect("/admin/accounts", 0, null);
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("entityModel") AccountModel entity,
            BindingResult result,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "счета", entity)) {
            return redirect("/admin/accounts", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/accounts", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam Long id,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query
    ) {
        service.delete(id);
        return redirect("/admin/accounts", currentPage, query);
    }
}