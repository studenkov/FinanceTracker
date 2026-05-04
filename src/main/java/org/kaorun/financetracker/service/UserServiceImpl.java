package org.kaorun.financetracker.service;


import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserModel> findAll() {
        return repository.findAll();
    }

    public UserModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<UserModel> findByUsername(String username) {
        return repository.findByUsernameContainingIgnoreCase(username);
    }

    public List<UserModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public UserModel add(UserModel user) {
        return repository.save(user);
    }

    public UserModel update(UserModel user) {
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}