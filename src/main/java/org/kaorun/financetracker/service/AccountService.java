package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.AccountModel;

import java.util.List;

public interface AccountService extends CrudService<AccountModel, Long> {
    List<AccountModel> findByTitle(String title);
    List<AccountModel> findPage(int page, int size);
}
