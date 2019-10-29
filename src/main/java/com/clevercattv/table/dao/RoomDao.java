package com.clevercattv.table.dao;

import com.clevercattv.table.jdbc.DataBase;
import com.clevercattv.table.models.Group;
import com.clevercattv.table.models.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoomDao extends DaoImpl<Room> {

    public RoomDao() {
        super("rooms");
    }

    @Override
    public Optional<Room> get(int id) {
        try (Connection connection = DataBase.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT * FROM " + tableName + " WHERE id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Room room = Room.build(id,
                    rs.getString("name"),
                    rs.getInt("type")
            );
            rs.close();
            return Optional.of(room);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Room> getAll() {
        return null;
    }

    @Override
    public void save(Room room) {

    }

    @Override
    public void saveAll(Room... t) {

    }

    @Override
    public void update(Room room) {

    }
}
