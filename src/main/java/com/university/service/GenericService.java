package com.university.service;

import java.util.List;

public interface GenericService<T> {
    List<T> getAll();

    List<T> getAll(int limit, int offset);

    void create(T t);

    T get(int id);

    void update(T t, int id);

    void delete(int id);
}
