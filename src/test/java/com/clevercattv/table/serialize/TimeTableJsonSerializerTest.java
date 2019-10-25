package com.clevercattv.table.serialize;

import com.clevercattv.table.MainTest;
import com.clevercattv.table.models.*;
import com.clevercattv.table.services.TimeTableService;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class TimeTableJsonSerializerTest extends MainTest {

    private static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService(new TimeTable(LocalDate.now()));

    @BeforeClass
    public static void beforeClass() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, FIRST_LESSON);
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, SECOND_LESSON);
    }

    @DataProvider
    public static Object[][] dayOfWeekDataProvider() {
        return new Object[][] {
                { TIME_TABLE_SERVICE.getWeek(), "Test1.json" },
                { TIME_TABLE_SERVICE.getDays(DayOfWeek.MONDAY,DayOfWeek.THURSDAY), "Test2.json" },
                { TIME_TABLE_SERVICE.getDaysByGroup(FIRST_LESSON.getGroup(),DayOfWeek.MONDAY), "Test3.json" },
                { TIME_TABLE_SERVICE.getWeekByGroup(SECOND_LESSON.getGroup()), "Test4.json" }
        };
    }

    @Test
    @UseDataProvider("dayOfWeekDataProvider")
    public void saveTimeTable(Map<DayOfWeek, List<Lesson>> days, String path) {
        TimeTable timeTable = new TimeTable(LocalDate.now());
        timeTable.setDayOfWeek(days);
        TimeTableJsonSerializer.serialize(timeTable,path);
    }

    @Test
    @UseDataProvider("dayOfWeekDataProvider")
    public void readTimeTable(Map<DayOfWeek, List<Lesson>> days, String path) {
        assertEquals(TimeTableJsonSerializer.deserialize(path).getDayOfWeek(),days);
    }

}