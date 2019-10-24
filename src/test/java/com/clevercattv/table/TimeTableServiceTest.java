package com.clevercattv.table;

import com.clevercattv.table.exceptions.BusyException;
import com.clevercattv.table.models.Group;
import com.clevercattv.table.models.Lesson;
import com.clevercattv.table.models.Room;
import com.clevercattv.table.models.Teacher;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class TimeTableServiceTest extends MainTest {

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
    public void testGetDaysByArgumentEmpty() {
        TIME_TABLE_SERVICE.getDays();
    }

    @Test
    public void testGetLessonsByDayAndGroup() {
        
    }

}
