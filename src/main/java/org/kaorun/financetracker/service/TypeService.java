package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.TypeModel;

import java.util.List;

public interface TypeService extends CrudService<TypeModel, Long> {
    List<TypeModel> findByTitle(String title);
    List<TypeModel> findPage(int page, int size);
}
