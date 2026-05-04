package org.kaorun.financetracker.controller;

import org.kaorun.financetracker.model.CategoryModel;
import org.kaorun.financetracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) String query,
                         @RequestParam(defaultValue = "0") int page) {
        int size = 10;
        List<CategoryModel> categories;

        if (query != null && !query.isEmpty()) {
            try {
                long id = Long.parseLong(query.trim());
                CategoryModel cat = service.findById(id);
                categories = (cat != null) ? List.of(cat) : List.of();
            } catch (NumberFormatException e) {
                categories = service.findByTitle(query);
            }
        } else {
            categories = service.findPage(page, size);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);

        return "categoryList";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title,
                      @RequestParam int typeId,
                      @RequestParam int userId) {
        CategoryModel category = new CategoryModel(title, typeId, userId);
        service.add(category);
        return "redirect:/categories";
    }
}