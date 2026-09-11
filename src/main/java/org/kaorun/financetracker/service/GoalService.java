package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.GoalModel;

import java.util.List;

public interface GoalService extends CrudService<GoalModel, Long> {
    List<GoalModel> findByTitle(String title);
    List<GoalModel> findPage(int page, int size);
}
