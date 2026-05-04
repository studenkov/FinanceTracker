package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.TransactionModel;
import org.kaorun.financetracker.repository.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<TransactionModel> findAll() {
        return repository.findAll();
    }

    public TransactionModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<TransactionModel> findByNote(String note) {
        return repository.findByNoteContainingIgnoreCase(note);
    }

    public List<TransactionModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public TransactionModel add(TransactionModel transaction) {
        return repository.save(transaction);
    }

    public TransactionModel update(TransactionModel transaction) {
        return repository.save(transaction);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
