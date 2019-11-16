package com.clevercattv.table.dto;

import com.clevercattv.table.model.Lesson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TimeTableDTO {

    private Set<String> dayOfWeek = new HashSet<>();
    private List<String> lessonTime = new ArrayList<>();
    private Set<String> groups = new HashSet<>();
    private List<ViewLessonDTO> lessons = new ArrayList<>();

    public TimeTableDTO toDto(List<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            this.groups.add(lesson.getGroup().getName());
            this.lessonTime.add(lesson.getNumber().getStart().toString());
            this.dayOfWeek.add(lesson.getDay().name());
            this.lessons.add(new ViewLessonDTO().toDto(lesson));
        }
        this.lessonTime = this.lessonTime.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        return this;
    }

    @Override
    public String toString() {
        return "TimeTableDTO{" +
                "dayOfWeek=" + dayOfWeek +
                ", lessonTime=" + lessonTime +
                ", groups=" + groups +
                ", lessons=" + lessons +
                '}';
    }

    public List<String> getLessonTime() {
        return lessonTime;
    }

    public Set<String> getDayOfWeek() {
        return dayOfWeek;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public List<ViewLessonDTO> getLessons() {
        return lessons;
    }

}