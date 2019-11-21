package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class GroupDao extends DaoImpl<Group> {

    private static final GroupDao DAO = new GroupDao();

    private static final String TABLE_NAME = "groups";
    private static final String FIND = "SELECT id, name FROM " + TABLE_NAME;
    private static final String ORDER_BY_NAME_ASC = " ORDER BY name ASC ";
    private static final String FIND_ALL = FIND + ORDER_BY_NAME_ASC;
    private static final String FIND_BY_NAME = FIND + " WHERE name ILIKE ? " + ORDER_BY_NAME_ASC;
    private static final String FIND_BY_ID = FIND + " WHERE id = ?";
    private static final String SAVE = "INSERT INTO " + TABLE_NAME + "(name) VALUES (?)";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET name = ? WHERE id = ?";

    private GroupDao() {
        super(TABLE_NAME, "name");
    }

    public static GroupDao getInstance() {
        return DAO;
    }

    @Override
    public Optional<Group> findById(int id) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(FIND_BY_ID)) {
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Group()
                        .setId(id)
                        .setName(rs.getString("name")));
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
    public List<Group> findAll() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement()) {
            return getGroupsByResultSet(stmt.executeQuery(FIND_ALL));
        }
    }

    public List<Group> findByName(String name) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(FIND_BY_NAME)) {
            stmt.setString(1, "%" + name + "%");
            return getGroupsByResultSet(stmt.executeQuery());
        }
    }

    private List<Group> getGroupsByResultSet(ResultSet rs) throws SQLException {
        try{
            List<Group> list = new ArrayList<>();
            while (rs.next()) {
                list.add(
                        new Group()
                                .setId(rs.getInt("id"))
                                .setName(rs.getString("name"))
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
    public Group save(Group group) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, group.getName());
            stmt.executeUpdate();
            return fillById(group, stmt);
        }
    }

    @Override
    public Collection<Group> saveAll(Collection<Group> groups) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            for (Group group : groups) {
                stmt.setString(1, group.getName());
                stmt.addBatch();
            }
            stmt.executeBatch();
            return fillAllByIds(groups, stmt);
        }
    }

    @Override
    public void update(Group group) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setString(1, group.getName());
            stmt.setInt(2, group.getId());
            stmt.executeUpdate();
        }
    }

}