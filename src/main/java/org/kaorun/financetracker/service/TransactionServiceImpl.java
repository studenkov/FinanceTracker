package org.kaorun.financetracker.service;

import lombok.RequiredArgsConstructor;
import org.kaorun.financetracker.model.AccountModel;
import org.kaorun.financetracker.model.CategoryModel;
import org.kaorun.financetracker.model.TransactionModel;
import org.kaorun.financetracker.repository.AccountRepository;
import org.kaorun.financetracker.repository.CategoryRepository;
import org.kaorun.financetracker.repository.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<TransactionModel> findAll() {
        return repository.findAll();
    }

    @Override
    public TransactionModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<TransactionModel> findByNote(String note) {
        return repository.findByNoteContainingIgnoreCase(note);
    }

    @Override
    public List<TransactionModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    @Transactional
    public TransactionModel add(TransactionModel transaction) {
        if (transaction.getAccount() != null && transaction.getAccount().getId() != null) {
            AccountModel account = accountRepository.findById(transaction.getAccount().getId()).orElse(null);
            CategoryModel category = null;
            if (transaction.getCategory() != null && transaction.getCategory().getId() != null) {
                category = categoryRepository.findById(transaction.getCategory().getId()).orElse(null);
            }

            if (account != null && transaction.getAmount() != null) {
                boolean isIncome = category != null && category.getType() != null &&
                        category.getType().getTitle() != null &&
                        category.getType().getTitle().toLowerCase().contains("доход");

                double currentBalance = account.getBalance() != null ? account.getBalance() : 0.0;
                if (isIncome) {
                    account.setBalance(currentBalance + transaction.getAmount());
                } else {
                    account.setBalance(currentBalance - transaction.getAmount());
                }
                accountRepository.save(account);
            }
        }
        return repository.save(transaction);
    }

    @Override
    @Transactional
    public TransactionModel update(TransactionModel transaction) {
        TransactionModel existing = repository.findById(transaction.getId()).orElse(null);
        if (existing != null) {
            // Revert existing transaction impact
            revertAccountImpact(existing);
        }
        // Apply new impact
        if (transaction.getAccount() != null && transaction.getAccount().getId() != null) {
            AccountModel account = accountRepository.findById(transaction.getAccount().getId()).orElse(null);
            CategoryModel category = null;
            if (transaction.getCategory() != null && transaction.getCategory().getId() != null) {
                category = categoryRepository.findById(transaction.getCategory().getId()).orElse(null);
            }

            if (account != null && transaction.getAmount() != null) {
                boolean isIncome = category != null && category.getType() != null &&
                        category.getType().getTitle() != null &&
                        category.getType().getTitle().toLowerCase().contains("доход");

                double currentBalance = account.getBalance() != null ? account.getBalance() : 0.0;
                if (isIncome) {
                    account.setBalance(currentBalance + transaction.getAmount());
                } else {
                    account.setBalance(currentBalance - transaction.getAmount());
                }
                accountRepository.save(account);
            }
        }
        return repository.save(transaction);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        TransactionModel existing = repository.findById(id).orElse(null);
        if (existing != null) {
            revertAccountImpact(existing);
            repository.delete(existing);
        }
    }

    private void revertAccountImpact(TransactionModel transaction) {
        if (transaction.getAccount() != null && transaction.getAccount().getId() != null && transaction.getAmount() != null) {
            AccountModel account = accountRepository.findById(transaction.getAccount().getId()).orElse(null);
            CategoryModel category = null;
            if (transaction.getCategory() != null && transaction.getCategory().getId() != null) {
                category = categoryRepository.findById(transaction.getCategory().getId()).orElse(null);
            }

            if (account != null) {
                boolean isIncome = category != null && category.getType() != null &&
                        category.getType().getTitle() != null &&
                        category.getType().getTitle().toLowerCase().contains("доход");

                double currentBalance = account.getBalance() != null ? account.getBalance() : 0.0;
                if (isIncome) {
                    account.setBalance(currentBalance - transaction.getAmount());
                } else {
                    account.setBalance(currentBalance + transaction.getAmount());
                }
                accountRepository.save(account);
            }
        }
    }
}
