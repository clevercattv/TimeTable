package com.clevercattv.table.service;

import com.clevercattv.table.TestData;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IOExecutorTest extends TestData {

    private static String TEST_FILE = "IOExecutorTest.json";

    @AfterClass
    public static void afterClass() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            new File(TEST_FILE).delete();
        }
    }

    @Test
    public void save() throws ExecutionException, InterruptedException {
        IOExecutor.save(TIME_TABLE_SERVICE.getTimeTable(), TEST_FILE).get();
        assertTrue(new File(TEST_FILE).exists());
    }

    @Test
    public void load() throws ExecutionException, InterruptedException {
        assertEquals(IOExecutor.load(TEST_JSON_URL).get(), TIME_TABLE_SERVICE.getTimeTable());
    }

}