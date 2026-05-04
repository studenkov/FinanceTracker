package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.GoalModel;
import org.kaorun.financetracker.repository.GoalRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {
    private final GoalRepository repository;

    public GoalServiceImpl(GoalRepository repository) {
        this.repository = repository;
    }

    public List<GoalModel> findAll() {
        return repository.findAll();
    }

    public GoalModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<GoalModel> findByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public List<GoalModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public GoalModel add(GoalModel goal) {
        return repository.save(goal);
    }

    public GoalModel update(GoalModel goal) {
        return repository.save(goal);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
