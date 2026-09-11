package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.CategoryModel;
import org.kaorun.financetracker.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Категории", description = "Управление категориями доходов и расходов")
public class CategoryApiController extends AbstractApiController<CategoryModel, Long> {

    public CategoryApiController(CategoryService categoryService) {
        super(categoryService, "Категория успешно удалена");
    }
}