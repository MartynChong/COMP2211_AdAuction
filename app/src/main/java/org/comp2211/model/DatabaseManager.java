package org.comp2211.model;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection conn;

    public Connection getConn() throws SQLException {
        return conn = DriverManager.getConnection("jdbc:sqlite:testdb.db");
    }

}
