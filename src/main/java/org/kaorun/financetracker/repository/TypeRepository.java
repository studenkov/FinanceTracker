package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.TypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<TypeModel, Long> {
    List<TypeModel> findByTitleContainingIgnoreCase(String title);
}