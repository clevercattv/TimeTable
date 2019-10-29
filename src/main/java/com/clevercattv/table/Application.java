package com.clevercattv.table;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.jdbc.DataBase;
import com.clevercattv.table.models.Group;

public class Application {

    public static void main(String[] args) {
        DataBase dataBase = new DataBase();
        dataBase.dropCreateDB();
//        GroupDao groupDao = new GroupDao();
//        groupDao.save(Group.build("Test"));
//        groupDao.saveAll(Group.build("Test1"),Group.build("Test2"),Group.build("Test3"));
//        groupDao.update(Group.build(1,"Test"));
//        System.out.println(groupDao.get(1).get());
//        groupDao.delete(Group.build(1,"Test"));
        //skype Любов Мунтян  -- english

    }

}