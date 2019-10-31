package com.clevercattv.table.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class TimeTable {

    private final LocalDate firstSemesterDay;
    private final LocalDate lastSemesterDay;
    private Map<DayOfWeek, List<Lesson>> dayOfWeek = new EnumMap<>(DayOfWeek.class);

    private static int countOfWeeks = 15; // semester weeks

    private TimeTable() {
        this.firstSemesterDay = null;
        this.lastSemesterDay = null;
    }

    public TimeTable(LocalDate firstSemesterDay) {
        this.firstSemesterDay = firstSemesterDay;
        this.lastSemesterDay = firstSemesterDay.plusDays(firstSemesterDay.getDayOfYear() + (long)(countOfWeeks * 7));
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