package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.FrequencyModel;

import java.util.List;

public interface FrequencyService extends CrudService<FrequencyModel, Long> {
    List<FrequencyModel> findByTitle(String title);
    List<FrequencyModel> findPage(int page, int size);
}
