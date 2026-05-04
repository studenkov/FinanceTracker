package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.BudgetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetModel, Long> {
    List<BudgetModel> findByLimitAmount(Double limitAmount);
}