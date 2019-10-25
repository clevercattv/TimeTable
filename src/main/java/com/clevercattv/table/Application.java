package com.clevercattv.table;

import com.clevercattv.table.models.Room;
import com.clevercattv.table.models.Group;
import com.clevercattv.table.models.Teacher;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;
import com.clevercattv.table.services.TimeTableService;
import com.clevercattv.table.models.Lesson;
import com.clevercattv.table.models.TimeTable;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {
        TimeTableService service = new TimeTableService(new TimeTable(LocalDate.now()));
        service.addLesson(DayOfWeek.MONDAY,
                Lesson.build(
                        Teacher.build("Teacher First", Teacher.Type.DOCENT),
                        Lesson.Number.FIRST,
                        Group.build("521"),
                        "Math",
                        Room.build("2", Room.Type.AUDITORY)
                )
        );
        service.addLesson(DayOfWeek.MONDAY,
                Lesson.build(
                        Teacher.build("Teacher Second", Teacher.Type.DOCENT),
                        Lesson.Number.FIRST,
                        Group.build(Group.build("522"),Group.build("523")),
                        "Math",
                        Room.build("3", Room.Type.AUDITORY)
                )
        );
        TimeTableJsonSerializer.serialize(service.getTimeTable(),"Data.json");
        TimeTableJsonSerializer.deserialize("Data.json");
        //skype Любов Мунтян  -- english

    }

}