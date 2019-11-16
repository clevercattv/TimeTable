package com.clevercattv.table.model;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.dao.TeacherDao;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TimeTableFilter {

    private static final List<String> TEACHER_TYPES = Arrays.stream(Teacher.Type.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    private static final List<String> ROOM_TYPES = Arrays.stream(Room.Type.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    private static final List<String> DAY_OF_WEEK = Arrays.stream(DayOfWeek.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    private List<String> groups;
    private List<String> teachers;
    private List<String> rooms;

    private TimeTableFilter() {
    }

    public static TimeTableFilter build() throws SQLException {
        TimeTableFilter timeTableFilter = new TimeTableFilter();
        timeTableFilter.groups = GroupDao.getInstance()
                .findAll()
                .stream()
                .map(Group::getName)
                .collect(Collectors.toList());
        timeTableFilter.teachers = TeacherDao.getInstance()
                .findAll()
                .stream()
                .map(Teacher::getFullName)
                .collect(Collectors.toList());
        timeTableFilter.rooms = RoomDao.getInstance()
                .findAll()
                .stream()
                .map(Room::getName)
                .collect(Collectors.toList());
        return timeTableFilter;
    }

    public List<String> getTeacherTypes() {
        return TEACHER_TYPES;
    }

    public List<String> getRoomTypes() {
        return ROOM_TYPES;
    }

    public List<String> getDayOfWeek() {
        return DAY_OF_WEEK;
    }

    public List<String> getGroups() {
        return groups;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public List<String> getRooms() {
        return rooms;
    }

}