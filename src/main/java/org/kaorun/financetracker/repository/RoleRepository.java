package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    List<RoleModel> findByRoleContainingIgnoreCase(String role);
}