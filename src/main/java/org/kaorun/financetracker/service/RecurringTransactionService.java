package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.RecurringTransactionModel;

import java.util.List;

public interface RecurringTransactionService extends CrudService<RecurringTransactionModel, Long> {
    List<RecurringTransactionModel> findByActive(boolean active);
    List<RecurringTransactionModel> findPage(int page, int size);
}
