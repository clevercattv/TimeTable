package com.clevercattv.table.dto;

import com.clevercattv.table.model.Lesson;

import java.time.DayOfWeek;

public class LessonDTO extends Lesson {

    private DayOfWeek day;

    public LessonDTO(Lesson lesson, DayOfWeek day) {
        super(lesson.getId(), lesson.getName(), lesson.getNumber(),
                lesson.getTeacher(), lesson.getGroup(), lesson.getRoom());
        this.day = day;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }
}
