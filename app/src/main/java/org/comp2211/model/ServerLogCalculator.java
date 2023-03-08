package org.comp2211.model;

import java.sql.*;

public class ServerLogCalculator {
    private String databaseFilePath = "src\\main\\java\\org\\comp2211\\resources\\testSQL\\test.db";
    private Connection conn;
    private ClickCalculator clickCalc = new ClickCalculator();

    public ServerLogCalculator() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getBounce() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet bounceResult = stmt.executeQuery("SELECT COUNT (*) AS bounce FROM server_log WHERE pages_viewed =  1 OR entry_date = exit_date;");
            while (bounceResult.next()) {
                int bounce = bounceResult.getInt("bounce");
                return bounce;
            }
            bounceResult.close();
            stmt.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Get number of conversion
     * @return number of conversion
     */
    public int getConver() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet ConverResult = stmt.executeQuery("SELECT COUNT (*) AS Conversion FROM server_log WHERE conversion = 1;");
            while (ConverResult.next()) {
                int conver = ConverResult.getInt("Conversion");
                return conver;
            }
            ConverResult.close();
            stmt.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public float getBounceRate() {
        try {
            Statement stmt = conn.createStatement();
            float bounce = this.getBounce();
            ResultSet bounceRateResult = stmt.executeQuery("SELECT COUNT (*) AS total_clicks FROM server_log;");
            while (bounceRateResult.next()) {
                float total_clicks = bounceRateResult.getFloat("total_clicks");
                return bounce / total_clicks;
            }

            bounceRateResult.close();
            stmt.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Get cost per acquisition
     * @return cost per acquisition
     */
    public double getCPA() {
        double totalCost = clickCalc.getTotalCost();
        double acquisition = this.getConver();
        return totalCost / acquisition;
    }

    public void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerLogCalculator calc = new ServerLogCalculator();
        System.out.println(String.format("%.02f" ,calc.getCPA()));
    }
}
