package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.CurrencyModel;

import java.util.List;

public interface CurrencyService extends CrudService<CurrencyModel, Long> {
    List<CurrencyModel> findByTitle(String title);
    List<CurrencyModel> findPage(int page, int size);
}
