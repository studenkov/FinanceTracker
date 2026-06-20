package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kaorun.financetracker.model.CategoryModel;
import org.kaorun.financetracker.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@Tag(name =  "Категории")
public class CategoryApiController {
    private final CategoryService categoryService;

    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Получить все категории")
    public List<CategoryModel> getAll() {
        return categoryService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новую категорию")
    @ApiResponse(responseCode = "200", description = "Успешно создано")
    public ResponseEntity<CategoryModel> create(@Valid @RequestBody CategoryModel category) {
        categoryService.add(category);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить категорию по ID")
    @ApiResponse(responseCode = "200", description = "Успешно обновлено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CategoryModel category) {
        if (categoryService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        category.setId(id);
        categoryService.add(category);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить категорию по ID")
    @ApiResponse(responseCode = "200", description = "Успешно удалено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (categoryService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        categoryService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Категория успешно удалена"));
    }
}