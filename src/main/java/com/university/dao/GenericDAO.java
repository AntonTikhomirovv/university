package com.university.dao;

import java.util.List;

public interface GenericDAO<T> {
    List<T> getAll();

    List<T> getAll(int limit, int offset);

    void create(T t);

    T get(int id);

    void update(T t, int id);

    void delete(int id);
}
