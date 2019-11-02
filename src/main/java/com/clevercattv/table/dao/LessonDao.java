package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.dto.LessonDTO;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;

import java.sql.*;
import java.time.DayOfWeek;
import java.util.*;

// todo rewrite
public class LessonDao implements Dao<Map.Entry<DayOfWeek,Lesson>> {

    private static final String TABLE_NAME = "lessons";
    private static final String GET_ALL = "SELECT l.id, l.name, l.number, t.id, t.fullname, t.type, " +
            "r.id, r.name, r.type" +
            "g.id, g.name, g.combined, l.day FROM lessons l " +
            "LEFT JOIN teachers t ON t.id = l.teacherId " +
            "LEFT JOIN groups g ON g.id = l.groupId " +
            "LEFT JOIN rooms r ON r.id = l.roomId ";
    private static final String GET_ID = GET_ALL + "WHERE l.id = ? ";
    private static final String INSERT = "INSERT INTO " + TABLE_NAME + "" +
            "(name,number,teacherid,roomid,groupid,day) VALUES (?,?,?,?,?,?)";
    private static final String COUNT_GROUPS_BY_COMBINED_FALSE_AND_NAME_IN_ARRAY = "SELECT count(*) FROM groups " +
            "WHERE combined = false AND name in ?";
    private static final String GROUPS_BY_COMBINED_TRUE = "SELECT * FROM groups WHERE combined = TRUE";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET name = ?," +
            " number = ?, teacherId = ?, roomId = ?, groupId = ?, day = ? WHERE id = ?";

    private Lesson buildLesson(ResultSet rs) throws SQLException {
        return new Lesson()
                .setId(rs.getInt(1))
                .setName(rs.getString(2))
                .setNumber(Lesson.Number.values()[rs.getInt(3)])
                .setTeacher(
                        new Teacher()
                                .setId(rs.getInt(4))
                                .setFullName(rs.getString(5))
                                .setType(Teacher.Type.values()[rs.getInt(6)])
                )
                .setRoom(
                        new Room()
                                .setId(rs.getInt(7))
                                .setName(rs.getString(8))
                                .setType(Room.Type.values()[rs.getInt(9)])
                )
                .setGroup(
                        new Group()
                                .setId(rs.getInt(10))
                                .setName(rs.getString(11))
                                .setCombined(rs.getBoolean(12))
                );
    }

    public Optional<Lesson> get(int id) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_ID)) {
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            rs.next();
            return Optional.of(buildLesson(rs));
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    public List<Lesson> getAll() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL)) {
            List<Lesson> list = new ArrayList<>();
            while (rs.next()) {
                list.add(buildLesson(rs));
            }
            return list;
        }
    }

    private void fillSaveStatement(PreparedStatement stmt, Lesson lesson,
                                   DayOfWeek dayOfWeek) throws SQLException {
        stmt.setString(1, lesson.getName());
        stmt.setInt(2, Arrays.asList(Lesson.Number.values()).indexOf(lesson.getNumber()));
        stmt.setInt(3, lesson.getTeacher().getId());
        stmt.setInt(4, lesson.getRoom().getId());
        stmt.setInt(5, lesson.getGroup().getId());
        stmt.setInt(6, dayOfWeek.getValue());
    }

    public Lesson save(Lesson lesson, DayOfWeek dayOfWeek) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT)) {
            fillSaveStatement(stmt, lesson, dayOfWeek);
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                lesson.setId(rs.getInt(1));
                return lesson;
            } else {
                throw new SQLException("Creating lesson failed, no ID obtained.");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    public void update(Lesson lesson, DayOfWeek dayOfWeek) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            fillSaveStatement(stmt, lesson, dayOfWeek);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Map.Entry<DayOfWeek, Lesson>> findById(int id) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_ID)) {
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            rs.next();
            return Collections.singletonMap(
                    DayOfWeek.of(rs.getInt(13)),
                    buildLesson(rs)).entrySet()
                    .stream()
                    .findFirst();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    @Override
    public List<Map.Entry<DayOfWeek, Lesson>> findAll() throws SQLException {
        return null;
    }

    @Override
    public Map.Entry<DayOfWeek, Lesson> save(Map.Entry<DayOfWeek, Lesson> lesson) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT)) {
            fillSaveStatement(stmt, lesson.getValue(), lesson.getKey());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                lesson.getValue().setId(rs.getInt(1));
                return lesson;
            } else {
                throw new SQLException("Creating lesson failed, no ID obtained.");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    @Override
    public Collection<Map.Entry<DayOfWeek, Lesson>> saveAll(
            Collection<Map.Entry<DayOfWeek, Lesson>> lessons) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            for (Map.Entry<DayOfWeek, Lesson> lesson : lessons) {
                fillSaveStatement(stmt, lesson.getValue(), lesson.getKey());
                stmt.addBatch();
            }
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            for (Map.Entry<DayOfWeek, Lesson> lesson : lessons) {
                rs.next();
                lesson.getValue().setId(rs.getInt(1));
            }
            return lessons;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    @Override
    public void update(Map.Entry<DayOfWeek, Lesson> lesson) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            fillSaveStatement(stmt, lesson.getValue(), lesson.getKey());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Map.Entry<DayOfWeek, Lesson> entry) throws SQLException {

    }

}