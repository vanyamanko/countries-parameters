package com.manko.counties.service;

import java.util.List;

public interface CrudService<T, S> {
    List<T> getAll();

    T get(Integer id);

    T create(S createForm);

    T update(Integer id, S updateForm);

    void delete(Integer id);
}
