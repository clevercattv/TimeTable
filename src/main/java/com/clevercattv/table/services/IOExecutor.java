package com.clevercattv.table.services;

import com.clevercattv.table.models.TimeTable;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IOExecutor {

    private static ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    public static Future<String> save(TimeTable timeTable, String path) {
        return EXECUTOR.submit(() -> {
            TimeTableJsonSerializer.serialize(timeTable, path);
            return "Saving " + path + " complete!";
        });
    }

    public static Future<TimeTable> load(String path) {
        return EXECUTOR.submit(() -> TimeTableJsonSerializer.deserialize(path));
    }

}
