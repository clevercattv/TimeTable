package com.clevercattv.table.serialize;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class SqlJsonSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectWriter WRITER = MAPPER.writer();

    private SqlJsonSerializer(){}

    public static void serialize(Map<String,String> lessons, String path) throws IOException {
        WRITER.writeValue(new File(path), lessons);
    }

    public static Map<String,String> deserialize(String path) throws IOException {
        return MAPPER.readValue(new FileInputStream(path), new TypeReference<Map<String,String>>() {});
    }


}