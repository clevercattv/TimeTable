package com.clevercattv.table.service;

import com.clevercattv.table.MainTest;
import com.clevercattv.table.exception.BusyException;
import com.clevercattv.table.model.*;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class TimeTableDataServiceTest extends MainTest {

    private static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService(new TimeTable(LocalDate.now()));

    @BeforeClass
    public static void beforeClass() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, FIRST_LESSON);
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, SECOND_LESSON);
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, THIRD_LESSON);
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, COMBINED);
    }

    @DataProvider
    public static Object[][] lessonDataProvider() {
        return new Object[][]{
                {FIRST_LESSON.getTeacher(), new Group().setName("577"),
                        new Room().setName("5").setType(Room.Type.AUDITORY)},
                {new Teacher().setFullName("Test teacher").setType(Teacher.Type.DOCENT),
                        FIRST_LESSON.getGroup(), new Room().setName("5").setType(Room.Type.AUDITORY)},
                {new Teacher().setFullName("Test teacher").setType(Teacher.Type.DOCENT),
                        new Group().setName("577"), FIRST_LESSON.getRoom()},
                {new Teacher().setFullName("Test teacher").setType(Teacher.Type.DOCENT),
                        FIRST_LESSON.getGroup(), FIRST_LESSON.getRoom()},
                {FIRST_LESSON.getTeacher(), new Group().setName("577"), FIRST_LESSON.getRoom()},
                {FIRST_LESSON.getTeacher(), FIRST_LESSON.getGroup(), new Room().setName("5").setType(Room.Type.AUDITORY)},
                {FIRST_LESSON.getTeacher(), FIRST_LESSON.getGroup(), FIRST_LESSON.getRoom()},
                {new Teacher().setFullName("Test teacher").setType(Teacher.Type.DOCENT),
                        new Group().setCombinedGroups(new Group[]{
                                new Group().setName("577"),
                                FIRST_LESSON.getGroup(),
                                SECOND_LESSON.getGroup()
                        }),
                        new Room().setName("5").setType(Room.Type.AUDITORY)},
                {FIRST_LESSON.getTeacher(), COMBINED.getGroup(), FIRST_LESSON.getRoom()},
        };
    }

    @DataProvider
    public static Object[][] daysDataProvider() {
        return new Object[][]{
                {DayOfWeek.MONDAY, DayOfWeek.THURSDAY,},
                {DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,},
                {DayOfWeek.SATURDAY, DayOfWeek.THURSDAY,}
        };
    }

    @Test(expected = BusyException.class)
    @UseDataProvider("lessonDataProvider")
    public void testAddLesson(Teacher teacher, Group group, Room room) {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY,
                new Lesson()
                        .setName("Math")
                        .setNumber(Lesson.Number.FIRST)
                        .setTeacher(teacher)
                        .setRoom(room)
                        .setGroup(group));
    }

    @Test
    @UseDataProvider("daysDataProvider")
    public void testGetDays(DayOfWeek... days) {
        Map<DayOfWeek, List<Lesson>> daysMap = TIME_TABLE_SERVICE.getDays(days);
        daysMap.forEach((key, value) -> assertTrue(Arrays.asList(days).contains(key)));
        assertTrue(days.length >= daysMap.size());
//        List<DayOfWeek> dayList = Arrays.asList(days);
//        for (Map.Entry<DayOfWeek, List<Lesson>> dayOfWeekListEntry : TIME_TABLE_SERVICE.getDays(days).entrySet()) {
//            assertTrue(dayList.contains(dayOfWeekListEntry.getKey()));
//        }
    }

    @Test
    public void testGetDaysByGroup() {
        assertTrue(TIME_TABLE_SERVICE.getDaysByGroup(FIRST_LESSON.getGroup(), DayOfWeek.MONDAY).size() == 1);
    }

    @Test
    public void testGetLessonsByDayAndGroup() {
        assertTrue(TIME_TABLE_SERVICE.getLessonsByDayAndGroup(DayOfWeek.MONDAY,
                FIRST_LESSON.getGroup()).size() == 1);
    }

}