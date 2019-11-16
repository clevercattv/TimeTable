package com.clevercattv.table.dto;

import com.clevercattv.table.model.Lesson;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CrudLessonDTO {

    private static final List<String> dayOfWeek = Arrays.stream(DayOfWeek.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    private static final List<String> number = Arrays.stream(Lesson.Number.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    private List<ViewLessonDTO> lessons = new ArrayList<>();
    private List<Name_Id> teachers;
    private List<Name_Id> rooms;
    private List<Name_Id> groups;

    public CrudLessonDTO toDto(List<Lesson> lessons, List<Name_Id> teachers,
                               List<Name_Id> rooms, List<Name_Id> groups) {
        for (Lesson lesson : lessons) {
            this.lessons.add(new ViewLessonDTO().toDto(lesson));
        }
        this.teachers = teachers;
        this.rooms = rooms;
        this.groups = groups;
        return this;
    }

    public List<ViewLessonDTO> getLessons() {
        return lessons;
    }

    public List<String> getDayOfWeek() {
        return dayOfWeek;
    }

    public List<String> getNumber() {
        return number;
    }

    public List<Name_Id> getTeachers() {
        return teachers;
    }

    public List<Name_Id> getRooms() {
        return rooms;
    }

    public List<Name_Id> getGroups() {
        return groups;
    }
}
