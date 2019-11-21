package com.clevercattv.table.dto;

import com.clevercattv.table.model.Lesson;

public class CrudLessonDTO {

    private int id;
    private String name;
    private String number;
    private String teacher;
    private String group;
    private String room;
    private String day;

    public CrudLessonDTO toDto(Lesson lesson) {
        this.id = lesson.getId();
        this.name = lesson.getName();
        this.number = lesson.getNumber().name();
        this.teacher = lesson.getTeacher().getFullName();
        this.group = lesson.getGroup().getName();
        this.room = lesson.getRoom().getName();
        this.day = lesson.getDay().name();
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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