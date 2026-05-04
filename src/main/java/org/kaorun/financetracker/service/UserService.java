package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.UserModel;

import java.util.List;


public interface UserService {
    List<UserModel> findAll();
    UserModel findById(Long id);
    List<UserModel> findByUsername(String username);
    List<UserModel> findPage(int page, int size);
    UserModel add(UserModel user);
    UserModel update(UserModel user);
    void delete(Long id);
}