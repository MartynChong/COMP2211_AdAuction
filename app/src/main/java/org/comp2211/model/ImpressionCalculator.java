package org.comp2211.model;

import java.sql.*;

public class ImpressionCalculator {
    DatabaseManager dbManager;

    public ImpressionCalculator() {
        dbManager = new DatabaseManager();
    }

    public int getImpr() {
        try {
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            ResultSet uniquesResult = stmt.executeQuery("SELECT COUNT (id) AS \"Uniques\" FROM impression;");
            while (uniquesResult.next()) {
                int uniqueImpression = uniquesResult.getInt("Uniques");
                return uniqueImpression;
            }
            uniquesResult.close();
            stmt.close();
            conn.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Get cost per thousand impressions
     * @return cost per thousand impressions
     */
    public float getCPM() {
        try {
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            ResultSet uniquesResult = stmt.executeQuery("SELECT SUM (cost) AS \"CPM\" FROM impression;");
            float costs = 0;
            while (uniquesResult.next()) {
                costs = uniquesResult.getFloat("CPM");
            }
            float imprs = getImpr();

            uniquesResult.close();
            stmt.close();
            conn.close();
            return (costs / imprs) * 1000;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public float getImprCost() {
        try {
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            ResultSet intResult = stmt.executeQuery("SELECT SUM(cost) FROM impression;");
            while (intResult.next()) {
                float totalCost = intResult.getFloat("SUM(cost)");
                return totalCost;
            }
            intResult.close();
            stmt.close();
            conn.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
    }
}
