package com.clevercattv.table.serialize;

import com.clevercattv.table.model.Lesson;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class LessonJsonSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectWriter WRITER = MAPPER.writer();

    private LessonJsonSerializer(){}

    public static void serialize(List<Lesson> lessons, String path) throws IOException {
        WRITER.writeValue(new File(path), lessons);
    }

    public static List<Lesson> deserialize(String path) throws IOException {
        return MAPPER.readValue(new FileInputStream(path), new TypeReference<List<Lesson>>() {});
    }


}