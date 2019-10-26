package com.clevercattv.table;

import com.clevercattv.table.models.*;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;
import com.clevercattv.table.services.TimeTableService;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestSpeed {

    private static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService(new TimeTable(LocalDate.now()));

    private static final char[] CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Test
    public void createRandom() {
        long startTime = System.currentTimeMillis();
        for (int dayInt = 0; dayInt < 5; dayInt++) { // DayOfWeek.values().length - 2
            for (int lessonNumberInt = 0; lessonNumberInt < Lesson.Number.values().length; lessonNumberInt++) {
                for (int groupInt = 0; groupInt < 8; groupInt++) {
                    TIME_TABLE_SERVICE.addLesson(DayOfWeek.values()[dayInt],
                            Lesson.build(
                                    Teacher.build("test teacher name " +
                                            CHAR_LIST[dayInt] +
                                            CHAR_LIST[lessonNumberInt] +
                                            CHAR_LIST[groupInt],Teacher.Type.DOCENT),
                                    Lesson.Number.values()[lessonNumberInt],
                                    Group.build("51" + groupInt),
                                    "Math",
                                    Room.build("24" + groupInt, Room.Type.AUDITORY)
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
