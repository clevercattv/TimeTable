package com.clevercattv.table.dto;

import com.clevercattv.table.model.Lesson;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimeTableDTO {

    private Set<String> dayOfWeeks = new HashSet<>();
    private Set<String> lessonTime = new HashSet<>();
    private Set<String> groups = new HashSet<>();
    private List<LessonDTO> lessons = new ArrayList<>();

    public TimeTableDTO() { }

    public TimeTableDTO toDto(List<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            this.groups.add(lesson.getGroup().getName());
            this.lessonTime.add(lesson.getNumber().getStart().toString());
            this.dayOfWeeks.add(lesson.getDay().name());
            LessonDTO lessonDTO = new LessonDTO();
            this.lessons.add(lessonDTO.toDto(lesson));
        }
        return this;
    }

    public Set<String> getLessonTime() {
        return lessonTime;
    }

    public Set<String> getDayOfWeeks() {
        return dayOfWeeks;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public List<LessonDTO> getLessons() {
        return lessons;
    }
}