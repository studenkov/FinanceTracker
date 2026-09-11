package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.CategoryModel;
import java.util.List;

public interface CategoryService extends CrudService<CategoryModel, Long> {
    List<CategoryModel> findByTitle(String title);
    List<CategoryModel> findPage(int page, int size);
}