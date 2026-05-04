package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.CategoryModel;
import java.util.List;

public interface CategoryService {
    List<CategoryModel> findAll();

    CategoryModel findById(Long id); // 1. Changed from int to Long

    List<CategoryModel> findByTitle(String title);

    List<CategoryModel> findPage(int page, int size);

    CategoryModel add(CategoryModel category);

    CategoryModel update(CategoryModel category);

    void delete(Long id); // 2. Changed from int to Long
}