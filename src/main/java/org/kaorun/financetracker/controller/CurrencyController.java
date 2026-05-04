package org.kaorun.financetracker.controller;

import org.kaorun.financetracker.model.CurrencyModel;
import org.kaorun.financetracker.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyService service;

    @GetMapping("/currencies")
    public String getAll(Model model) {
        model.addAttribute("currencies", service.findAll());
        return "currencyList";
    }

    @GetMapping("/currencies/search")
    public String search(@RequestParam String title, Model model) {
        model.addAttribute("currencies", service.findByTitle(title));
        return "currencyList";
    }

    @GetMapping("/currencies/page")
    public String page(@RequestParam int page, Model model) {
        model.addAttribute("currencies", service.findPage(page, 10));
        return "currencyList";
    }

    @PostMapping("/currencies/add")
    public String add(@RequestParam String title) {
        service.add(new CurrencyModel(title));
        return "redirect:/currencies";
    }

    @PostMapping("/currencies/delete")
    public String delete(@RequestParam long id) {
        service.delete(id);
        return "redirect:/currencies";
    }
}
