package org.kaorun.financetracker.controller;

import org.kaorun.financetracker.model.AccountModel;
import org.kaorun.financetracker.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/accounts")
    public String getAll(Model model) {
        model.addAttribute("accounts", service.findAll());
        return "accountList";
    }

    @GetMapping("/accounts/search")
    public String search(@RequestParam String title, Model model) {
        model.addAttribute("accounts", service.findByTitle(title));
        return "accountList";
    }

    @GetMapping("/accounts/page")
    public String page(@RequestParam int page, Model model) {
        model.addAttribute("accounts", service.findPage(page, 10));
        return "accountList";
    }

    @PostMapping("/accounts/add")
    public String add(@RequestParam String title,
                      @RequestParam double balance,
                      @RequestParam int userId,
                      @RequestParam int currencyId) {

        service.add(new AccountModel(title, balance, userId, currencyId));
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/update")
    public String update(@RequestParam String title,
                         @RequestParam double balance,
                         @RequestParam int userId,
                         @RequestParam int currencyId) {

        service.update(new AccountModel(title, balance, userId, currencyId));
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/delete")
    public String delete(@RequestParam long id) {
        service.delete(id);
        return "redirect:/accounts";
    }
}