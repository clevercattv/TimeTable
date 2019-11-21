package com.clevercattv.table.dao;

import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class LessonDao extends DaoImpl<Lesson> {

    private static final LessonDao DAO = new LessonDao();

    private static final String TABLE_NAME = "lessons";
    private static final String FIND_QUERY = "SELECT l.id, l.name, l.number, " +
            "t.id, t.fullname, t.type, " +
            "r.id, r.name, r.type, " +
            "g.id, g.name, l.day FROM lessons l " +
            "LEFT JOIN teachers t ON t.id = l.teacherId " +
            "LEFT JOIN groups g ON g.id = l.groupId " +
            "LEFT JOIN rooms r ON r.id = l.roomId ";
    private static final String ORDER_BY = "ORDER BY l.day, l.number, g.name ";
    private static final String FIND_ALL = FIND_QUERY + ORDER_BY;
    private static final String FIND_FILTERED = FIND_QUERY +
            "WHERE l.name ILIKE ? AND l.number LIKE ? AND t.fullname LIKE ? " +
            "AND r.name LIKE ? AND g.name LIKE ? AND l.day LIKE ? " +
            ORDER_BY;
    private static final String FIND_BY_ID = FIND_QUERY + "WHERE l.id = ? ";
    private static final String SAVE = "INSERT INTO " + TABLE_NAME + "" +
            "(name,number,teacherid,roomid,groupid,day) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET name = ?," +
            " number = ?, teacherId = ?, roomId = ?, groupId = ?, day = ? WHERE id = ?";

    private LessonDao() {
        super(TABLE_NAME, "name");
    }

    public static LessonDao getInstance() {
        return DAO;
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
             Statement stmt = connection.createStatement()) {
            return getLessonsByResultSet(stmt.executeQuery(FIND_ALL));
        }
    }

    public List<Lesson> findFilteredByRequest(HttpServletRequest req) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(FIND_FILTERED)) {
            stmt.setString(1, "%" + req.getParameter("fName") + "%");
            stmt.setString(2, "%" + req.getParameter("fNumber") + "%");
            stmt.setString(3, "%" + req.getParameter("fTeacher") + "%");
            stmt.setString(4, "%" + req.getParameter("fGroup") + "%");
            stmt.setString(5, "%" + req.getParameter("fRoom") + "%");
            stmt.setString(6, "%" + req.getParameter("fDay") + "%");
            return getLessonsByResultSet(stmt.executeQuery());
        }
    }

    private List<Lesson> getLessonsByResultSet(ResultSet rs) throws SQLException {
        try {
            List<Lesson> list = new ArrayList<>();
            while (rs.next()) {
                list.add(buildLesson(rs));
            }
            return list;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    @Override
    public Lesson save(Lesson lesson) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            fillSaveStatement(stmt, lesson);
            stmt.executeUpdate();
            return fillById(lesson, stmt);
        }
    }

    @Override
    public Collection<Lesson> saveAll(Collection<Lesson> lessons) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            for (Lesson lesson : lessons) {
                fillSaveStatement(stmt, lesson);
                stmt.addBatch();
            }
            stmt.executeBatch();
            return fillAllByIds(lessons, stmt);
        }
    }

    @Override
    public void update(Lesson lesson) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            fillSaveStatement(stmt, lesson);
            stmt.setInt(7, lesson.getId());
            stmt.executeUpdate();
        }
    }

    private Lesson buildLesson(ResultSet rs) throws SQLException {
        return new Lesson()
                .setId(rs.getInt(1))
                .setName(rs.getString(2))
                .setNumber(Lesson.Number.valueOf(rs.getString(3)))
                .setTeacher(
                        new Teacher()
                                .setId(rs.getInt(4))
                                .setFullName(rs.getString(5))
                                .setType(Teacher.Type.valueOf(rs.getString(6)))
                )
                .setRoom(
                        new Room()
                                .setId(rs.getInt(7))
                                .setName(rs.getString(8))
                                .setType(Room.Type.valueOf(rs.getString(9)))
                )
                .setGroup(
                        new Group()
                                .setId(rs.getInt(10))
                                .setName(rs.getString(11))
                )
                .setDay(DayOfWeek.valueOf(rs.getString(12)));
    }

    private void fillSaveStatement(PreparedStatement stmt, Lesson lesson) throws SQLException {
        stmt.setString(1, lesson.getName());
        stmt.setString(2, lesson.getNumber().name());
        stmt.setInt(3, lesson.getTeacher().getId());
        stmt.setInt(4, lesson.getRoom().getId());
        stmt.setInt(5, lesson.getGroup().getId());
        stmt.setString(6, lesson.getDay().name());
    }

}