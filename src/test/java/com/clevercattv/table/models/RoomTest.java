package com.clevercattv.table.models;

import com.clevercattv.table.MainTest;
import com.clevercattv.table.exceptions.BusyException;
import com.clevercattv.table.exceptions.NamingException;
import com.clevercattv.table.exceptions.RoomBusyException;
import com.clevercattv.table.models.Group;
import com.clevercattv.table.models.Lesson;
import com.clevercattv.table.models.Room;
import com.clevercattv.table.models.Teacher;
import com.clevercattv.table.services.TimeTableService;
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
        Room.build(str,Room.Type.AUDITORY);
    }

    @Test(expected = BusyException.class)
    public void testRoomBusyException() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY,
                Lesson.build(
                        Teacher.build("Docent name third",Teacher.Type.DOCENT),
                        Lesson.Number.FIRST,
                        Group.build("508"),
                        "Math",
                        FIRST_LESSON.getRoom()
                )
        );
    }

}
