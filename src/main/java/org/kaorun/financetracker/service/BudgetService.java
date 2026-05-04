package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.BudgetModel;

import java.util.List;

public interface BudgetService {
    List<BudgetModel> findAll();
    BudgetModel findById(Long id);
    List<BudgetModel> findByLimit(double limit);
    List<BudgetModel> findPage(int page, int size);
    BudgetModel add(BudgetModel x);
    BudgetModel update(BudgetModel x);
    void delete(Long id);
}
