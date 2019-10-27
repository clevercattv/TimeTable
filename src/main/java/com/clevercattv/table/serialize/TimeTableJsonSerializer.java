package com.clevercattv.table.serialize;

import com.clevercattv.table.models.TimeTable;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.*;

public class TimeTableJsonSerializer {

    private static ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private static ObjectMapper MAPPER = new ObjectMapper();
    private static ObjectWriter WRITER;

    static {
        MAPPER.registerModule(new JavaTimeModule());
        WRITER = MAPPER.writer(new DefaultPrettyPrinter());
    }

    public static void serialize(TimeTable timeTable, String path) {
        EXECUTOR.submit(() -> {
            try {
                WRITER.writeValue(new File(path), timeTable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static Future<TimeTable> deserialize(String path) {
        return EXECUTOR.submit(() -> MAPPER.readValue(new FileInputStream(path), TimeTable.class));
    }


}