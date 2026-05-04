package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    List<CategoryModel> findByTitleContainingIgnoreCase(String title);
}