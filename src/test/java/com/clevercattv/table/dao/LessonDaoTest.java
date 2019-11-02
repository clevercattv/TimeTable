package com.clevercattv.table.dao;

import com.clevercattv.table.model.Group;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LessonDaoTest {

    private LessonDao dao = new LessonDao();

    @Test
    public void get() throws SQLException {
        dao.get(1);
    }

    @Test
    public void getAll() throws SQLException {
        dao.getAll();
    }

    @Test
    public void save() throws SQLException {

    }

    @Test
    public void saveAll() throws SQLException {

    }

    @Test
    public void update() throws SQLException {
//        dao.update();
    }

}