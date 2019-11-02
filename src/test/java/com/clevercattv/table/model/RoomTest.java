package com.clevercattv.table.model;

import com.clevercattv.table.MainTest;
import com.clevercattv.table.exception.BusyException;
import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.service.TimeTableService;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class RoomTest extends MainTest {

    private static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService(new TimeTable(LocalDate.now()));

    @BeforeClass
    public static void beforeClass() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, FIRST_LESSON);
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, SECOND_LESSON);
    }

    @Test(expected = NamingException.class)
    @DataProvider({
            "Room name%",
            "",
            "Room namesssssssssssssssssssssssssssssssssssssssssssssssssss"
    })
    public void testValidation(String str) {
        new Room().setName(str).setType(Room.Type.AUDITORY);
    }

    @Test(expected = BusyException.class)
    public void testRoomBusyException() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY,
                new Lesson()
                        .setName("Math")
                        .setNumber(Lesson.Number.FIRST)
                        .setTeacher(
                                new Teacher()
                                        .setFullName("Docent name third")
                                        .setType(Teacher.Type.DOCENT)
                        )
                        .setRoom(FIRST_LESSON.getRoom())
                        .setGroup(new Group().setName("508"))
        );
    }

}
