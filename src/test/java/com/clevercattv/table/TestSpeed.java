package com.clevercattv.table;

import com.clevercattv.table.model.*;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;
import com.clevercattv.table.service.TimeTableService;
import org.junit.Test;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class TestSpeed {

    private static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService(new TimeTable(LocalDate.now()));

    private static final char[] CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Test
    public void createRandom() {
        long startTime = System.currentTimeMillis();
        for (int day = 0; day < 5; day++) { // DayOfWeek.values().length - 2
            for (int number = 0; number < Lesson.Number.values().length; number++) {
                for (int group = 0; group < 8; group++) {
                    TIME_TABLE_SERVICE.addLesson(DayOfWeek.values()[day],
                            new Lesson().setName("Math")
                                    .setNumber(Lesson.Number.values()[number])
                                    .setTeacher(
                                            new Teacher()
                                                    .setFullName("test teacher name " +
                                                            CHAR_LIST[day] +
                                                            CHAR_LIST[number] +
                                                            CHAR_LIST[group])
                                                    .setType(Teacher.Type.DOCENT)
                                    )
                                    .setRoom(
                                            new Room()
                                                    .setName("24" + group)
                                                    .setType(Room.Type.AUDITORY)
                                    )
                                    .setGroup(
                                            new Group()
                                                    .setName("51" + group)
                                    )
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
