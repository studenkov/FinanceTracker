package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.CategoryModel;
import org.kaorun.financetracker.repository.CategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<CategoryModel> findAll() {
        return repository.findAll();
    }

    // Changed 'int' to 'Long' to match JpaRepository.
    // JPA's findById returns an Optional, so we use .orElse(null) to unpack it.
    public CategoryModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Updated to match the custom method name in your repository
    public List<CategoryModel> findByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    // Replaced custom findPage with Spring Data's built-in PageRequest
    public List<CategoryModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    // JPA uses .save() for adding new records
    public CategoryModel add(CategoryModel category) {
        return repository.save(category);
    }

    // JPA also uses .save() for updating existing records
    public CategoryModel update(CategoryModel category) {
        return repository.save(category);
    }

    // Changed 'int' to 'Long' and updated to JPA's .deleteById()
    public void delete(Long id) {
        repository.deleteById(id);
    }
}