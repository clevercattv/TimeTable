package com.clevercattv.table.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTable {

    private final LocalDate firstSemesterDay;
    private final LocalDate lastSemesterDay;
    private Map<DayOfWeek, List<Lesson>> dayOfWeek = new HashMap<>();

    private static int COUNT_OF_WEEKS = 15; // semester weeks

    private TimeTable() {
        this.firstSemesterDay = null;
        this.lastSemesterDay = null;
    }

    public TimeTable(LocalDate firstSemesterDay) {
        this.firstSemesterDay = firstSemesterDay;
        this.lastSemesterDay = firstSemesterDay.plusDays(firstSemesterDay.getDayOfYear() + (COUNT_OF_WEEKS * 7));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        dayOfWeek.forEach((key, value) -> builder.append(Arrays.toString(value.toArray())));
        return "TimeTable{" +
                "firstSemesterDay=" + firstSemesterDay +
                ", lastSemesterDay=" + lastSemesterDay +
                ", dayOfWeek=" + builder.toString() +
                '}';
    }

    public LocalDate getFirstSemesterDay() {
        return firstSemesterDay;
    }

    public Map<DayOfWeek, List<Lesson>> getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Map<DayOfWeek, List<Lesson>> dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDate getLastSemesterDay() {
        return lastSemesterDay;
    }



}