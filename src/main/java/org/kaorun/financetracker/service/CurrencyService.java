package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.CurrencyModel;

import java.util.List;

public interface CurrencyService {
    List<CurrencyModel> findAll();
    CurrencyModel findById(Long id);
    List<CurrencyModel> findByTitle(String title);
    List<CurrencyModel> findPage(int page, int size);
    CurrencyModel add(CurrencyModel x);
    CurrencyModel update(CurrencyModel x);
    void delete(Long id);
}
