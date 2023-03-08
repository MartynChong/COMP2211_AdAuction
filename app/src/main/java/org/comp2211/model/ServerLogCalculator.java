package org.comp2211.model;

import java.sql.*;

public class ServerLogCalculator {
    DatabaseManager dbManager;
    public ServerLogCalculator() {
        dbManager = new DatabaseManager();
    }
    public int getBounce() {
        try {
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            ResultSet bounceResult = stmt.executeQuery("SELECT COUNT (*) AS bounce FROM server_log WHERE pages_viewed =  1 OR entry_date = exit_date;");
            int bounce = 0;
            while (bounceResult.next()) {
                bounce = bounceResult.getInt("bounce");
            }
            bounceResult.close();
            stmt.close();
            conn.close();
            return bounce;
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
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            ResultSet ConverResult = stmt.executeQuery("SELECT COUNT (*) AS Conversion FROM server_log WHERE conversion = 1;");
            int conver = 0;
            while (ConverResult.next()) {
                 conver = ConverResult.getInt("Conversion");
            }
            ConverResult.close();
            stmt.close();
            conn.close();
            return conver;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public float getBounceRate() {
        try {
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            float bounce = this.getBounce();
            ResultSet bounceRateResult = stmt.executeQuery("SELECT COUNT (*) AS total_clicks FROM server_log;");
            float total_clicks = 0;
            while (bounceRateResult.next()) {
                total_clicks = bounceRateResult.getFloat("total_clicks");
            }

            bounceRateResult.close();
            stmt.close();
            conn.close();
            return bounce / total_clicks * 100;
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
        try {
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            ResultSet cpaResult = stmt.executeQuery("SELECT SUM (impression.cost) AS impr_cost, SUM (clicks.cost) AS click_cost FROM impression INNER JOIN clicks on clicks.id = impression.id AND click_date = impress_date INNER JOIN ( SELECT entry_date, id AS server_log_id FROM server_log WHERE conversion = 1 ) ON entry_date = click_date AND server_log_id = clicks.id;");
            double imprCost = 0;
            double clickCost = 0;
            while (cpaResult.next()) {
                imprCost = cpaResult.getDouble("impr_cost");
                clickCost = cpaResult.getDouble("click_cost");
            }
            cpaResult.close();
            stmt.close();
            conn.close();
            return (double) (Math.round((imprCost + clickCost) * 100.0) / 100.0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static void main(String[] args) {
    }
}
