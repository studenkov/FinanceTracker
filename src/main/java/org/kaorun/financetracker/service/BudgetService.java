package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.BudgetModel;

import java.util.List;

public interface BudgetService extends CrudService<BudgetModel, Long> {
    List<BudgetModel> findByLimit(double limit);
    List<BudgetModel> findPage(int page, int size);
}
