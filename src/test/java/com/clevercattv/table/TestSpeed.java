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

    @Test
    public void createRandom() {
        long startTime = System.currentTimeMillis();
        List<Long> generateTime = new ArrayList<>();
        for (int dayInt = 0; dayInt < 5; dayInt++) { // DayOfWeek.values().length - 2
            for (int lessonNumberInt = 0; lessonNumberInt < Lesson.Number.values().length; lessonNumberInt++) {
                for (int groupInt = 0; groupInt < 8; groupInt++) {
                    TIME_TABLE_SERVICE.addLesson(DayOfWeek.values()[dayInt],
                            Lesson.build(
                                    Teacher.build(generateRandomString(generateTime),Teacher.Type.DOCENT),
                                    Lesson.Number.values()[lessonNumberInt],
                                    Group.build("51" + groupInt),
                                    "Math",
                                    Room.build("24" + groupInt, Room.Type.AUDITORY)
                            )
                    );
                }
            }
        }
        long endTime = System.currentTimeMillis() - startTime;
        for (Long time : generateTime) {
            endTime -= time;
        }
        System.out.println("Adding time : " + endTime);
        startTime = System.currentTimeMillis();
        TimeTableJsonSerializer.serialize(TIME_TABLE_SERVICE.getTimeTable(),"randomTest.json");
        System.out.println("Writing json time : " + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        TimeTableJsonSerializer.deserialize("randomTest.json");
        System.out.println("Reading json time : " + (System.currentTimeMillis() - startTime));
    }

    private static final String CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int RANDOM_STRING_LENGTH = 12;

    public String generateRandomString(List<Long> times){
        long startTime = System.currentTimeMillis();
        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        times.add(System.currentTimeMillis() - startTime);
        return randStr.toString();
    }

    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

}
