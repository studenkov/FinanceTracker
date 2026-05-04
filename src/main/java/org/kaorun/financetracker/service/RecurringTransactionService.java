package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.RecurringTransactionModel;

import java.util.List;

public interface RecurringTransactionService {
    List<RecurringTransactionModel> findAll();
    RecurringTransactionModel findById(Long id);
    List<RecurringTransactionModel> findByActive(boolean active);
    List<RecurringTransactionModel> findPage(int page, int size);
    RecurringTransactionModel add(RecurringTransactionModel x);
    RecurringTransactionModel update(RecurringTransactionModel x);
    void delete(Long id);
}
