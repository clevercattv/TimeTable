package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.model.Room;

import java.sql.*;
import java.util.*;

public class RoomDao extends DaoImpl<Room> {

    private static final String TABLE_NAME = "rooms";
    private static final String FIND_ALL = "SELECT id, name, type FROM " + TABLE_NAME;
    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";
    private static final String SAVE = "INSERT INTO " + TABLE_NAME + "(name,type) VALUES (?,?)";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET name = ?, type = ? WHERE id = ?";

    public RoomDao() {
        super(TABLE_NAME);
    }

    @Override
    public Optional<Room> findById(int id) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(FIND_BY_ID)) {
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()){
                return Optional.of(new Room()
                        .setId(id)
                        .setName(rs.getString("name"))
                        .setType(Room.Type.values()[rs.getInt("type")]));
            } else {
                return Optional.empty();
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    @Override
    public List<Room> findAll() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL)) {
            List<Room> list = new ArrayList<>();
            while (rs.next()) {
                list.add(
                        new Room()
                                .setId(rs.getInt("id"))
                                .setName(rs.getString("name"))
                                .setType(Room.Type.values()[rs.getInt("type")])
                );
            }
            return list;
        }
    }

    @Override
    public Room save(Room room) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, room.getName());
            stmt.setInt(2, Arrays.asList(Room.Type.values()).indexOf(room.getType()));
            stmt.executeUpdate();
            return fillById(room,stmt);
        }
    }

    @Override
    public Collection<Room> saveAll(Collection<Room> rooms) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            List<Room.Type> types = Arrays.asList(Room.Type.values());
            for (Room room : rooms) {
                stmt.setString(1, room.getName());
                stmt.setInt(2, types.indexOf(room.getType()));
                stmt.addBatch();
            }
            stmt.executeBatch();
            return fillAllByIds(rooms,stmt);
        }
    }

    @Override
    public void update(Room room) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setString(1, room.getName());
            stmt.setInt(2, Arrays.asList(Room.Type.values()).indexOf(room.getType()));
            stmt.setInt(3, room.getId());
            stmt.executeUpdate();
        }
    }

}
