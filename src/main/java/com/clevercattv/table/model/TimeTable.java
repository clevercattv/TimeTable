package com.clevercattv.table.model;

import java.util.List;

public class TimeTable {

    private List<Lesson> lessons;

    public TimeTable(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

}
