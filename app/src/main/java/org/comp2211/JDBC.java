package org.comp2211;

import java.sql.*;


public class JDBC {
    public static void main(String[] args) {

        try {
            String databaseFilePath = "src\\main\\java\\org\\comp2211\\resources\\testSQL\\test.db";

            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from test_table;");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
