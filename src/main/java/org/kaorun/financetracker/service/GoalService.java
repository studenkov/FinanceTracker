package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.GoalModel;

import java.util.List;

public interface GoalService {
    List<GoalModel> findAll();
    GoalModel findById(Long id);
    List<GoalModel> findByTitle(String title);
    List<GoalModel> findPage(int page, int size);
    GoalModel add(GoalModel x);
    GoalModel update(GoalModel x);
    void delete(Long id);
}
