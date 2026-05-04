package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.AccountModel;

import java.util.List;

public interface AccountService {
    List<AccountModel> findAll();
    AccountModel findById(Long id);
    List<AccountModel> findByTitle(String title);
    List<AccountModel> findPage(int page, int size);
    AccountModel add(AccountModel x);
    AccountModel update(AccountModel x);
    void delete(Long id);
}
