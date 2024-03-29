package com.clevercattv.table;

import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;
import com.clevercattv.table.service.TimeTableService;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;

public class TestSpeed {

    private static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService();

    private static final char[] CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Test
    public void testSpeed() {
        long startTime = System.currentTimeMillis();
        for (int dayI = 0; dayI < 5; dayI++) { // DayOfWeek.values().length - 2
            DayOfWeek dayOfWeek = DayOfWeek.values()[dayI];
            for (int numberI = 0; numberI < Lesson.Number.values().length; numberI++) {
                Lesson.Number number = Lesson.Number.values()[numberI];
                for (int groupI = 0; groupI < 8; groupI++) {
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
        System.out.println("Adding time : " + (System.currentTimeMillis() - startTime));
        try {
            startTime = System.currentTimeMillis();
            TimeTableJsonSerializer.serialize(TIME_TABLE_SERVICE.getTimeTable(), "randomTest.json");
            System.out.println("Writing json time : " + (System.currentTimeMillis() - startTime));
            startTime = System.currentTimeMillis();
            TimeTableJsonSerializer.deserialize("randomTest.json");
            System.out.println("Reading json time : " + (System.currentTimeMillis() - startTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}