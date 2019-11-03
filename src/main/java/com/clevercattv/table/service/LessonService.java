package com.clevercattv.table.service;

import com.clevercattv.table.exception.BusyException;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

public class LessonService {

    @JsonIgnore
    private static final String ROOM_BUSY = "ROOM";
    @JsonIgnore
    private static final String TEACHER_BUSY = "TEACHER";

    private List<Lesson> lessons;

    public LessonService(){
        lessons = new ArrayList<>();
    }

    public LessonService(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void addLesson(Lesson lesson) {
        List<String> busyList = new ArrayList<>();
        lessons.stream()
                .filter(e -> e.getDay().equals(lesson.getDay())
                        && e.getNumber().equals(lesson.getNumber()))
                .forEach(item -> {
                    if (item.getRoom().equals(lesson.getRoom())) {
                        busyList.add(ROOM_BUSY);
                    }
                    if (item.getTeacher().equals(lesson.getTeacher())) {
                        busyList.add(TEACHER_BUSY);
                    }
                    getBusyGroupsNames(item.getGroup(), lesson.getGroup())
                            .ifPresent(busyList::add);
                });
        if (busyList.isEmpty()) {
            lessons.add(lesson);
        } else {
            throw new BusyException(busyList.toString());
        }
    }

    private Optional<String> getBusyGroupsNames(Group group, Group newGroup) {
        List<String> busyGroups = new ArrayList<>();
        for (String groupItem : group.getName().split(Group.DIVIDER)) {
            for (String newGroupItem : newGroup.getName().split(Group.DIVIDER)) {
                if (groupItem.equals(newGroupItem)) busyGroups.add(groupItem);
            }
        }
        return busyGroups.isEmpty() ? Optional.empty() : Optional.of(busyGroups.toString());
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Lesson> getDays(List<DayOfWeek> days) {
        if (days.isEmpty()) throw new IllegalArgumentException("Days is empty!");
        return lessons
                .stream()
                .filter(e -> days.contains(e.getDay()))
                .collect(Collectors.toList());
    }

    public List<Lesson> getDaysByGroup(Group group, List<DayOfWeek> days) {
        return lessons.stream()
                .filter(e -> days.contains(e.getDay()) && group.equals(e.getGroup()))
                .collect(Collectors.toList());
    }

    public List<Lesson> getWeekByGroup(Group group) {
        return lessons.stream()
                .filter(e -> group.equals(e.getGroup()))
                .collect(Collectors.toList());
    }

    public List<Lesson> getLessonsByDayAndGroup(DayOfWeek day, Group group) {
        return lessons.stream()
                .filter(e -> e.getGroup().equals(group) && day.equals(e.getDay()))
                .collect(Collectors.toList());
    }

    public List<Lesson> getLessonsByDay(DayOfWeek day) {
        return lessons.stream()
                .filter(e -> day.equals(e.getDay()))
                .collect(Collectors.toList());
    }

}