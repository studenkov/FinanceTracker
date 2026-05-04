package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.GoalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<GoalModel, Long> {
    List<GoalModel> findByTitleContainingIgnoreCase(String title);
}