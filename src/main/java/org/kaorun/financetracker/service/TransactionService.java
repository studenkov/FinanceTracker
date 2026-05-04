package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.TransactionModel;

import java.util.List;

public interface TransactionService {
    List<TransactionModel> findAll();
    TransactionModel findById(Long id);
    List<TransactionModel> findByNote(String note);
    List<TransactionModel> findPage(int page, int size);
    TransactionModel add(TransactionModel x);
    TransactionModel update(TransactionModel x);
    void delete(Long id);
}
