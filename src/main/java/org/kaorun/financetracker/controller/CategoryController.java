package org.kaorun.financetracker.controller;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.CategoryModel;
import org.kaorun.financetracker.service.CategoryService;
import org.kaorun.financetracker.service.TypeService;
import org.kaorun.financetracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;
    private final TypeService typeService;
    private final UserService userService;

    public CategoryController(CategoryService service, TypeService typeService, UserService userService) {
        this.service = service;
        this.typeService = typeService;
        this.userService = userService;
    }

    @GetMapping
    public String getAll(Model model, @RequestParam(required = false) String query, @RequestParam(defaultValue = "0") int page) {
        if (query != null && !query.isEmpty()) {
            try {
                CategoryModel cat = service.findById(Long.parseLong(query.trim()));
                model.addAttribute("categories", cat != null ? java.util.List.of(cat) : java.util.List.of());
            } catch (NumberFormatException e) {
                model.addAttribute("categories", service.findByTitle(query));
            }
        } else {
            model.addAttribute("categories", service.findPage(page, 10));
        }
        model.addAttribute("allTypes", typeService.findAll());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", new CategoryModel());
        }
        return "categoryList";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("category") CategoryModel category, BindingResult result) {
        if (!result.hasErrors()) {
            service.add(category);
        }
        return "redirect:/categories";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id) {
        service.delete(id);
        return "redirect:/categories";
    }
}