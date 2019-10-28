package com.clevercattv.table.dao;

import com.clevercattv.table.jdbc.DataBase;
import com.clevercattv.table.models.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupDao extends Dao<Group> {

    @Override
    public Optional<Group> get(int id) {
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM groups where groupId = ?");
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return Optional.of(Group.build(rs.getString("groupName")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Group> getAll() {
        List<Group> list = new ArrayList<>();
        try {
            Connection connection = DataBase.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM groups");
            while (rs.next()){
//                rs.getInt();
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Group group) {

    }

    @Override
    public void update(Group group) {

    }

    @Override
    public void delete(Group group) {

    }

}
