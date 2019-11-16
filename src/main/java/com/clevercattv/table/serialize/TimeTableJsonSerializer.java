package com.clevercattv.table.serialize;

import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.TimeTable;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class TimeTableJsonSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectWriter WRITER = MAPPER.writer();

    private TimeTableJsonSerializer(){}

    public static void serialize(TimeTable timetable, String path) throws IOException {
        WRITER.writeValue(new File(path), timetable);
    }

    public static TimeTable deserialize(String path) throws IOException {
        return MAPPER.readValue(new FileInputStream(path), TimeTable.class);
    }


}