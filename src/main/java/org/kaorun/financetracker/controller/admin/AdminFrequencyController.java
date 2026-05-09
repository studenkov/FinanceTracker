package org.kaorun.financetracker.controller.admin;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.FrequencyModel;
import org.kaorun.financetracker.service.FrequencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/frequencies")
public class AdminFrequencyController extends AbstractAdminController {
    private final FrequencyService service;

    public AdminFrequencyController(FrequencyService service) { this.service = service; }

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
        model.addAttribute("activeTab", "frequencies");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new FrequencyModel());
        }
        return "admin/frequencies";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("entityModel") FrequencyModel entity,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "частоты", entity)) {
            return redirect("/admin/frequencies", 0, null);
        }
        service.add(entity);
        return redirect("/admin/frequencies", 0, null);
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("entityModel") FrequencyModel entity,
            BindingResult result,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "частоты", entity)) {
            return redirect("/admin/frequencies", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/frequencies", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam Long id,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query
    ) {
        service.delete(id);
        return redirect("/admin/frequencies", currentPage, query);
    }
}