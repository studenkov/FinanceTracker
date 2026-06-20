package org.kaorun.financetracker.controller;

import org.kaorun.financetracker.model.AccountModel;
import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.service.AccountService;
import org.kaorun.financetracker.service.CurrencyService;
import org.kaorun.financetracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final UserService userService;

    public AccountController(AccountService accountService, CurrencyService currencyService, UserService userService) {
        this.accountService = accountService;
        this.currencyService = currencyService;
        this.userService = userService;
    }

    @GetMapping
    public String showAccounts(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("allCurrencies", currencyService.findAll());
        model.addAttribute("accountModel", new AccountModel());
        return "accounts";
    }

    @PostMapping("/add")
    public String addAccount(@ModelAttribute("accountModel") AccountModel accountModel, Principal principal) {
        if (principal != null) {
            UserModel currentUser = userService.findByUsername(principal.getName()).stream().findFirst().orElse(null);
            accountModel.setUser(currentUser);
        }
        accountService.add(accountModel);
        return "redirect:/accounts";
    }
}