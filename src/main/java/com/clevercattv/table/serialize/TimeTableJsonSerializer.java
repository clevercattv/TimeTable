package com.clevercattv.table.serialize;

import com.clevercattv.table.model.TimeTable;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TimeTableJsonSerializer {

    private static ObjectMapper MAPPER = new ObjectMapper();
    private static ObjectWriter WRITER;

    static {
        MAPPER.registerModule(new JavaTimeModule());
        WRITER = MAPPER.writer(new DefaultPrettyPrinter());
    }

    public static void serialize(TimeTable timeTable, String path) {
        try {
            WRITER.writeValue(new File(path), timeTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TimeTable deserialize(String path) {
        try {
            return MAPPER.readValue(new FileInputStream(path), TimeTable.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return EXECUTOR.submit(() -> MAPPER.readValue(new FileInputStream(path), TimeTable.class));
        return null;
    }


}