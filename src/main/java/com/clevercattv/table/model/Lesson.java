package com.clevercattv.table.model;

import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.validation.PerformedMessage;
import com.clevercattv.table.validation.Validator;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

public class Lesson implements EntityId<Lesson> {

    public static final int MIN_NAME_LENGTH = 4;
    public static final int MAX_NAME_LENGTH = 32;
    public static final String NAME_PATTERN = "^[a-z A-Z]+$";

    private int id;
    private String name;
    private Number number;
    private Teacher teacher;
    private Group group;
    private Room room;//
    private DayOfWeek day;

    public Lesson() {
    }

    public Lesson(int id, String name, Number number, Teacher teacher, Group group, Room room) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.teacher = teacher;
        this.group = group;
        this.room = room;
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
                "  id=" + id +
                "  lessonNumber=" + number +
                ", group=" + group +
                ", name=" + name +
                ", teacher=" + teacher +
                ", auditory=" + room +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Lesson setId(int id) {
        this.id = id;
        return this;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Lesson setTeacher(Teacher teacher) {
        if (teacher == null) throw new NullPointerException("Lesson teacher can't be null.");
        this.teacher = teacher;
        return this;
    }

    public Number getNumber() {
        return number;
    }

    public Lesson setNumber(Number number) {
        if (number == null) throw new NullPointerException("Lesson number can't be null.");
        this.number = number;
        return this;
    }

    public Group getGroup() {

        return group;
    }

    public Lesson setGroup(Group group) {
        if (group == null) throw new NullPointerException("Lesson group can't be null.");
        this.group = group;
        return this;
    }

    public String getName() {
        return name;
    }

    public Lesson setName(String name) {
        Validator.getMessagesByPerformedTrue(Arrays.asList(
                new PerformedMessage("Lesson name length less than minimum.",
                        name.length() < MIN_NAME_LENGTH),
                new PerformedMessage("Lesson name length more than maximum.",
                        name.length() > MAX_NAME_LENGTH),
                new PerformedMessage("Lesson name have forbidden symbols. Please use 'a-z A-Z'",
                        !name.matches(NAME_PATTERN))
        ));
        this.name = name;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Lesson setRoom(Room room) {
        if (room == null) throw new NullPointerException("Lesson room can't be null.");
        this.room = room;
        return this;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public Lesson setDay(DayOfWeek day) {
        this.day = day;
        return this;
    }

    public enum Number {

        FIRST(1, LocalTime.parse("08:20:00"), LocalTime.parse("09:40:00")),
        SECOND(2, LocalTime.parse("09:50:00"), LocalTime.parse("11:10:00")),
        THIRD(3, LocalTime.parse("11:30:00"), LocalTime.parse("12:50:00")),
        FOURTH(4, LocalTime.parse("13:00:00"), LocalTime.parse("14:20:00")),
        FIFTH(5, LocalTime.parse("14:40:00"), LocalTime.parse("16:00:00"));

        private final int id;
        private final LocalTime start;
        private final LocalTime end;

        Number(int id, LocalTime start, LocalTime end) {
            this.id = id;
            this.start = start;
            this.end = end;
        }

        public int getId() {
            return id;
        }

        public LocalTime getStart() {
            return start;
        }

        public LocalTime getEnd() {
            return end;
        }

    }

}