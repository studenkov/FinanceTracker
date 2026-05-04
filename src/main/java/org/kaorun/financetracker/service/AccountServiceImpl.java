package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.AccountModel;
import org.kaorun.financetracker.repository.AccountRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    public List<AccountModel> findAll() {
        return repository.findAll();
    }

    public AccountModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<AccountModel> findByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public List<AccountModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public AccountModel add(AccountModel account) {
        return repository.save(account);
    }

    public AccountModel update(AccountModel account) {
        return repository.save(account);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
