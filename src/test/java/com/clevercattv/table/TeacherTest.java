package com.clevercattv.table;

import com.clevercattv.table.exceptions.NamingException;
import com.clevercattv.table.exceptions.TeacherBusyException;
import com.clevercattv.table.models.Group;
import com.clevercattv.table.models.Room;
import com.clevercattv.table.models.Teacher;
import com.clevercattv.table.services.TimeTableService;
import com.clevercattv.table.models.Lesson;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DayOfWeek;

import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class TeacherTest extends MainTest {

    @Test(expected = NamingException.class)
    @DataProvider({
            "Docent name1",
            "Doc",
            "Docent namesssssssssssssssssssssssssssssssssssssssssssssssssss"
    })
    public void testValidation(String str) {
        Teacher.build(str,Teacher.Type.DOCENT);
    }

    @Test(expected = TeacherBusyException.class)
    public void testTeacherBusyException() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY,
                Lesson.build(
                        Teacher.build("Docent name",Teacher.Type.DOCENT),
                        Lesson.Number.FIRST,
                        Group.build("506"),
                        "Math",
                        Room.build("11", Room.Type.AUDITORY)
                )
        );
    }


}
