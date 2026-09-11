package org.kaorun.financetracker.repository;

import org.kaorun.financetracker.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    List<UserModel> findByUsernameContainingIgnoreCase(String username);

    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);
}