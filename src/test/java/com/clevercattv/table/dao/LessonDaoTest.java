package com.clevercattv.table.dao;

import com.clevercattv.table.TestData;
import com.clevercattv.table.database.TableService;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;
import com.clevercattv.table.service.TimeTableService;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

@RunWith(DataProviderRunner.class)
public class LessonDaoTest extends TestData {

    private static final LessonDao DAO = LessonDao.getInstance();
    private static final GroupDao GROUP_DAO = GroupDao.getInstance();
    private static final RoomDao ROOM_DAO = RoomDao.getInstance();
    private static final TeacherDao TEACHER_DAO = TeacherDao.getInstance();

    @BeforeClass
    public static void beforeClass() throws IOException {
        try {
            TableService.dropTables();
            TableService.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TIME_TABLE_SERVICE = new TimeTableService(TimeTableJsonSerializer.deserialize(TEST_JSON_URL));
        FIRST_LESSON = TIME_TABLE_SERVICE.getLessons().get(0);
        SECOND_LESSON = TIME_TABLE_SERVICE.getLessons().get(1);
        THIRD_LESSON = TIME_TABLE_SERVICE.getLessons().get(2);
    }


    @DataProvider
    public static Object[][] lessonsProvider() {
        return new Object[][]{ {FIRST_LESSON}, {SECOND_LESSON}, {THIRD_LESSON} };
    }

    @Test
    @UseDataProvider("lessonsProvider")
    public void findById(Lesson testLesson) throws SQLException {
        Lesson lesson = DAO.findById(testLesson.getId())
                .orElseThrow(() -> new SQLException("DB didn't find anything!"));
        Assert.assertEquals(testLesson,lesson);
    }

    @Test
    public void findAll() throws SQLException {
        Assert.assertTrue(Arrays.deepEquals(
                new Lesson[]{SECOND_LESSON,FIRST_LESSON,THIRD_LESSON},
                DAO.findAll().toArray()));
    }

    @Test(expected = SQLException.class)
    public void saveError() throws SQLException {
        DAO.save(FIRST_LESSON);
    }

    @Test
    public void update() throws SQLException {
        FIRST_LESSON.setName("new name");
        DAO.update(FIRST_LESSON);
        Lesson lesson = DAO.findById(FIRST_LESSON.getId()).orElseThrow(NullPointerException::new);
        Assert.assertEquals("new name",lesson.getName());
    }

}