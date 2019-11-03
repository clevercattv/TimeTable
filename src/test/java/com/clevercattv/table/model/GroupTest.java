package com.clevercattv.table.model;

import com.clevercattv.table.MainTest;
import com.clevercattv.table.exception.BusyException;
import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.service.LessonService;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DayOfWeek;

@RunWith(DataProviderRunner.class)
public class GroupTest extends MainTest {

    private static final LessonService TIME_TABLE_SERVICE = new LessonService();

    @BeforeClass
    public static void beforeClass() {
        TIME_TABLE_SERVICE.addLesson(FIRST_LESSON);
        TIME_TABLE_SERVICE.addLesson(SECOND_LESSON);
    }

    @Test(expected = NamingException.class)
    @DataProvider({
            "525name%",
            "52",
            "525name looooooooo"
    })
    public void testValidation(String str) {
        new Group().setName(str);
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
