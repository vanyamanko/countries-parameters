package com.manko.countries.service;

import java.util.List;

public interface CrudService<T, S> {
    List<T> getAll();

    T get(Integer id);

    List<T> create(List<S> createForm);

    T update(Integer id, S updateForm);

    void delete(Integer id);
}
