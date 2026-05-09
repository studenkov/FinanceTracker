package org.kaorun.financetracker.controller.admin;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.CategoryModel;
import org.kaorun.financetracker.service.CategoryService;
import org.kaorun.financetracker.service.TypeService;
import org.kaorun.financetracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController extends AbstractAdminController {
    private final CategoryService service;
    private final TypeService typeService;
    private final UserService userService;

    public AdminCategoryController(CategoryService service, TypeService typeService, UserService userService) {
        this.service = service;
        this.typeService = typeService;
        this.userService = userService;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String query,
            Model model
    ) {
        model.addAttribute("data", resolveData(query, page, service::findById, service::findByTitle, () -> service.findPage(page, PAGE_SIZE), service::findAll));
        model.addAttribute("allTypes", typeService.findAll());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("query", query);
        model.addAttribute("activeTab", "categories");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new CategoryModel());
        }
        return "admin/categories";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("entityModel") CategoryModel entity,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "категории", entity)) {
            return redirect("/admin/categories", 0, null);
        }
        service.add(entity);
        return redirect("/admin/categories", 0, null);
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("entityModel") CategoryModel entity,
            BindingResult result,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "категории", entity)) {
            return redirect("/admin/categories", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/categories", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam Long id,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query
    ) {
        service.delete(id);
        return redirect("/admin/categories", currentPage, query);
    }
}