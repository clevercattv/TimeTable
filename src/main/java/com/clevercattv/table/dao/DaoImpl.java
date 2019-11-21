package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.dto.NameIdDTO;
import com.clevercattv.table.model.EntityId;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class DaoImpl<T extends EntityId> implements Dao<T> {

    private final String deleteQuery;
    private final String findAllIdAndNamesQuery;

    DaoImpl(String tableName, String findAllIdAndNamesQuery) {
        this.deleteQuery = "DELETE FROM " + tableName + " WHERE id = ?";
        this.findAllIdAndNamesQuery = "SELECT id, " + findAllIdAndNamesQuery + " FROM " + tableName;
    }

    public List<NameIdDTO> findAllIdAndName() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(findAllIdAndNamesQuery)) {
            List<NameIdDTO> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new NameIdDTO(rs.getInt(1),rs.getString(2)));
            }
            return list;
        }
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
        delete(t.getId());
    }

    public void delete(int id) throws SQLException{
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

}