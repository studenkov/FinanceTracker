package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    List<AccountModel> findByTitleContainingIgnoreCase(String title);
}