package com.clevercattv.table;

import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;

import java.time.DayOfWeek;

public class MainTest {

    protected static final Teacher FIRST_TEACHER = new Teacher()
            .setFullName("Docent name")
            .setType(Teacher.Type.DOCENT);

    protected static final Teacher SECOND_TEACHER = new Teacher()
            .setFullName("Professor name")
            .setType(Teacher.Type.PROFESSOR);

    protected static final Teacher THIRD_TEACHER = new Teacher()
            .setFullName("Post Graduate Name")
            .setType(Teacher.Type.POST_GRADUATE);

    protected static final Room FIRST_ROOM = new Room()
            .setName("10")
            .setType(Room.Type.AUDITORY);

    protected static final Room SECOND_ROOM = new Room()
            .setName("11")
            .setType(Room.Type.LABORATORY);

    protected static final Room THIRD_ROOM = new Room()
            .setName("12")
            .setType(Room.Type.LABORATORY);

    protected static final Group FIRST_GROUP = new Group()
            .setName("509");

    protected static final Group SECOND_GROUP = new Group()
            .setName("508");

    protected static final Lesson FIRST_LESSON = new Lesson()
            .setName("Math")
            .setNumber(Lesson.Number.FIRST)
            .setTeacher(FIRST_TEACHER)
            .setRoom(FIRST_ROOM)
            .setGroup(FIRST_GROUP)
            .setDay(DayOfWeek.MONDAY);

    protected static final Lesson SECOND_LESSON = new Lesson()
            .setName("Physic")
            .setNumber(Lesson.Number.FIRST)
            .setTeacher(SECOND_TEACHER)
            .setRoom(SECOND_ROOM)
            .setGroup(SECOND_GROUP)
            .setDay(DayOfWeek.MONDAY);

    protected static final Lesson THIRD_LESSON = new Lesson()
            .setName("Physic")
            .setNumber(Lesson.Number.THIRD)
            .setTeacher(THIRD_TEACHER)
            .setRoom(SECOND_ROOM)
            .setGroup(SECOND_GROUP)
            .setDay(DayOfWeek.MONDAY);


}
