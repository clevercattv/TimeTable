package com.clevercattv.table.models;

import com.clevercattv.table.exceptions.NamingException;
import com.clevercattv.table.services.TimeTableService;

import java.time.LocalTime;
import java.util.Objects;

public class Lesson {

    private String name;
    private Teacher teacher;
    private Number number;
    private Group group;
    private Room room;//

    private Lesson(){ }

    public static Lesson build(Teacher teacher, Number number,
                               Group group, String lessonName, Room room) {
        Lesson lesson = new Lesson();
        lesson.setTeacher(teacher);
        lesson.setNumber(number);
        lesson.setGroup(group);
        lesson.setName(lessonName);
        lesson.setRoom(room);
        return lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson schedule = (Lesson) o;
        return Objects.equals(teacher, schedule.teacher) &&
                number == schedule.number &&
                Objects.equals(group, schedule.group) &&
                Objects.equals(name, schedule.name) &&
                Objects.equals(room, schedule.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacher, number, group, name, room);
    }

    @Override
    public String toString() {
        return "\nLesson{" +
                "  lessonNumber=" + number.name() +
                ", group=" + group.getName() + ", " + group.isCombined() +
                ", name=" + name +
                ", teacher=" + teacher.getFullName() +
                ", auditory=" + room.getName() +
                '}';
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        if (teacher == null) throw new NullPointerException("Lesson teacher can't be null!");
        this.teacher = teacher;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        if (number == null) throw new NullPointerException("Lesson name number can't be null!");
        this.number = number;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        if (group == null) throw new NullPointerException("Lesson group can't be null!");
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 4) throw new NamingException("Lesson name length less than minimum.");
        if (name.length() > 48) throw new NamingException("Lesson name length more than maximum.");
        if (!TimeTableService.VALIDATION.matcher(name).matches()) {
            throw new NamingException("Lesson name have forbidden symbols. Please use 'a-z A-Z' ");
        }
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        if (room == null) throw new NullPointerException("Lesson auditory can't be null!");
        this.room = room;
    }

    public enum Number {

        FIRST(1, LocalTime.parse("08:20:00"), LocalTime.parse("09:40:00")),
        SECOND(2, LocalTime.parse("09:50:00"), LocalTime.parse("11:10:00")),
        THIRD(3, LocalTime.parse("11:30:00"), LocalTime.parse("12:50:00")),
        FOURTH(4, LocalTime.parse("13:00:00"), LocalTime.parse("14:20:00")),
        FIFTH(5, LocalTime.parse("14:40:00"), LocalTime.parse("16:00:00"));

        private final int number;
        private final LocalTime start;
        private final LocalTime end;

        Number(int number, LocalTime start, LocalTime end){
            this.number = number;
            this.start = start;
            this.end = end;
        }

        public int getNumber(){ return number; }

        public LocalTime getStart() { return start; }

        public LocalTime getEnd() { return end; }

    }

}