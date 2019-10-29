package com.clevercattv.table.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(int id);

    List<T> getAll();

    void save(T t);

    void saveAll(T... t);

    void update(T t);

    void delete(T t);

}
