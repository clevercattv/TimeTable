package com.clevercattv.table.services;

import com.clevercattv.table.exceptions.BusyException;
import com.clevercattv.table.exceptions.RoomBusyException;
import com.clevercattv.table.exceptions.TeacherBusyException;
import com.clevercattv.table.models.Group;
import com.clevercattv.table.models.Room;
import com.clevercattv.table.models.Teacher;
import com.clevercattv.table.models.Lesson;
import com.clevercattv.table.models.TimeTable;

import java.time.DayOfWeek;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeTableService {

    public static final String VALIDATION = "^[a-z A-Z]+$";

    private TimeTable timeTable;
    private FilterBuilder filter = new FilterBuilder();

    public TimeTableService(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    private void addDay(DayOfWeek dayOfWeek, List<Lesson> lessons) {
        timeTable.getDayOfWeek().put(dayOfWeek,lessons);
    }

    public void addLesson(DayOfWeek dayOfWeek, Lesson lesson) {
        List<Lesson> lessons = timeTable.getDayOfWeek().get(dayOfWeek);
        if (lessons == null) {
            lessons = new ArrayList<>();
            addDay(dayOfWeek, lessons);
        }
        List<String> busyList = new ArrayList<>();
        for (Lesson item : lessons) {
            if (item.getNumber().equals(lesson.getNumber())) {
                if (item.getRoom().equals(lesson.getRoom())){
                    busyList.add(RoomBusyException.class.getSimpleName());
                }
                if (item.getTeacher().equals(lesson.getTeacher())){
                    busyList.add(TeacherBusyException.class.getSimpleName());
                }
                fillListByGroupNameIfBusy(busyList, item.getGroup(), lesson.getGroup());
                if (busyList.size() > 0) {
                    throw new BusyException(busyList.toString());
                }
            }
        }
        lessons.add(lesson);
    }

    private void fillListByGroupNameIfBusy(List<String> busyList, Group group, Group newGroup) {
        if (!newGroup.isCombined() && !group.isCombined()) {
            if (group.equals(newGroup)) busyList.add(group.getName());
            return;
        }
        if (newGroup.isCombined() && group.isCombined()) {
            List<String> busyGroups = new ArrayList<>();
            for (String groupItem : Arrays.asList(group.getName().split(Group.DIVIDER))) {
                for (String newGroupItem : Arrays.asList(newGroup.getName().split(Group.DIVIDER))) {
                    if (groupItem.equals(newGroupItem)) busyGroups.add(groupItem);
                }
            }
            busyList.add(busyGroups.toString());
            return;
        }
        if (newGroup.isCombined() &&
                Arrays.asList(newGroup.getName().split(Group.DIVIDER)).contains(group.getName())) {
            busyList.add(group.getName());
            return;
        }
        if (Arrays.asList(group.getName().split(Group.DIVIDER)).contains(newGroup.getName()))
            busyList.add(newGroup.getName());
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
            List<Lesson> lessons = item.getValue()
                    .stream()
                    .filter(e -> e.getGroup().equals(group))
                    .collect(Collectors.toList());
            if (!lessons.isEmpty()) groupWeekLessons.put(item.getKey(),lessons);
        }
        return groupWeekLessons;
    }

    public List<Lesson> getLessonsByDayAndGroup(DayOfWeek day, Group group) {
        return filter
                .startFilterByDay(day)
                .filterByGroup(group)
                .getResult();
    }

    public List<Lesson> getLessonsByDay(DayOfWeek day) {
        return timeTable.getDayOfWeek().get(day);
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }
    public FilterBuilder getFilterBuilder() {
        return filter;
    }

    public class FilterBuilder {

        Stream<Lesson> lessonStream;

        /**
         * Start filtering, so new use create new filter from the start
         */
        public FilterBuilder startFilterByDay(DayOfWeek day) {
            lessonStream = getLessonsByDay(day).stream();
            return this;
        }

        public FilterBuilder filterByGroup(Group group) {
            lessonStream = lessonStream.filter(e -> e.getGroup().equals(group));
            return this;
        }

        public FilterBuilder filterByTeacher(Teacher teacher) {
            lessonStream = lessonStream.filter(e -> e.getTeacher().equals(teacher));
            return this;
        }

        public FilterBuilder filterByLessonNumber(Lesson.Number number) {
            lessonStream = lessonStream.filter(e -> e.getNumber().equals(number));
            return this;
        }

        public FilterBuilder filterByLessonRoom(Room room) {
            lessonStream = lessonStream.filter(e -> e.getRoom().equals(room));
            return this;
        }

        public FilterBuilder filterByLessonName(String name) {
            lessonStream = lessonStream.filter(e -> e.getName().equals(name));
            return this;
        }

        public List<Lesson> getResult() {
            return lessonStream.collect(Collectors.toList());
        }

    }

}