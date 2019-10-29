package com.clevercattv.table;

import com.clevercattv.table.db.ConnectionPool;

public class Application {

    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();
        connectionPool.dropCreateDB();
//        GroupDao groupDao = new GroupDao();
//        groupDao.save(Group.build("Test"));
//        groupDao.saveAll(Group.build("Test1"),Group.build("Test2"),Group.build("Test3"));
//        groupDao.update(Group.build(1,"Test"));
//        System.out.println(groupDao.get(1).get());
//        groupDao.delete(Group.build(1,"Test"));
        //skype Любов Мунтян  -- english

    }

}