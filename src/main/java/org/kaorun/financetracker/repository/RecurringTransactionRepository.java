package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.RecurringTransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransactionModel, Long> {
    List<RecurringTransactionModel> findByIsActive(Boolean isActive);
}