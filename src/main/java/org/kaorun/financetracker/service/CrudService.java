package org.kaorun.financetracker.service;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> findAll();

    T findById(ID id);

    T add(T entity);

    T update(T entity);

    void delete(ID id);
}
