package com.clevercattv.table.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> findById(int id) throws SQLException;

    List<T> findAll() throws SQLException;

    T save(T t) throws SQLException;

    Collection<T> saveAll(Collection<T> t) throws SQLException;

    void update(T t) throws SQLException;

    void delete(T t) throws SQLException;

}
