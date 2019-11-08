package com.clevercattv.table.dto;

import com.clevercattv.table.model.Lesson;

public class LessonDTO {

    private String name;
    private String lessonTime;
    private String teacher;
    private String group;
    private String room;
    private String day;

    public LessonDTO() {
    }

    public LessonDTO toDto(Lesson lesson) {
        this.name = lesson.getName();
        this.lessonTime = lesson.getNumber().getStart().toString();
        this.teacher = lesson.getTeacher().getFullName() + " "
                + lesson.getTeacher().getType().getAbbreviation();
        this.group = lesson.getGroup().getName();
        this.room = lesson.getRoom().getName() + " "
                + lesson.getRoom().getType().getName();
        this.day = lesson.getDay().name();
        return this;
    }

    public String getName() {
        return name;
    }

    public String getLessonTime() {
        return lessonTime;
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
