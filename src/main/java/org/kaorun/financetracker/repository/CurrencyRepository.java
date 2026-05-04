package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.CurrencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyModel, Long> {
    List<CurrencyModel> findByTitleContainingIgnoreCase(String title);
}