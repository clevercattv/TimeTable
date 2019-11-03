package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.model.EntityId;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public abstract class DaoImpl<T extends EntityId> implements Dao<T> {

    private final String delete;

    DaoImpl(String tableName) {
        this.delete = "DELETE FROM " + tableName + " WHERE id = ?";
    }

    T fillById(T t, PreparedStatement stmt) throws SQLException {
        try (ResultSet id = stmt.getGeneratedKeys()){
            if (id.next()) {
                t.setId(id.getInt(1));
                return t;
            } else {
                throw new SQLException("Creating " + t.getClass().getSimpleName() + " failed, no ID obtained.");
            }
        }
    }

    Collection<T> fillAllByIds(Collection<T> items, PreparedStatement stmt) throws SQLException {
        try (ResultSet ids = stmt.getGeneratedKeys()){
            for (T item1 : items) {
                ids.next();
                item1.setId(ids.getInt(1));
            }
            return items;
        }
    }

    public void delete(T t) throws SQLException{
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(delete)) {
            stmt.setInt(1, t.getId());
            stmt.execute();
        }
    }

}