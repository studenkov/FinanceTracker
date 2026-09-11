package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.UserModel;

import java.util.List;


public interface UserService extends CrudService<UserModel, Long> {
    List<UserModel> findByUsername(String username);
    List<UserModel> findPage(int page, int size);

    boolean existsByUsername(String username);
}