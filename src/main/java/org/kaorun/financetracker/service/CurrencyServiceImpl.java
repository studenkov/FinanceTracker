package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.CurrencyModel;
import org.kaorun.financetracker.repository.CurrencyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository repository;

    public CurrencyServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    public List<CurrencyModel> findAll() {
        return repository.findAll();
    }

    public CurrencyModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<CurrencyModel> findByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public List<CurrencyModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public CurrencyModel add(CurrencyModel currency) {
        return repository.save(currency);
    }

    public CurrencyModel update(CurrencyModel currency) {
        return repository.save(currency);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
