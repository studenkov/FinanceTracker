package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.TypeModel;
import org.kaorun.financetracker.repository.TypeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    private final TypeRepository repository;

    public TypeServiceImpl(TypeRepository repository) {
        this.repository = repository;
    }

    public List<TypeModel> findAll() {
        return repository.findAll();
    }

    public TypeModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<TypeModel> findByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public List<TypeModel> findPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public TypeModel add(TypeModel type) {
        return repository.save(type);
    }

    public TypeModel update(TypeModel type) {
        return repository.save(type);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
