package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.model.Room;

import java.sql.*;
import java.util.*;

public class RoomDao extends DaoImpl<Room> {

    private static final RoomDao ROOM_DAO = new RoomDao();
    private static final String TABLE_NAME = "rooms";
    private static final String FIND = "SELECT id, name, type FROM " + TABLE_NAME;
    private static final String ORDER_BY_NAME_ASC = " ORDER BY name ASC ";
    private static final String FIND_ALL = FIND + ORDER_BY_NAME_ASC;
    private static final String FIND_BY_NAME_AND_TYPE = FIND + " WHERE name ILIKE ? and type LIKE ?" + ORDER_BY_NAME_ASC;
    private static final String FIND_BY_ID = FIND + " WHERE id = ?";
    private static final String SAVE = "INSERT INTO " + TABLE_NAME + "(name,type) VALUES (?,?)";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET name = ?, type = ? WHERE id = ?";

    private RoomDao() {
        super(TABLE_NAME, "name");
    }

    public static RoomDao getInstance() {
        return ROOM_DAO;
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
                        .setType(Room.Type.valueOf(rs.getString("type"))));
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
                                .setType(Room.Type.valueOf(rs.getString("type")))
                );
            }
            return list;
        }
    }

    public List<Room> findByNameAndType(String name, String type) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(FIND_BY_NAME_AND_TYPE)) {
            stmt.setString(1,"%" + name + "%");
            stmt.setString(2,"%" + type + "%");
            rs = stmt.executeQuery();
            List<Room> list = new ArrayList<>();
            while (rs.next()) {
                list.add(
                        new Room()
                                .setId(rs.getInt("id"))
                                .setName(rs.getString("name"))
                                .setType(Room.Type.valueOf(rs.getString("type")))
                );
            }
            return list;
        } finally {
            if (rs != null){
                rs.close();
            }
        }
    }

    @Override
    public Room save(Room room) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, room.getName());
            stmt.setString(2, room.getType().name());
            stmt.executeUpdate();
            return fillById(room,stmt);
        }
    }

    @Override
    public Collection<Room> saveAll(Collection<Room> rooms) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            for (Room room : rooms) {
                stmt.setString(1, room.getName());
                stmt.setString(2, room.getType().name());
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
            stmt.setString(2, room.getType().name());
            stmt.setInt(3, room.getId());
            stmt.executeUpdate();
        }
    }

}
