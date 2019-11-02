package com.clevercattv.table;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.dao.LessonDao;
import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.dao.TeacherDao;
import com.clevercattv.table.database.ConnectionPool;
import com.clevercattv.table.database.TableService;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Arrays;

public class Application {

    public static void main(String[] args) throws SQLException {
        Arrays.stream(Group.class.getMethods())
                .filter(e -> e.getName().equals("build"))
                .filter(e -> e.getReturnType().getSimpleName().equals("Optional"))
                .forEach(e -> {
                            System.out.println(e.getReturnType().getSimpleName());
                            Arrays.stream(e.getParameters())
                                    .forEach(ee -> System.out.println(ee.getType().getSimpleName()));
                            System.out.println("==============");
                        }
                );

        if (true) return;

        DatabaseMetaData metaData = ConnectionPool.getMetaData();
//        ResultSet rs = metaData.getTables("SoftServeTimeTable",
//                "public","teachers",new String[]{});

        LessonDao lessonDao = new LessonDao();
        TeacherDao teacherDao = new TeacherDao();
        GroupDao groupDao = new GroupDao();
        RoomDao roomDao = new RoomDao();

        Teacher teacher1 = new Teacher().setId(1).setFullName("Teacher DOCENT name").setType(Teacher.Type.DOCENT);
        Teacher teacher2 = new Teacher().setId(2).setFullName("Teacher GRADUATE name").setType(Teacher.Type.POST_GRADUATE);
        Teacher teacher3 = new Teacher().setId(3).setFullName("Teacher PROFESSOR name").setType(Teacher.Type.PROFESSOR);

        Teacher teacher4 = new Teacher().setId(4).setFullName("Teacher DOCENT").setType(Teacher.Type.DOCENT);
        Teacher teacher5 = new Teacher().setId(5).setFullName("Teacher GRADUATE").setType(Teacher.Type.POST_GRADUATE);
        Teacher teacher6 = new Teacher().setId(6).setFullName("Teacher PROFESSOR").setType(Teacher.Type.PROFESSOR);

        Group group1 = new Group().setId(1).setName("501");
        Group group2 = new Group().setId(2).setName("502");
        Group group3 = new Group().setId(3).setName("503");

        Group group4 = new Group().setId(4).setName("504");
        Group group5 = new Group().setId(5).setName("505");
        Group group6 = new Group().setId(6).setName("506");

        Room room1 = new Room().setId(1).setName("11").setType(Room.Type.AUDITORY);
        Room room2 = new Room().setId(2).setName("12").setType(Room.Type.AUDITORY);
        Room room3 = new Room().setId(3).setName("13").setType(Room.Type.AUDITORY);

        Room room4 = new Room().setId(4).setName("14").setType(Room.Type.AUDITORY);
        Room room5 = new Room().setId(5).setName("15").setType(Room.Type.AUDITORY);
        Room room6 = new Room().setId(6).setName("16").setType(Room.Type.AUDITORY);

        Lesson lesson1 = new Lesson().setId(1).setName("Math").setNumber(Lesson.Number.FIRST).setTeacher(teacher1).setRoom(room1).setGroup(group1);
        Lesson lesson2 = new Lesson().setId(2).setName("Math").setNumber(Lesson.Number.FIRST).setTeacher(teacher2).setRoom(room2).setGroup(group2);
        Lesson lesson3 = new Lesson().setId(3).setName("Math").setNumber(Lesson.Number.FIRST).setTeacher(teacher3).setRoom(room3).setGroup(group3);

        Lesson lesson4 = new Lesson().setId(4).setName("Math").setNumber(Lesson.Number.FIRST).setTeacher(teacher1).setRoom(room4).setGroup(group4);
        Lesson lesson5 = new Lesson().setId(5).setName("Math").setNumber(Lesson.Number.FIRST).setTeacher(teacher5).setRoom(room1).setGroup(group5);
        Lesson lesson6 = new Lesson().setId(6).setName("Math").setNumber(Lesson.Number.FIRST).setTeacher(teacher6).setRoom(room6).setGroup(group1);

        try {
            TableService.dropTables();
            TableService.createTables();

            teacherDao.save(teacher1);
            teacherDao.save(teacher2);
            teacherDao.save(teacher3);
            teacherDao.save(teacher4);
            teacherDao.save(teacher5);
            teacherDao.save(teacher6);

            groupDao.save(group1);
            groupDao.save(group2);
            groupDao.save(group3);
            groupDao.save(group4);
            groupDao.save(group5);
            groupDao.save(group6);

            roomDao.save(room1);
            roomDao.save(room2);
            roomDao.save(room3);
            roomDao.save(room4);
            roomDao.save(room5);
            roomDao.save(room6);

//            System.out.println(lesson1.getTeacher());
            lessonDao.save(lesson1, DayOfWeek.MONDAY);
//            System.out.println(lesson2.getTeacher());
            lessonDao.save(lesson2, DayOfWeek.MONDAY);
            lessonDao.save(lesson3, DayOfWeek.MONDAY);

//            lessonDao.save(lesson4, DayOfWeek.MONDAY);
//            lessonDao.save(lesson5, DayOfWeek.MONDAY);
//            lessonDao.save(lesson6, DayOfWeek.MONDAY);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
