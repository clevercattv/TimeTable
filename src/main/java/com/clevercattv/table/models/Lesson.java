package com.clevercattv.table.models;

import com.clevercattv.table.services.TimeTableService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Objects;

public class Lesson {

    public final static int MIN_NAME_LENGTH = 10;
    public final static int MAX_NAME_LENGTH = 48;

    @NotEmpty
    @Pattern(regexp = TimeTableService.VALIDATION)
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    private String name;

    @Valid
    @NotNull
    private Teacher teacher;

    @NotNull
    private Number number;

    @Valid
    @NotNull
    private Group group;

    @Valid
    @NotNull
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
        this.teacher = teacher;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
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