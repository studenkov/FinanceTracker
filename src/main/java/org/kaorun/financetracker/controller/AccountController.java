package org.kaorun.financetracker.controller;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.AccountModel;
import org.kaorun.financetracker.service.AccountService;
import org.kaorun.financetracker.service.CurrencyService;
import org.kaorun.financetracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;
    private final UserService userService;
    private final CurrencyService currencyService;

    public AccountController(AccountService service, UserService userService, CurrencyService currencyService) {
        this.service = service;
        this.userService = userService;
        this.currencyService = currencyService;
    }

    @GetMapping
    public String getAll(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("accounts", service.findPage(page, 10));
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allCurrencies", currencyService.findAll());
        if (!model.containsAttribute("account")) {
            model.addAttribute("account", new AccountModel());
        }
        return "accountList";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("account") AccountModel account, BindingResult result) {
        if (!result.hasErrors()) {
            service.add(account);
        }
        return "redirect:/accounts";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id) {
        service.delete(id);
        return "redirect:/accounts";
    }
}