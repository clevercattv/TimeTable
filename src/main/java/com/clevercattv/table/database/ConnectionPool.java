package com.clevercattv.table.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.sql.ConnectionPoolDataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static final String PROPERTIES = "application.properties";
    private static ConnectionPoolDataSource poolDataSource;

    private ConnectionPool() {
    }

    static {
        try {
            PGConnectionPoolDataSource source = new PGConnectionPoolDataSource();
            Properties properties = new Properties();
            properties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(PROPERTIES));
            source.setServerName(properties.getProperty("jdbc.server.name"));
            source.setDatabaseName(properties.getProperty("jdbc.database.name"));
            source.setUser(properties.getProperty("jdbc.database.username"));
            source.setPassword(properties.getProperty("jdbc.database.password"));
            source.setApplicationName(properties.getProperty("application.name"));
            poolDataSource = source;
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = poolDataSource.getPooledConnection().getConnection();
        if (Objects.isNull(connection)) throw new NullPointerException();
        return connection;
    }

}