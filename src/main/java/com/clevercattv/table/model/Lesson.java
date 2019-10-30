package com.clevercattv.table.model;

import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.validation.Validator;
import com.clevercattv.table.validation.PerformedMessage;

import java.time.LocalTime;
import java.util.Objects;

public class Lesson {

    public final static int MIN_NAME_LENGTH = 4;
    public final static int MAX_NAME_LENGTH = 32;
    public final static String NAME_PATTERN = "^[a-z A-Z]+$";

    private String name;
    private Teacher teacher;
    private Number number;
    private Group group;
    private Room room;//

    private Lesson() {
    }

    public static Lesson build(Teacher teacher, Number number,
                               Group group, String lessonName, Room room) {
        return new Lesson()
                .setTeacher(teacher)
                .setNumber(number)
                .setGroup(group)
                .setName(lessonName)
                .setRoom(room);
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

    public Lesson setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public Number getNumber() {
        return number;
    }

    public Lesson setNumber(Number number) {
        this.number = number;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public Lesson setGroup(Group group) {
        this.group = group;
        return this;
    }

    public String getName() {
        return name;
    }

    public Lesson setName(String name) {
        Validator.filterByPerformedTrueAndResultMessagesToString(new PerformedMessage[]{
                new PerformedMessage("Lesson name length less than minimum.",
                        name.length() < MIN_NAME_LENGTH),
                new PerformedMessage("Lesson name length more than maximum.",
                        name.length() > MAX_NAME_LENGTH),
                new PerformedMessage("Lesson name have forbidden symbols. Please use 'a-z A-Z'",
                        !name.matches(NAME_PATTERN)),
        }).ifPresent(e -> {
            throw new NamingException(e);
        });
        this.name = name;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Lesson setRoom(Room room) {
        this.room = room;
        return this;
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

        Number(int number, LocalTime start, LocalTime end) {
            this.number = number;
            this.start = start;
            this.end = end;
        }

        public int getNumber() {
            return number;
        }

        public LocalTime getStart() {
            return start;
        }

        public LocalTime getEnd() {
            return end;
        }

    }

}