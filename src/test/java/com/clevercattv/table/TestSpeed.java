package com.clevercattv.table;

import com.clevercattv.table.model.*;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;
import com.clevercattv.table.service.TimeTableService;
import org.junit.Test;

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
                            Lesson.build(
                                    Teacher.build("test teacher name " +
                                            CHAR_LIST[day] +
                                            CHAR_LIST[number] +
                                            CHAR_LIST[group],Teacher.Type.DOCENT),
                                    Lesson.Number.values()[number],
                                    Group.build("51" + group),
                                    "Math",
                                    Room.build("24" + group, Room.Type.AUDITORY)
                            )
                    );
                }
            }
        }
        System.out.println("Adding time : " + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        TimeTableJsonSerializer.serialize(TIME_TABLE_SERVICE.getTimeTable(),"randomTest.json");
        System.out.println("Writing json time : " + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        TimeTableJsonSerializer.deserialize("randomTest.json");
        System.out.println("Reading json time : " + (System.currentTimeMillis() - startTime));
    }

}
