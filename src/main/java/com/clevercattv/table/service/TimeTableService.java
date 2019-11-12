package com.clevercattv.table.service;

import com.clevercattv.table.exception.BusyException;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.TimeTable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

public class TimeTableService {

    @JsonIgnore
    private static final String ROOM_BUSY = "ROOM";
    @JsonIgnore
    private static final String TEACHER_BUSY = "TEACHER";
    @JsonIgnore
    private static final String GROUP_BUSY = "GROUP";

    private TimeTable timeTable;

    public TimeTableService(){
        timeTable = new TimeTable(new ArrayList<>());
    }

    public TimeTableService(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public void addLesson(Lesson lesson) {
        List<String> busyList = new ArrayList<>();
        timeTable.getLessons().stream()
                .filter(e -> e.getDay().equals(lesson.getDay())
                        && e.getNumber().equals(lesson.getNumber()))
                .forEach(item -> {
                    if (item.getRoom().equals(lesson.getRoom())) {
                        busyList.add(ROOM_BUSY);
                    }
                    if (item.getTeacher().equals(lesson.getTeacher())) {
                        busyList.add(TEACHER_BUSY);
                    }
                    if (item.getGroup().equals(lesson.getGroup())){
                        busyList.add(GROUP_BUSY);
                    }
                });
        if (busyList.isEmpty()) {
            timeTable.getLessons().add(lesson);
        } else {
            throw new BusyException(busyList.toString());
        }
    }

    public List<Lesson> getLessons() {
        return timeTable.getLessons();
    }

    public List<Lesson> getDays(List<DayOfWeek> days) {
        if (days.isEmpty()) throw new IllegalArgumentException("Days is empty!");
        return timeTable.getLessons()
                .stream()
                .filter(e -> days.contains(e.getDay()))
                .collect(Collectors.toList());
    }

    public List<Lesson> getDaysByGroup(Group group, List<DayOfWeek> days) {
        return timeTable.getLessons().stream()
                .filter(e -> days.contains(e.getDay()) && group.equals(e.getGroup()))
                .collect(Collectors.toList());
    }

    public List<Lesson> getWeekByGroup(Group group) {
        return timeTable.getLessons().stream()
                .filter(e -> group.equals(e.getGroup()))
                .collect(Collectors.toList());
    }

    public List<Lesson> getLessonsByDayAndGroup(DayOfWeek day, Group group) {
        return timeTable.getLessons().stream()
                .filter(e -> e.getGroup().equals(group) && day.equals(e.getDay()))
                .collect(Collectors.toList());
    }

    public List<Lesson> getLessonsByDay(DayOfWeek day) {
        return timeTable.getLessons().stream()
                .filter(e -> day.equals(e.getDay()))
                .collect(Collectors.toList());
    }

}