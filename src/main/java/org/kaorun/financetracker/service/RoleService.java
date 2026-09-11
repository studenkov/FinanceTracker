package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.RoleModel;

import java.util.List;

public interface RoleService extends CrudService<RoleModel, Long> {
    List<RoleModel> findByRole(String role);
    List<RoleModel> findPage(int page, int size);
}
