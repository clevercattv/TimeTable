package com.clevercattv.table.database;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.dao.LessonDao;
import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.dao.TeacherDao;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Arrays;

public class Test {

    private static final GroupDao GROUP_DAO = new GroupDao();
    private static final RoomDao ROOM_DAO = new RoomDao();
    private static final TeacherDao TEACHER_DAO = new TeacherDao();
    private static final LessonDao LESSON_DAO = new LessonDao();

    private static final Group FIRST_GROUP = new Group().setName("501");
    private static final Group SECOND_GROUP = new Group().setName("502");
    private static final Group THIRD_GROUP = new Group().setName("503");

    private static final Room FIRST_ROOM = new Room().setName("51").setType(Room.Type.AUDITORY);
    private static final Room SECOND_ROOM = new Room().setName("52").setType(Room.Type.LABORATORY);
    private static final Room THIRD_ROOM = new Room().setName("53").setType(Room.Type.AUDITORY);

    private static final Teacher FIRST_TEACHER = new Teacher().setFullName("First teacher").setType(Teacher.Type.DOCENT);
    private static final Teacher SECOND_TEACHER = new Teacher().setFullName("Second teacher").setType(Teacher.Type.POST_GRADUATE);
    private static final Teacher THIRD_TEACHER = new Teacher().setFullName("Third teacher").setType(Teacher.Type.PROFESSOR);

    private static final Lesson FIRST_LESSON = new Lesson()
            .setName("First lesson")
            .setGroup(FIRST_GROUP)
            .setRoom(FIRST_ROOM)
            .setTeacher(FIRST_TEACHER)
            .setDay(DayOfWeek.MONDAY)
            .setNumber(Lesson.Number.FIRST);

    private static final Lesson SECOND_LESSON = new Lesson()
            .setName("Second lesson")
            .setGroup(SECOND_GROUP)
            .setRoom(SECOND_ROOM)
            .setTeacher(SECOND_TEACHER)
            .setDay(DayOfWeek.MONDAY)
            .setNumber(Lesson.Number.FIRST);

    private static final Lesson THIRD_LESSON = new Lesson()
            .setName("Third lesson")
            .setGroup(THIRD_GROUP)
            .setRoom(THIRD_ROOM)
            .setTeacher(THIRD_TEACHER)
            .setDay(DayOfWeek.MONDAY)
            .setNumber(Lesson.Number.FIRST);

    public static void main(String[] args) throws SQLException {
        TableService.dropTables();
        TableService.createTables();
        GROUP_DAO.saveAll(Arrays.asList(FIRST_GROUP,SECOND_GROUP,THIRD_GROUP));
        ROOM_DAO.saveAll(Arrays.asList(FIRST_ROOM,SECOND_ROOM,THIRD_ROOM));
        TEACHER_DAO.saveAll(Arrays.asList(FIRST_TEACHER,SECOND_TEACHER,THIRD_TEACHER));
        LESSON_DAO.saveAll(Arrays.asList(FIRST_LESSON,SECOND_LESSON,THIRD_LESSON));
    }

}
