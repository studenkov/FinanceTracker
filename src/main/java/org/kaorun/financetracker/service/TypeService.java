package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.TypeModel;

import java.util.List;

public interface TypeService {
    List<TypeModel> findAll();
    TypeModel findById(Long id);
    List<TypeModel> findByTitle(String title);
    List<TypeModel> findPage(int page, int size);
    TypeModel add(TypeModel x);
    TypeModel update(TypeModel x);
    void delete(Long id);
}
