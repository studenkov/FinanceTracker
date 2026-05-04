package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.FrequencyModel;

import java.util.List;

public interface FrequencyService {
    List<FrequencyModel> findAll();
    FrequencyModel findById(Long id);
    List<FrequencyModel> findByTitle(String title);
    List<FrequencyModel> findPage(int page, int size);
    FrequencyModel add(FrequencyModel x);
    FrequencyModel update(FrequencyModel x);
    void delete(Long id);
}
