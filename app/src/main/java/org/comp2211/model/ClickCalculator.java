package org.comp2211.model;

import java.sql.*;

// - View the number of clicks to see how many people have decided to click on the ad [M]
// - View the number of clicks to see how much the ad campaign costs if I am paying per click [M]
// - View the number of unique clicks so that I can see if the ad is attracting new users [M]
// - View the number of unique clicks to determine whether the ad is attractive for a wide audience or not, allowing us to make adjustments to it if need be. [M]
// SELECT strftime('%d-%m-%Y %H:%M:%f', click_date/1000.0, 'unixepoch') FROM clicks WHERE id = '8895519749317550080' - FOR DATE/TIME
public class ClickCalculator {
//    private String databaseFilePath = "src/main/java/org/comp2211/resources/testSQL/test.db";
    private DatabaseManager dbManager;
    private ImpressionCalculator imprCalc = new ImpressionCalculator();

    public ClickCalculator() {
        dbManager = new DatabaseManager();
    }

    // - Total Number of Clicks
    public int getTotalClicks() {
        try {
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            ResultSet intResult = stmt.executeQuery("SELECT COUNT(*) FROM clicks ");
            int totalClick = 0;
            while (intResult.next()) {
                totalClick = intResult.getInt("COUNT(*)");
                break;
            }
            intResult.close();
            stmt.close();
            conn.close();
            return totalClick;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // - Total Number of Unique Clicks
    public int getUniqueClicks() {
        try {
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            ResultSet intResult = stmt.executeQuery("SELECT COUNT(DISTINCT id) FROM clicks");
            int uniques = 0;
            while (intResult.next()) {
                uniques =  intResult.getInt("COUNT(DISTINCT id)");
                break;
            }
            intResult.close();
            stmt.close();
            conn.close();
            return uniques;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // - Total Cost of all clicks
    public float getTotalCost() {
        try {
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            ResultSet intResult = stmt.executeQuery("SELECT SUM(cost) FROM clicks");
            float clickCost = 0;
            float imprCost = 0;
            while (intResult.next()) {
                clickCost = intResult.getFloat("SUM(cost)");
                imprCost = imprCalc.getImprCost();
            }
            intResult.close();
            stmt.close();
            conn.close();
            return clickCost + imprCost;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // - Cost per Click AKA sum of costs / number of clicks
    public double getCPC() {
        try {
            Connection conn = dbManager.getConn();
            Statement stmt = conn.createStatement();
            ResultSet intResult = stmt.executeQuery("SELECT SUM(cost)/COUNT(*) FROM clicks");
            double totalCost = 0;
            while (intResult.next()) {
                totalCost = intResult.getDouble("SUM(cost)/COUNT(*)");
            }
            intResult.close();
            stmt.close();
            conn.close();
            return (double) (Math.round(totalCost * 100.0) / 100.0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Calculate click through rate
     * @return click through rate
     */
    public float getCTR() {
        float click = getTotalClicks();
        float impr = imprCalc.getImpr();
        return click / impr * 100;
    }

    public static void main(String[] args) {
    }
}
