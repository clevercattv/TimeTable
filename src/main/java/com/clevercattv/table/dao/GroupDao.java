package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupDao extends DaoImpl<Group> {

    public GroupDao() {
        super("groups");
    }

    @Override
    public Optional<Group> get(int id) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT * FROM " + tableName + " WHERE id = ?")) {
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            rs.next();
            Group group = Group.build(id,
                    rs.getString("name"),
                    rs.getBoolean("combined")
            );
            return Optional.of(group);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    @Override
    public List<Group> getAll() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {
            List<Group> list = new ArrayList<>();
            while (rs.next()) {
                list.add(
                        Group.build(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getBoolean("combined")
                        )
                );
            }
            return list;
        }
    }

    @Override
    public void save(Group group) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO " + tableName + "(name,combined) VALUES (?,?)")) {
            stmt.setString(1, group.getName());
            stmt.setBoolean(2, group.isCombined());
            stmt.executeUpdate();
        }
    }

    @Override
    public void saveAll(Group... groups) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO " + tableName + "(name,combined) VALUES (?,?)")) {
            for (Group group : groups) {
                stmt.setString(1, group.getName());
                stmt.setBoolean(2, group.isCombined());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public void update(Group group) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE " + tableName + " SET name = ?, combined = ? WHERE id = ?")) {
            stmt.setString(1, group.getName());
            stmt.setBoolean(2, group.isCombined());
            stmt.setInt(3, group.getId());
            stmt.executeUpdate();
        }
    }

}