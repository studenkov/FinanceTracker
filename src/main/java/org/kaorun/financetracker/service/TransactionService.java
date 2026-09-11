package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.TransactionModel;

import java.util.List;

public interface TransactionService extends CrudService<TransactionModel, Long> {
    List<TransactionModel> findByNote(String note);
    List<TransactionModel> findPage(int page, int size);
}
