package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.FrequencyModel;
import org.kaorun.financetracker.repository.FrequencyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrequencyServiceImpl implements FrequencyService {
    private final FrequencyRepository repository;

    public FrequencyServiceImpl(FrequencyRepository repository) {
        this.repository = repository;
    }

    public List<FrequencyModel> findAll() {
        return repository.findAll();
    }

    public FrequencyModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<FrequencyModel> findByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public List<FrequencyModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public FrequencyModel add(FrequencyModel frequency) {
        return repository.save(frequency);
    }

    public FrequencyModel update(FrequencyModel frequency) {
        return repository.save(frequency);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
