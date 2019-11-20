package com.clevercattv.table.model;

import com.clevercattv.table.TestData;
import com.clevercattv.table.exception.BusyException;
import com.clevercattv.table.exception.NamingException;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DayOfWeek;

@RunWith(DataProviderRunner.class)
public class GroupTest extends TestData {

    @Test(expected = NamingException.class)
    @DataProvider({
            "525name%",
            "52",
            "525name looooooooo"
    })
    public void testValidation(String str) {
        new Group(1).setName(str);
    }

    @Test(expected = BusyException.class)
    public void testGroupBusyException() {
        TIME_TABLE_SERVICE.addLesson(
                new Lesson()
                        .setName("Math")
                        .setNumber(Lesson.Number.FIRST)
                        .setTeacher(
                                new Teacher()
                                        .setFullName("Docent name second")
                                        .setType(Teacher.Type.DOCENT)
                        )
                        .setRoom(
                                new Room()
                                        .setName("103")
                                        .setType(Room.Type.AUDITORY)
                        )
                        .setGroup(FIRST_LESSON.getGroup())
                        .setDay(DayOfWeek.MONDAY)
        );
    }

}
