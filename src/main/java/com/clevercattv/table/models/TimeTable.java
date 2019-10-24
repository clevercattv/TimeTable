package com.clevercattv.table.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

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

    public TimeTable(LocalDate firstSemesterDay, Map<DayOfWeek, List<Lesson>> dayOfWeek) {
        this.firstSemesterDay = firstSemesterDay;
        this.lastSemesterDay = firstSemesterDay.plusDays(firstSemesterDay.getDayOfYear() + (COUNT_OF_WEEKS * 7));
        this.dayOfWeek = dayOfWeek;
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

    public TimeTable(LocalDate firstSemesterDay, int countOfWeeks) {
        this.firstSemesterDay = firstSemesterDay;
        COUNT_OF_WEEKS = countOfWeeks;
        this.lastSemesterDay = firstSemesterDay.plusDays(firstSemesterDay.getDayOfYear() + (countOfWeeks * 7));
        Arrays.asList(DayOfWeek.values()).forEach(e -> dayOfWeek.put(e,new ArrayList<>()));
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