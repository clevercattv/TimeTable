package com.clevercattv.table;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.dao.LessonDao;
import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.dao.TeacherDao;
import com.clevercattv.table.database.TableService;
import com.clevercattv.table.model.*;
import com.clevercattv.table.serialize.LessonJsonSerializer;
import com.clevercattv.table.service.TimeTableService;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;

public class TestSpeed {

    private static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService();

    private static final char[] CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private static final LessonDao DAO = LessonDao.getInstance();
    private static final GroupDao GROUP_DAO = GroupDao.getInstance();
    private static final RoomDao ROOM_DAO = RoomDao.getInstance();
    private static final TeacherDao TEACHER_DAO = new TeacherDao();

    @Test
    public void createRandom() throws SQLException {
        TableService.dropTables();
        TableService.createTables();
        long startTime = System.currentTimeMillis();
        for (int dayI = 0; dayI < 5; dayI++) { // DayOfWeek.values().length - 2
            DayOfWeek dayOfWeek = DayOfWeek.values()[dayI];
            for (int numberI = 0; numberI < Lesson.Number.values().length; numberI++) {
                Lesson.Number number = Lesson.Number.values()[numberI];
                for (int groupI = 0; groupI < 1; groupI++) {
                    Group group = new Group().setName(numberI + "" + groupI + "" + dayI);
                    Room room = new Room().setName(numberI + "" + groupI + "" + dayI).setType(Room.Type.AUDITORY);
                    Teacher teacher = new Teacher()
                            .setFullName("test teacher name " +
                                    CHAR_LIST[dayI] +
                                    CHAR_LIST[numberI] +
                                    CHAR_LIST[groupI])
                            .setType(Teacher.Type.DOCENT);
                    TIME_TABLE_SERVICE.addLesson(
                            new Lesson().setName("Math")
                                    .setNumber(number)
                                    .setTeacher(teacher)
                                    .setRoom(room)
                                    .setGroup(group)
                                    .setDay(dayOfWeek)
                    );
                }
            }
        }
//        for (Lesson lesson : TIME_TABLE_SERVICE.getLessons()) {
//            ROOM_DAO.save(lesson.getRoom());
//            GROUP_DAO.save(lesson.getGroup());
//            TEACHER_DAO.save(lesson.getTeacher());
//        }
//        DAO.saveAll(TIME_TABLE_SERVICE.getLessons());
        System.out.println("Adding time : " + (System.currentTimeMillis() - startTime));
        try {
            startTime = System.currentTimeMillis();
            LessonJsonSerializer.serialize(TIME_TABLE_SERVICE.getLessons(), "randomTest.json");
            System.out.println("Writing json time : " + (System.currentTimeMillis() - startTime));
            startTime = System.currentTimeMillis();
            LessonJsonSerializer.deserialize("randomTest.json");
            System.out.println("Reading json time : " + (System.currentTimeMillis() - startTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
