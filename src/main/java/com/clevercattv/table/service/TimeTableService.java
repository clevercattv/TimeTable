package com.clevercattv.table.service;

import com.clevercattv.table.exception.BusyException;
import com.clevercattv.table.exception.RoomBusyException;
import com.clevercattv.table.exception.TeacherBusyException;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.TimeTable;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeTableService {

    private TimeTable timeTable;

    public TimeTableService(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public void addLesson(DayOfWeek dayOfWeek, Lesson lesson) {
        List<Lesson> lessons = timeTable.getDayOfWeek()
                .computeIfAbsent(dayOfWeek, k -> new ArrayList<>());
        List<String> busyList = new ArrayList<>();
        for (Lesson item : lessons) {
            if (item.getNumber().equals(lesson.getNumber())) {
                if (item.getRoom().equals(lesson.getRoom())){
                    busyList.add(RoomBusyException.class.getSimpleName());
                }
                if (item.getTeacher().equals(lesson.getTeacher())){
                    busyList.add(TeacherBusyException.class.getSimpleName());
                }
                checkGroupsIsBusy(item.getGroup(), lesson.getGroup())
                        .ifPresent(busyList::add);
                if (busyList.size() > 0) {
                    throw new BusyException(busyList.toString());
                }
            }
        }
        lessons.add(lesson);
    }

    private Optional<String> checkGroupsIsBusy(Group group, Group newGroup) {
        if (!newGroup.isCombined() && !group.isCombined()) {
            if (group.equals(newGroup)) {
                return Optional.of(group.getName());
            }
            return Optional.empty();
        }
        if (newGroup.isCombined() && group.isCombined()) {
            List<String> busyGroups = new ArrayList<>();
            for (String groupItem : Arrays.asList(group.getName().split(Group.DIVIDER))) {
                for (String newGroupItem : Arrays.asList(newGroup.getName().split(Group.DIVIDER))) {
                    if (groupItem.equals(newGroupItem)) busyGroups.add(groupItem);
                }
            }
            if (busyGroups.isEmpty()){
                return Optional.empty();
            }
            return Optional.of(busyGroups.toString());
        }
        if (newGroup.isCombined() &&
                Arrays.asList(newGroup.getName().split(Group.DIVIDER)).contains(group.getName())) {
            return Optional.of(group.getName());
        }
        if (Arrays.asList(group.getName().split(Group.DIVIDER)).contains(newGroup.getName())){
            return Optional.of(newGroup.getName());
        }
        return Optional.empty();
    }

    public Map<DayOfWeek, List<Lesson>> getWeek() {
        return timeTable.getDayOfWeek();
    }

    public Map<DayOfWeek, List<Lesson>> getDays(DayOfWeek... days) {
        if (days.length == 0) throw new IllegalArgumentException("Days is empty!");
        return timeTable.getDayOfWeek()
                .entrySet()
                .stream()
                .filter(e -> Arrays.asList(days).contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<DayOfWeek, List<Lesson>> getDaysByGroup(Group group, DayOfWeek... days) {
        Map<DayOfWeek, List<Lesson>> groupWeekLessons = timeTable.getDayOfWeek().entrySet().stream()
                .filter(e -> Arrays.asList(days).contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<DayOfWeek, List<Lesson>> item : groupWeekLessons.entrySet()) {
            List<Lesson> lessons = item.getValue()
                    .stream()
                    .filter(e -> e.getGroup().equals(group))
                    .collect(Collectors.toList());
            if (lessons.isEmpty()) {
                groupWeekLessons.remove(item.getKey());
            } else {
                groupWeekLessons.replace(item.getKey(),lessons);
            }
        }
        return groupWeekLessons;
    }

    public Map<DayOfWeek, List<Lesson>> getWeekByGroup(Group group) {
        Map<DayOfWeek, List<Lesson>> groupWeekLessons = new HashMap<>();
        for (Map.Entry<DayOfWeek, List<Lesson>> item : timeTable.getDayOfWeek().entrySet()) {
            groupWeekLessons.put(item.getKey(),
                    item.getValue().stream()
                            .filter(e -> e.getGroup().equals(group))
                            .collect(Collectors.toList()));
        }
        return groupWeekLessons;
    }

    public List<Lesson> getLessonsByDayAndGroup(DayOfWeek day, Group group) {
        return timeTable.getDayOfWeek().get(day).stream()
                .filter(e -> e.getGroup().equals(group))
                .collect(Collectors.toList());
    }

    public List<Lesson> getLessonsByDay(DayOfWeek day) {
        return timeTable.getDayOfWeek().get(day);
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

}