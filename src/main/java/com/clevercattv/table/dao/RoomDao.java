package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RoomDao extends DaoImpl<Room> {

    public RoomDao() {
        super("rooms");
    }

    @Override
    public Optional<Room> get(int id) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT * FROM " + tableName + " WHERE id = ?")) {
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            rs.next();
            Room room = Room.build(id,
                    rs.getString("name"),
                    rs.getInt("type")
            );
            return Optional.of(room);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    @Override
    public List<Room> getAll() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {
            List<Room> list = new ArrayList<>();
            while (rs.next()) {
                list.add(
                        Room.build(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getInt("type")
                        )
                );
            }
            return list;
        }
    }

    @Override
    public void save(Room room) throws SQLException {
        if (room.getId() > 0) {
            update(room);
            return;
        }
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO " + tableName + "(name,type) VALUES (?,?)")) {
            stmt.setString(1, room.getName());
            stmt.setInt(2, Arrays.asList(Room.Type.values()).indexOf(room.getType()));
            stmt.executeUpdate();
        }
    }

    @Override
    public void saveAll(Room... rooms) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO " + tableName + "(name,type) VALUES (?,?)")) {
            List<Room.Type> types = Arrays.asList(Room.Type.values());
            for (Room room : rooms) {
                stmt.setString(1, room.getName());
                stmt.setInt(2, types.indexOf(room.getType()));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public void update(Room room) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE " + tableName + " SET name = ?, type = ? WHERE id = ?")) {
            stmt.setString(1, room.getName());
            stmt.setInt(2, Arrays.asList(Room.Type.values()).indexOf(room.getType()));
            stmt.setInt(3, room.getId());
            stmt.executeUpdate();
        }
    }

}
