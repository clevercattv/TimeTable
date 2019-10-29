package com.clevercattv.table.dao;

import com.clevercattv.table.jdbc.DataBase;
import com.clevercattv.table.models.EntityId;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DaoImpl<T extends EntityId> implements Dao<T> {

    protected final String tableName;

    public DaoImpl(String tableName) {
        this.tableName = tableName;
    }

    public void delete(T t){
        try(Connection connection = DataBase.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                        "DELETE FROM " + tableName + " WHERE id = ?");
                ) {
            stmt.setInt(1,t.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
