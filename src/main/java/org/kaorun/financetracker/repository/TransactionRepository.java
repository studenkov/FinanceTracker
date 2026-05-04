package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
    List<TransactionModel> findByNoteContainingIgnoreCase(String note);
}