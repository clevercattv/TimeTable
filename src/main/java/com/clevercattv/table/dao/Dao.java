package com.clevercattv.table.dao;

import java.util.List;
import java.util.Optional;

public abstract class Dao<T> {

    abstract Optional<T> get(int id);

    abstract List<T> getAll();

    abstract void save(T t);

    abstract void update(T t);

    abstract void delete(T t);

}
