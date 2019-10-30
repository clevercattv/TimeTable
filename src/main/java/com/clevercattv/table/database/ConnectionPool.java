package com.clevercattv.table.database;

import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;
import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.sql.ConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {

    private static ConnectionPoolDataSource poolDataSource;

    static {
        PGConnectionPoolDataSource source = new PGConnectionPoolDataSource();
        source.setServerName("localhost");
        source.setDatabaseName("SoftServeTimeTable");
        source.setUser("postgres");
        source.setPassword("root");
        source.setApplicationName("CleverTimeTable");
        poolDataSource = source;
    }

    public static Connection getConnection() {
        try {
            return poolDataSource.getPooledConnection()
                    .getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

/*
    private String url = "jdbc:postgresql://localhost:5432/SoftServeTimeTable";
    private String user = "postgres";
    private String password = "root";
*/

    public static void dropCreateDB() {
        try {
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            stmt.execute("DROP SCHEMA public CASCADE ");
            stmt.execute("CREATE SCHEMA public");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createTables();
    }

    public static void createTables() {
        try(Connection connection = getConnection();
            Statement stmt = connection.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS Groups" +
                    "(id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(" + Group.MAX_NAME_LENGTH + ") NOT NULL UNIQUE," +
                    "combined BOOLEAN NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS Rooms" +
                    "(id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(" + Room.MAX_NAME_LENGTH + ") NOT NULL UNIQUE," +
                    "type INT NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS Teachers" +
                    "(id SERIAL PRIMARY KEY, " +
                    "fullName VARCHAR(" + Teacher.MAX_NAME_LENGTH + ") NOT NULL UNIQUE," +
                    "type INT NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS Lessons" +
                    "(id SERIAL PRIMARY KEY, name VARCHAR(32) NOT NULL UNIQUE," +
                    "teacher INT NOT NULL REFERENCES teachers(id), " +
                    "number INT NOT NULL , " +
                    "groups INT NOT NULL REFERENCES groups(id), " +
                    "room INT NOT NULL REFERENCES rooms(id), " +
                    "day INT NOT NULL)");

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}