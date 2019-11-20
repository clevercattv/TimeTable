package com.clevercattv.table.dto;

import com.clevercattv.table.model.Lesson;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TimeTableModelsDTO {

    private static final List<String> dayOfWeek = Arrays.stream(DayOfWeek.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    private static final List<String> number = Arrays.stream(Lesson.Number.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    private List<CrudLessonDTO> lessons = new ArrayList<>();
    private List<NameIdDTO> teachers;
    private List<NameIdDTO> rooms;
    private List<NameIdDTO> groups;

    public TimeTableModelsDTO toDto(List<Lesson> lessons, List<NameIdDTO> teachers,
                                    List<NameIdDTO> rooms, List<NameIdDTO> groups) {
        for (Lesson lesson : lessons) {
            this.lessons.add(new CrudLessonDTO().toDto(lesson));
        }
        this.teachers = teachers;
        this.rooms = rooms;
        this.groups = groups;
        return this;
    }

    public List<CrudLessonDTO> getLessons() {
        return lessons;
    }

    public List<String> getDayOfWeek() {
        return dayOfWeek;
    }

    public List<String> getNumber() {
        return number;
    }

    public List<NameIdDTO> getTeachers() {
        return teachers;
    }

    public List<NameIdDTO> getRooms() {
        return rooms;
    }

    public List<NameIdDTO> getGroups() {
        return groups;
    }

}
