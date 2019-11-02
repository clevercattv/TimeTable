package com.clevercattv.table.database;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.dao.LessonDao;
import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.dao.TeacherDao;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;

public class TableService {

    private static final String ID_SERIAL = "id SERIAL PRIMARY KEY, ";
    private static final String NOT_NULL_UNIQUE = "NOT NULL UNIQUE";

    private static final String CREATE_GROUPS = "CREATE TABLE IF NOT EXISTS Groups(" +
            ID_SERIAL +
            "name VARCHAR(" + Group.MAX_NAME_LENGTH + ") " + NOT_NULL_UNIQUE + "," +
            "combined BOOLEAN NOT NULL)";

    private static final String CREATE_ROOMS = "CREATE TABLE IF NOT EXISTS Rooms(" +
            ID_SERIAL +
            "name VARCHAR(" + Room.MAX_NAME_LENGTH + ") " + NOT_NULL_UNIQUE + "," +
            "type INT NOT NULL)";

    private static final String CREATE_TEACHERS = "CREATE TABLE IF NOT EXISTS Teachers(" +
            ID_SERIAL +
            "fullname VARCHAR(" + Teacher.MAX_NAME_LENGTH + ") " + NOT_NULL_UNIQUE + "," +
            "type INT NOT NULL)";

    private static final String CREATE_LESSONS = "CREATE TABLE IF NOT EXISTS Lessons(" +
            ID_SERIAL +
            "name VARCHAR(32) NOT NULL ," +
            "teacherId INT NOT NULL REFERENCES teachers(id), " +
            "number INT NOT NULL , " +
            "groupId INT NOT NULL REFERENCES groups(id), " +
            "roomId INT NOT NULL REFERENCES rooms(id), " +
            "day INT NOT NULL, " +
            "CONSTRAINT TeacherBusy UNIQUE (number,day,teacherId), " +
            "CONSTRAINT GroupBusy UNIQUE (number,day,groupId), " +
            "CONSTRAINT RoomBusy UNIQUE (number,day,roomId))";

    private TableService(){}

    public static void main(String[] args) throws SQLException {
        GroupDao groupDao = new GroupDao();
        RoomDao roomDao = new RoomDao();
        TeacherDao teacherDao = new TeacherDao();
        LessonDao lessonDao = new LessonDao();
        Group group = new Group().setName("name").setCombined(false);
        dropTables();
        createTables();
        groupDao.save(group);
        roomDao.save(new Room().setName("502").setType(Room.Type.AUDITORY));
        teacherDao.save(new Teacher().setFullName("Teacher full name").setType(Teacher.Type.DOCENT));
        lessonDao.save(new Lesson()
                .setName("name")
                .setNumber(Lesson.Number.FIRST)
                .setGroup(group)
                .setRoom(roomDao.findById(1).get())
                .setTeacher(teacherDao.findById(1).get()), DayOfWeek.MONDAY);

    }

    public static void dropTables() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("DROP SCHEMA public CASCADE ");
            stmt.execute("CREATE SCHEMA public");
        }
    }

    public static void createTables() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(CREATE_GROUPS);
            stmt.execute(CREATE_ROOMS);
            stmt.execute(CREATE_TEACHERS);
            stmt.execute(CREATE_LESSONS);
        }
    }

}
