package com.clevercattv.table.dao;

import com.clevercattv.table.model.Teacher;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

public class TeacherDaoTest {

    private TeacherDao dao = new TeacherDao();
    private Teacher teacher = new Teacher().setFullName("test teacher name").setType(Teacher.Type.DOCENT);

    @Test
    public void get() throws SQLException {
       dao.findById(1);
    }

    @Test
    public void getAll() throws SQLException {
        dao.findAll();
    }

    @Test
    public void save() throws SQLException {
        dao.save(teacher);
    }

    @Test
    public void saveAll() throws SQLException {
        dao.saveAll(Arrays.asList(teacher, teacher));
    }

    @Test
    public void update() throws SQLException {
//        dao.update();
    }
}