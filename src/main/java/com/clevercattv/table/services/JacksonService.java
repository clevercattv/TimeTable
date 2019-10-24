package com.clevercattv.table.services;

import com.clevercattv.table.models.TimeTable;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JacksonService {

    public void saveTimeTable(TimeTable timeTable, String path) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File(path), timeTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TimeTable readTimeTable(String path) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(new FileInputStream(path), TimeTable.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}