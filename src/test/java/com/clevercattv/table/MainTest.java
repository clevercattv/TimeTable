package com.clevercattv.table;

import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;

public class MainTest {

    protected static final Lesson FIRST_LESSON = new Lesson()
            .setName("Math")
            .setNumber(Lesson.Number.FIRST)
            .setTeacher(
                    new Teacher()
                            .setFullName("Docent name")
                            .setType(Teacher.Type.DOCENT)
            )
            .setRoom(
                    new Room()
                            .setName("10")
                            .setType(Room.Type.AUDITORY)
            )
            .setGroup(
                    new Group()
                            .setName("509")
            );

    protected static final Lesson SECOND_LESSON = new Lesson()
            .setName("Physic")
            .setNumber(Lesson.Number.FIRST)
            .setTeacher(
                    new Teacher()
                            .setFullName("Professor name")
                            .setType(Teacher.Type.PROFESSOR)
            )
            .setRoom(
                    new Room()
                            .setName("11")
                            .setType(Room.Type.LABORATORY)
            )
            .setGroup(
                    new Group()
                            .setName("508")
            );

    protected static final Lesson THIRD_LESSON = new Lesson()
            .setName("Physic")
            .setNumber(Lesson.Number.THIRD)
            .setTeacher(
                    new Teacher()
                            .setFullName("Post Graduate Name")
                            .setType(Teacher.Type.POST_GRADUATE)
            )
            .setRoom(
                    new Room()
                            .setName("11")
                            .setType(Room.Type.LABORATORY)
            )
            .setGroup(
                    new Group()
                            .setName("508")
            );

    protected static final Lesson COMBINED = new Lesson()
            .setName("Physic")
            .setNumber(Lesson.Number.FIRST)
            .setTeacher(
                    new Teacher()
                            .setFullName("Post Graduate Name")
                            .setType(Teacher.Type.POST_GRADUATE)
            )
            .setRoom(
                    new Room()
                            .setName("12")
                            .setType(Room.Type.LABORATORY)
            )
            .setGroup(
                    new Group().setCombinedGroups(new Group[]{
                            new Group().setName("555"),
                            new Group().setName("556"),
                            new Group().setName("557")
                    })
            );

}
