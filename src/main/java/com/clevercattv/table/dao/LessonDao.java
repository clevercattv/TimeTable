package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;

import java.sql.*;
import java.util.*;

public class LessonDao extends DaoImpl<Lesson> {

    private static final String TABLE_NAME = "lessons";
    private static final String FIND_ALL = "SELECT l.id, l.name, l.number, t.id, t.fullname, t.type, " +
            "r.id, r.name, r.type" +
            "g.id, g.name, g.combined, l.day FROM lessons l " +
            "LEFT JOIN teachers t ON t.id = l.teacherId " +
            "LEFT JOIN groups g ON g.id = l.groupId " +
            "LEFT JOIN rooms r ON r.id = l.roomId ";
    private static final String FIND_BY_ID = FIND_ALL + "WHERE l.id = ? ";
    private static final String SAVE = "INSERT INTO " + TABLE_NAME + "" +
            "(name,number,teacherid,roomid,groupid,day) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET name = ?," +
            " number = ?, teacherId = ?, roomId = ?, groupId = ?, day = ? WHERE id = ?";

    public LessonDao() {
        super(TABLE_NAME);
    }

    @Override
    public Optional<Lesson> findById(int id) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(FIND_BY_ID)) {
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(buildLesson(rs));
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
    public List<Lesson> findAll() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL)) {
            List<Lesson> list = new ArrayList<>();
            while (rs.next()) {
                list.add(buildLesson(rs));
            }
            return list;
        }
    }

    @Override
    public Lesson save(Lesson lesson) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            fillSaveStatement(stmt,lesson);
            stmt.executeUpdate();
            return fillById(lesson,stmt);
        }
    }

    @Override
    public Collection<Lesson> saveAll(Collection<Lesson> lessons) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            for (Lesson lesson : lessons) {
                fillSaveStatement(stmt,lesson);
                stmt.addBatch();
            }
            stmt.executeBatch();
            return fillAllByIds(lessons,stmt);
        }
    }

    @Override
    public void update(Lesson lesson) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            fillSaveStatement(stmt,lesson);
            stmt.executeUpdate();
        }
    }

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

    private void fillSaveStatement(PreparedStatement stmt, Lesson lesson) throws SQLException {
        stmt.setString(1, lesson.getName());
        stmt.setInt(2, Arrays.asList(Lesson.Number.values()).indexOf(lesson.getNumber()));
        stmt.setInt(3, lesson.getTeacher().getId());
        stmt.setInt(4, lesson.getRoom().getId());
        stmt.setInt(5, lesson.getGroup().getId());
        stmt.setInt(6, lesson.getDay().getValue());
    }

}