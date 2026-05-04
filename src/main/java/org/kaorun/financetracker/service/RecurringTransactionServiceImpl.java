package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.RecurringTransactionModel;
import org.kaorun.financetracker.repository.RecurringTransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecurringTransactionServiceImpl implements RecurringTransactionService {
    private final RecurringTransactionRepository repository;

    public RecurringTransactionServiceImpl(RecurringTransactionRepository repository) {
        this.repository = repository;
    }

    public List<RecurringTransactionModel> findAll() {
        return repository.findAll();
    }

    public RecurringTransactionModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<RecurringTransactionModel> findByActive(boolean active) {
        return repository.findByIsActive(active);
    }

    public List<RecurringTransactionModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public RecurringTransactionModel add(RecurringTransactionModel recurringTransaction) {
        return repository.save(recurringTransaction);
    }

    public RecurringTransactionModel update(RecurringTransactionModel recurringTransaction) {
        return repository.save(recurringTransaction);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
