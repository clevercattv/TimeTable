package com.clevercattv.table;

import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;

public class MainTest {

    protected static final Lesson FIRST_LESSON = Lesson.build(
            Teacher.build("Docent name",Teacher.Type.DOCENT),
            Lesson.Number.FIRST,
            Group.build("509"),
            "Math",
            Room.build("10", Room.Type.AUDITORY)
    );

    protected static final Lesson SECOND_LESSON = Lesson.build(
            Teacher.build("Professor name",Teacher.Type.PROFESSOR),
            Lesson.Number.FIRST,
            Group.build("508"),
            "Physic",
            Room.build("11", Room.Type.LABORATORY)
    );

    protected static final Lesson THIRD_LESSON = Lesson.build(
            Teacher.build("Post Graduate Name",Teacher.Type.POST_GRADUATE),
            Lesson.Number.THIRD,
            Group.build("508"),
            "Physic",
            Room.build("11", Room.Type.LABORATORY)
    );

    protected static final Lesson COMBINED = Lesson.build(
            Teacher.build("Post Graduate Name",Teacher.Type.POST_GRADUATE),
            Lesson.Number.FIRST,
            Group.build(new Group[]{
                    Group.build("555"),
                    Group.build("556"),
                    Group.build("557")
            }),
            "Physic",
            Room.build("12", Room.Type.LABORATORY)
    );

/*    // Complex exception
    @Test(expected = BusyException.class)
    public void testBusyException() {
        TIME_TABLE_SERVICE.addLesson(DayOfWeek.MONDAY,
                Lesson.build(
                        Teacher.build("Docent name",Teacher.Type.DOCENT),
                        Lesson.Number.FIRST,
                        Group.build("509"),
                        "Math",
                        Room.build("10", Room.Type.AUDITORY)
                )
        );
    }*/

}
