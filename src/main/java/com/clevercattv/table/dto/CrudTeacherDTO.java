package com.clevercattv.table.dto;

import com.clevercattv.table.model.Teacher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CrudTeacherDTO {

    private static final List<String> TYPES = Arrays.stream(Teacher.Type.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    private List<Teacher> teachers;

    public CrudTeacherDTO(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<String> getTypes() {
        return TYPES;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

}
