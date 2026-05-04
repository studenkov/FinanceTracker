package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.RoleModel;

import java.util.List;

public interface RoleService {
    List<RoleModel> findAll();
    RoleModel findById(Long id);
    List<RoleModel> findByRole(String role);
    List<RoleModel> findPage(int page, int size);
    RoleModel add(RoleModel role);
    RoleModel update(RoleModel role);
    void delete(Long id);
}
