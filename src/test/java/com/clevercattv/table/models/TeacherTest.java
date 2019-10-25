package com.clevercattv.table.models;

import com.clevercattv.table.MainTest;
import com.clevercattv.table.exceptions.BusyException;
import com.clevercattv.table.exceptions.NamingException;
import com.clevercattv.table.services.TimeTableService;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.DayOfWeek;
import java.time.LocalDate;

@RunWith(DataProviderRunner.class)
public class TeacherTest extends MainTest {

    private static final TimeTableService TIME_TABLE_SERVICE = new TimeTableService(new TimeTable(LocalDate.now()));
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, FIRST_LESSON);
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY, SECOND_LESSON);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test(expected = NamingException.class)
    @DataProvider({
            "Docent name1",
            "Doc",
            "Docent namesssssssssssssssssssssssssssssssssssssssssssssssssss"
    })
    public void testValidation(String str) {
        Teacher.build(str,Teacher.Type.DOCENT);
    }

//    public void testJavaxValidation() {
//        validator.validate(Teacher.build("Docsss norm",Teacher.Type.DOCENT),Teacher.class);
//    }

    @Test(expected = BusyException.class)
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
