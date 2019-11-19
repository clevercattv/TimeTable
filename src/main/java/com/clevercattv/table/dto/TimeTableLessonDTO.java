package com.clevercattv.table.dto;

import com.clevercattv.table.model.Lesson;

public class TimeTableLessonDTO {

    private String name;
    private String number;
    private String teacher;
    private String group;
    private String room;
    private String day;

    public TimeTableLessonDTO toDto(Lesson lesson) {
        this.name = lesson.getName();
        this.number = lesson.getNumber().getStart().toString();
        this.teacher = lesson.getTeacher().getFullName() + " " +
                lesson.getTeacher().getType().getAbbreviation();
        this.group = lesson.getGroup().getName();
        this.room = lesson.getRoom().getName() + " " +
                lesson.getRoom().getType().getName();
        this.day = lesson.getDay().name();
        return this;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getGroup() {
        return group;
    }

    public String getRoom() {
        return room;
    }

    public String getDay() {
        return day;
    }

}