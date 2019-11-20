package com.clevercattv.table;

import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;
import com.clevercattv.table.service.TimeTableService;
import org.junit.BeforeClass;

import java.io.IOException;
import java.net.URL;

public class TestData {

    protected static TimeTableService TIME_TABLE_SERVICE;
    protected static URL TEST_JSON_URL = Thread.currentThread()
            .getContextClassLoader()
            .getResource("TestBase.json");

    protected static Lesson FIRST_LESSON;
    protected static Lesson SECOND_LESSON;
    protected static Lesson THIRD_LESSON;

    @BeforeClass
    public static void beforeClass() throws IOException {
        TIME_TABLE_SERVICE = new TimeTableService(TimeTableJsonSerializer.deserialize(TEST_JSON_URL));
        FIRST_LESSON = TIME_TABLE_SERVICE.getLessons().get(0);
        SECOND_LESSON = TIME_TABLE_SERVICE.getLessons().get(1);
        THIRD_LESSON = TIME_TABLE_SERVICE.getLessons().get(2);
    }

}