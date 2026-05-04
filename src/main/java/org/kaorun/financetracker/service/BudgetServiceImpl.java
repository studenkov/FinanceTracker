package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.BudgetModel;
import org.kaorun.financetracker.repository.BudgetRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository repository;

    public BudgetServiceImpl(BudgetRepository repository) {
        this.repository = repository;
    }

    public List<BudgetModel> findAll() {
        return repository.findAll();
    }

    public BudgetModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<BudgetModel> findByLimit(double limit) {
        return repository.findByLimitAmount(limit);
    }

    public List<BudgetModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public BudgetModel add(BudgetModel budget) {
        return repository.save(budget);
    }

    public BudgetModel update(BudgetModel budget) {
        return repository.save(budget);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
