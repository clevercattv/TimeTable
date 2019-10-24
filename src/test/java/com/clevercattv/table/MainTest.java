package com.clevercattv.table;

import com.clevercattv.table.exceptions.*;
import com.clevercattv.table.models.Group;
import com.clevercattv.table.models.Room;
import com.clevercattv.table.models.Teacher;
import com.clevercattv.table.services.TimeTableService;
import com.clevercattv.table.models.Lesson;
import com.clevercattv.table.models.TimeTable;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {

    static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService(new TimeTable(LocalDate.now()));

    static final Lesson FIRST_LESSON = Lesson.build(
            Teacher.build("Docent name",Teacher.Type.DOCENT),
            Lesson.Number.FIRST,
            Group.build("509"),
            "Math",
            Room.build("10", Room.Type.AUDITORY)
    );

    static final Lesson SECOND_LESSON = Lesson.build(
            Teacher.build("Professor name",Teacher.Type.PROFESSOR),
            Lesson.Number.FIRST,
            Group.build("508"),
            "Math",
            Room.build("11", Room.Type.LABORATORY)
    );

    @BeforeClass
    public static void beforeClass() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, FIRST_LESSON);
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, SECOND_LESSON);
    }

    // Complex exception
    @Test(expected = BusyException.class)
    public void testBusyException() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY,
                Lesson.build(
                        Teacher.build("Docent name",Teacher.Type.DOCENT),
                        Lesson.Number.FIRST,
                        Group.build("509"),
                        "Math",
                        Room.build("10", Room.Type.AUDITORY)
                )
        );
    }

}
