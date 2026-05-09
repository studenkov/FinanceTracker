package org.kaorun.financetracker.controller.admin;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.CurrencyModel;
import org.kaorun.financetracker.service.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/currencies")
public class AdminCurrencyController extends AbstractAdminController {
    private final CurrencyService service;

    public AdminCurrencyController(CurrencyService service) { this.service = service; }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String query,
            Model model
    ) {
        model.addAttribute(
                "data",
                resolveData(
                        query,
                        page,
                        service::findById,
                        service::findByTitle,
                        () -> service.findPage(page, PAGE_SIZE),
                        service::findAll
                )
        );
        model.addAttribute("query", query);
        model.addAttribute("activeTab", "currencies");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new CurrencyModel());
        }
        return "admin/currencies";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("entityModel") CurrencyModel entity,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "валюты", entity)) {
            return redirect("/admin/currencies", 0, null);
        }
        service.add(entity);
        return redirect("/admin/currencies", 0, null);
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("entityModel") CurrencyModel entity,
            BindingResult result,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "валюты", entity)) {
            return redirect("/admin/currencies", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/currencies", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam Long id,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query
    ) {
        service.delete(id);
        return redirect("/admin/currencies", currentPage, query);
    }
}