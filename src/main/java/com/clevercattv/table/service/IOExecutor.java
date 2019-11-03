package com.clevercattv.table.service;

import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.serialize.LessonJsonSerializer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IOExecutor {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private static final String SAVE_COMPLETE = "Saving %s complete!";

    private IOExecutor(){}

    public static Future<String> save(List<Lesson> lessons, String path) {
        return EXECUTOR.submit(() -> {
            LessonJsonSerializer.serialize(lessons, path);
            return String.format(SAVE_COMPLETE, path);
        });
    }

    public static Future<List<Lesson>> load(String path) {
        return EXECUTOR.submit(() -> LessonJsonSerializer.deserialize(path));
    }

}
