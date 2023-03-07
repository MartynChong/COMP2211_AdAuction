package org.comp2211.model;

import java.sql.*;

public class ImpressionCalculator {
    private String databaseFilePath = "src\\main\\java\\org\\comp2211\\resources\\testSQL\\test.db";
    private Connection conn;

    public ImpressionCalculator() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getUniques() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet uniquesResult = stmt.executeQuery("SELECT COUNT (DISTINCT id) AS \"Uniques\" FROM impression;");
            while (uniquesResult.next()) {
                System.out.println();
                int uniqueImpression = uniquesResult.getInt("Uniques");
                return uniqueImpression;
            }
            uniquesResult.close();
            stmt.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getImpr() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet uniquesResult = stmt.executeQuery("SELECT COUNT (id) AS \"Uniques\" FROM impression;");
            while (uniquesResult.next()) {
                System.out.println();
                int uniqueImpression = uniquesResult.getInt("Uniques");
                return uniqueImpression;
            }
            uniquesResult.close();
            stmt.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public float getCPM() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet uniquesResult = stmt.executeQuery("SELECT SUM (cost) AS \"CPM\" FROM impression;");
            float costs = 0;
            while (uniquesResult.next()) {
                System.out.println();
                costs = uniquesResult.getFloat("CPM");
            }
            float imprs = (float) getImpr();

            uniquesResult.close();
            stmt.close();
            return costs / imprs * 1000;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ImpressionCalculator imprCalc = new ImpressionCalculator();
        System.out.println(imprCalc.getCPM());
        imprCalc.closeConn();
    }
}
