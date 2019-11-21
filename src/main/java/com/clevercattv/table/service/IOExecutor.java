package com.clevercattv.table.service;

import com.clevercattv.table.model.TimeTable;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IOExecutor {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private static final String SAVE_COMPLETE = "Saving %s complete!";

    private IOExecutor(){}

    public static Future<String> save(TimeTable timeTable, String path) {
        return EXECUTOR.submit(() -> {
            TimeTableJsonSerializer.serialize(timeTable, path);
            return String.format(SAVE_COMPLETE, path);
        });
    }

    public static Future<TimeTable> load(URL path) {
        return EXECUTOR.submit(() -> TimeTableJsonSerializer.deserialize(path));
    }

    public static Future<TimeTable> load(String path) {
        return EXECUTOR.submit(() -> TimeTableJsonSerializer.deserialize(path));
    }

}
