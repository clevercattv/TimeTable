package com.clevercattv.table.model;

import java.util.List;
import java.util.Objects;

public class TimeTable {

    private List<Lesson> lessons;

    private TimeTable(){}

    public TimeTable(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeTable timeTable = (TimeTable) o;
        return lessons.equals(timeTable.lessons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessons);
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

}
