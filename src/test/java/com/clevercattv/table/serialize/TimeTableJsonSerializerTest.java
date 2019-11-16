package com.clevercattv.table.serialize;

import com.clevercattv.table.MainTest;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.TimeTable;
import com.clevercattv.table.service.IOExecutor;
import com.clevercattv.table.service.TimeTableService;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(DataProviderRunner.class)
public class TimeTableJsonSerializerTest extends MainTest {

    private static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService();

    @BeforeClass
    public static void beforeClass() {
        TIME_TABLE_SERVICE.addLesson(FIRST_LESSON);
        TIME_TABLE_SERVICE.addLesson(SECOND_LESSON);
    }

    @DataProvider
    public static Object[][] dayOfWeekDataProvider() {
        return new Object[][]{
                {new TimeTable(TIME_TABLE_SERVICE.getLessons()), "Test1.json"},
                {new TimeTable(TIME_TABLE_SERVICE.getDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.THURSDAY))), "Test2.json"},
                {new TimeTable(TIME_TABLE_SERVICE.getDaysByGroup(FIRST_LESSON.getGroup(), Arrays.asList(DayOfWeek.MONDAY))), "Test3.json"},
                {new TimeTable(TIME_TABLE_SERVICE.getWeekByGroup(SECOND_LESSON.getGroup())), "Test4.json"}
        };
    }

    @Test
    @UseDataProvider("dayOfWeekDataProvider")
    public void saveLoadTimeTable(TimeTable timeTable, String path) throws ExecutionException, InterruptedException {
        IOExecutor.save(timeTable, path);
        assertEquals(IOExecutor.load(path).get(), timeTable);
    }

    @Test(expected = ExecutionException.class)
    public void readTimeTableError() throws ExecutionException, InterruptedException {
        assertNull(IOExecutor.load("Test5.json").get());
    }

}