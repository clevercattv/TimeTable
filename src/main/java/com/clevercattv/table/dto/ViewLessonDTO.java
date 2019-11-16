package com.clevercattv.table.dto;

import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.validation.PerformedMessage;
import com.clevercattv.table.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class ViewLessonDTO {

    private int id;
    private String name;
    private String number;
    private String teacher;
    private String group;
    private String room;
    private String day;

    public ViewLessonDTO toDto(Lesson lesson) {
        this.id = lesson.getId();
        this.name = lesson.getName();
        this.number = lesson.getNumber().name();
        this.teacher = lesson.getTeacher().getFullName();
        this.group = lesson.getGroup().getName();
        this.room = lesson.getRoom().getName();
        this.day = lesson.getDay().name();
        return this;
    }

    public ViewLessonDTO toDto(HttpServletRequest req) {
        this.id = Integer.parseInt(req.getParameter("id"));
        this.name = req.getParameter("name");
        this.number = req.getParameter("number");
        this.teacher = req.getParameter("teacher");
        this.group = req.getParameter("group");
        this.room = req.getParameter("room");
        this.day = req.getParameter("day");
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