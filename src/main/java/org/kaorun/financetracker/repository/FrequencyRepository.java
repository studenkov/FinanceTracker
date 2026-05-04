package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.FrequencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrequencyRepository extends JpaRepository<FrequencyModel, Long> {
    List<FrequencyModel> findByTitleContainingIgnoreCase(String title);
}