package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.model.Teacher;

import java.sql.*;
import java.util.*;

public class TeacherDao extends DaoImpl<Teacher> {

    private static final TeacherDao dao = new TeacherDao();
    private static final String TABLE_NAME = "teachers";
    private static final String FIND_ALL = "SELECT id, fullname, type FROM " + TABLE_NAME;
    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";
    private static final String SAVE = "INSERT INTO " + TABLE_NAME + "(fullname,type) VALUES (?,?)";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET fullname = ?, type = ? WHERE id = ?";

    private TeacherDao() {
        super(TABLE_NAME, "fullname");
    }

    public static TeacherDao getInstance() {
        return dao;
    }

    @Override
    public Optional<Teacher> findById(int id) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(FIND_BY_ID)) {
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            rs.next();
            return Optional.of(new Teacher()
                    .setId(id)
                    .setFullName(rs.getString("fullname"))
                    .setType(Teacher.Type.valueOf(rs.getString("type"))));
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    @Override
    public List<Teacher> findAll() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL)) {
            List<Teacher> list = new ArrayList<>();
            while (rs.next()) {
                list.add(
                        new Teacher()
                                .setId(rs.getInt("id"))
                                .setFullName(rs.getString("fullname"))
                                .setType(Teacher.Type.valueOf(rs.getString("type")))
                );
            }
            return list;
        }
    }

    @Override
    public Teacher save(Teacher teacher) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, teacher.getFullName());
            stmt.setString(2, teacher.getType().name());
            stmt.executeUpdate();
            return fillById(teacher,stmt);
        }
    }

    @Override
    public Collection<Teacher> saveAll(Collection<Teacher> teachers) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            for (Teacher teacher : teachers) {
                stmt.setString(1, teacher.getFullName());
                stmt.setString(2, teacher.getType().name());
                stmt.addBatch();
            }
            stmt.executeBatch();
            return fillAllByIds(teachers,stmt);
        }
    }

    @Override
    public void update(Teacher teacher) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setString(1, teacher.getFullName());
            stmt.setString(2, teacher.getType().name());
            stmt.setInt(3, teacher.getId());
            stmt.executeUpdate();
        }
    }

}