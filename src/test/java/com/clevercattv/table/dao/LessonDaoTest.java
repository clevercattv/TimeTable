package com.clevercattv.table.dao;

import com.clevercattv.table.MainTest;
import com.clevercattv.table.database.TableService;
import com.clevercattv.table.model.Lesson;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(DataProviderRunner.class)
public class LessonDaoTest extends MainTest {

    private static final LessonDao DAO = LessonDao.getInstance();
    private static final GroupDao GROUP_DAO = GroupDao.getInstance();
    private static final RoomDao ROOM_DAO = new RoomDao();
    private static final TeacherDao TEACHER_DAO = new TeacherDao();

    @Before
    public void before() throws SQLException {
        TableService.dropTables();
        TableService.createTables();
        GROUP_DAO.save(FIRST_GROUP);
        GROUP_DAO.save(SECOND_GROUP);
        ROOM_DAO.save(FIRST_ROOM);
        ROOM_DAO.save(SECOND_ROOM);
        ROOM_DAO.save(THIRD_ROOM);
        TEACHER_DAO.save(FIRST_TEACHER);
        TEACHER_DAO.save(SECOND_TEACHER);
        TEACHER_DAO.save(THIRD_TEACHER);
        DAO.save(FIRST_LESSON);
        DAO.save(SECOND_LESSON);
        DAO.save(THIRD_LESSON);
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
        Assert.assertEquals(Arrays.asList(FIRST_LESSON,SECOND_LESSON,THIRD_LESSON),DAO.findAll());
    }

    @Test(expected = SQLException.class)
    public void saveError() throws SQLException {
        DAO.save(FIRST_LESSON);
    }

    @Test
    public void saveAll() throws SQLException {
        DAO.delete(FIRST_LESSON);
        DAO.delete(SECOND_LESSON);
        DAO.delete(THIRD_LESSON);
        Collection<Lesson> lessons = DAO.saveAll(Arrays.asList(FIRST_LESSON, SECOND_LESSON, THIRD_LESSON));
        Assert.assertEquals(3,lessons.size());
    }

    @Test
    public void update() throws SQLException {
        FIRST_LESSON.setName("new name");
        DAO.update(FIRST_LESSON);
        Assert.assertEquals(DAO.findById(FIRST_LESSON.getId())
                .get()
                .getName(),"new name");
    }

}