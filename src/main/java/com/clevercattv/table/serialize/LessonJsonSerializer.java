package com.clevercattv.table.serialize;

import com.clevercattv.table.model.Lesson;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class LessonJsonSerializer {

    private LessonJsonSerializer(){}

    public static void serialize(List<Lesson> lessons, String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(path), lessons);
    }

    public static List<Lesson> deserialize(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(new FileInputStream(path), new TypeReference<List<Lesson>>() {});
    }


}