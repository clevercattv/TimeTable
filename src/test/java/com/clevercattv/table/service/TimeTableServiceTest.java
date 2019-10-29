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
public class TimeTableServiceTest extends MainTest {

    private static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService(new TimeTable(LocalDate.now()));

    @BeforeClass
    public static void beforeClass() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, FIRST_LESSON);
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, SECOND_LESSON);
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, THIRD_LESSON);
    }

    @DataProvider
    public static Object[][] lessonDataProvider() {
        return new Object[][] {
                { FIRST_LESSON.getTeacher(), Group.build("577"),
                        Room.build("5", Room.Type.AUDITORY) },
                { Teacher.build("Test teacher",Teacher.Type.DOCENT),
                        FIRST_LESSON.getGroup(), Room.build("5", Room.Type.AUDITORY) },
                { Teacher.build("Test teacher",Teacher.Type.DOCENT),
                        Group.build("577"), FIRST_LESSON.getRoom() },
                { Teacher.build("Test teacher",Teacher.Type.DOCENT),
                        FIRST_LESSON.getGroup(), FIRST_LESSON.getRoom() },
                { FIRST_LESSON.getTeacher(), Group.build("577"), FIRST_LESSON.getRoom() },
                { FIRST_LESSON.getTeacher(), FIRST_LESSON.getGroup(), Room.build("5", Room.Type.AUDITORY) },
                { FIRST_LESSON.getTeacher(), FIRST_LESSON.getGroup(), FIRST_LESSON.getRoom() },
                { Teacher.build("Test teacher",Teacher.Type.DOCENT),
                        Group.build(Group.build("577"),FIRST_LESSON.getGroup()),
                        Room.build("5", Room.Type.AUDITORY) },
        };
    }

    @DataProvider
    public static Object[][] daysDataProvider() {
        return new Object[][] {
                { DayOfWeek.MONDAY, DayOfWeek.THURSDAY, },
                { DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, },
                { DayOfWeek.SATURDAY, DayOfWeek.THURSDAY, }
        };
    }

    @Test(expected = BusyException.class)
    @UseDataProvider("lessonDataProvider")
    public void testAddLesson(Teacher teacher, Group group, Room room) {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY,
                Lesson.build(teacher,Lesson.Number.FIRST,group,"Math",room));
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

    @Test(expected = IllegalArgumentException.class)
    public void testGetDaysWithArgumentEmpty() {
        TIME_TABLE_SERVICE.getDays();
    }

    @Test
    public void testGetDaysByGroup() {
        assertTrue(TIME_TABLE_SERVICE.getDaysByGroup(FIRST_LESSON.getGroup(),DayOfWeek.MONDAY).size() == 1);
    }

    @Test
    public void testGetLessonsByDayAndGroup() {
        assertTrue(TIME_TABLE_SERVICE.getLessonsByDayAndGroup(DayOfWeek.MONDAY,
                FIRST_LESSON.getGroup()).size() == 1);
    }

    @DataProvider
    public static Object[] filterDataProvider() {
        return new Object[] { FIRST_LESSON, SECOND_LESSON };
    }

    @DataProvider
    public static Object[][] filtersDataProvider() {
        return new Object[][] {
                {TIME_TABLE_SERVICE.getFilterBuilder()
                        .startFilterByDay(DayOfWeek.MONDAY)
                        .filterByLessonRoom(FIRST_LESSON.getRoom())
                        .getResult(), FIRST_LESSON },
                {TIME_TABLE_SERVICE.getFilterBuilder()
                        .startFilterByDay(DayOfWeek.MONDAY)
                        .filterByLessonRoom(SECOND_LESSON.getRoom())
                        .getResult(), SECOND_LESSON },
                {TIME_TABLE_SERVICE.getFilterBuilder()
                        .startFilterByDay(DayOfWeek.MONDAY)
                        .filterByLessonName(FIRST_LESSON.getName())
                        .getResult(), FIRST_LESSON },
                {TIME_TABLE_SERVICE.getFilterBuilder()
                        .startFilterByDay(DayOfWeek.MONDAY)
                        .filterByLessonName(SECOND_LESSON.getName())
                        .getResult(), SECOND_LESSON },
                {TIME_TABLE_SERVICE.getFilterBuilder()
                        .startFilterByDay(DayOfWeek.MONDAY)
                        .filterByGroup(FIRST_LESSON.getGroup())
                        .getResult(), FIRST_LESSON },
                {TIME_TABLE_SERVICE.getFilterBuilder()
                        .startFilterByDay(DayOfWeek.MONDAY)
                        .filterByGroup(SECOND_LESSON.getGroup())
                        .getResult(), SECOND_LESSON },
                {TIME_TABLE_SERVICE.getFilterBuilder()
                        .startFilterByDay(DayOfWeek.MONDAY)
                        .filterByLessonNumber(FIRST_LESSON.getNumber())
                        .getResult(), FIRST_LESSON },
                {TIME_TABLE_SERVICE.getFilterBuilder()
                        .startFilterByDay(DayOfWeek.MONDAY)
                        .filterByLessonNumber(THIRD_LESSON.getNumber())
                        .getResult(), THIRD_LESSON },
                {TIME_TABLE_SERVICE.getFilterBuilder()
                        .startFilterByDay(DayOfWeek.MONDAY)
                        .filterByTeacher(FIRST_LESSON.getTeacher())
                        .getResult(), FIRST_LESSON },
                {TIME_TABLE_SERVICE.getFilterBuilder()
                        .startFilterByDay(DayOfWeek.MONDAY)
                        .filterByTeacher(SECOND_LESSON.getTeacher())
                        .getResult(), SECOND_LESSON }
        };
    }

    @Test
    public void testStartFilterByDay() {
        assertTrue(TIME_TABLE_SERVICE.getFilterBuilder().startFilterByDay(DayOfWeek.MONDAY)
                .getClass().getSimpleName().equals(TimeTableService.FilterBuilder.class.getSimpleName()));
    }

    @Test
    @UseDataProvider("filtersDataProvider")
    public void testFilters(List<Lesson> lessons, Lesson lesson) {
        assertTrue(lessons.stream()
                .findFirst()
                .orElseThrow(NullPointerException::new)
                .equals(lesson));
    }


    @Test
    public void getResult() {
        DayOfWeek day = DayOfWeek.MONDAY;
        assertTrue(TIME_TABLE_SERVICE.getFilterBuilder()
                .startFilterByDay(day)
                .getResult().size() == TIME_TABLE_SERVICE.getLessonsByDay(day).size());
    }


}
