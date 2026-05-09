package org.kaorun.financetracker.controller.admin;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.TypeModel;
import org.kaorun.financetracker.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/types")
public class AdminTypeController extends AbstractAdminController {
    private final TypeService service;

    public AdminTypeController(TypeService service) { this.service = service; }

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
        model.addAttribute("activeTab", "types");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new TypeModel());
        }
        return "admin/types";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("entityModel") TypeModel entity,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "типа", entity)) {
            return redirect("/admin/types", 0, null);
        }
        service.add(entity);
        return redirect("/admin/types", 0, null);
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("entityModel") TypeModel entity,
            BindingResult result,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "типа", entity)) {
            return redirect("/admin/types", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/types", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam Long id,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query
    ) {
        service.delete(id);
        return redirect("/admin/types", currentPage, query);
    }
}