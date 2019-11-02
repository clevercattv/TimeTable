package com.clevercattv.table.dao;

import com.clevercattv.table.model.Group;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

public class GroupDaoTest {

    private GroupDao dao = new GroupDao();
    private Group room = new Group().setName("245");

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
        dao.save(room);
    }

    @Test
    public void saveAll() throws SQLException {
        dao.saveAll(Arrays.asList(room, room));
    }

    @Test
    public void update() throws SQLException {
//        dao.update();
    }

}