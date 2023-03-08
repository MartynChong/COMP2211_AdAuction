package org.comp2211.model;

import java.sql.*;

// - View the number of clicks to see how many people have decided to click on the ad [M]
// - View the number of clicks to see how much the ad campaign costs if I am paying per click [M]
// - View the number of unique clicks so that I can see if the ad is attracting new users [M]
// - View the number of unique clicks to determine whether the ad is attractive for a wide audience or not, allowing us to make adjustments to it if need be. [M]
// SELECT strftime('%d-%m-%Y %H:%M:%f', click_date/1000.0, 'unixepoch') FROM clicks WHERE id = '8895519749317550080' - FOR DATE/TIME
public class ClickCalculator {
//    private String databaseFilePath = "src/main/java/org/comp2211/resources/testSQL/test.db";
    private String databaseFilePath =
      "src\\main\\java\\org\\comp2211\\resources\\testSQL\\test.db";
    private Connection conn;
    private ImpressionCalculator imprCalc = new ImpressionCalculator();

    public ClickCalculator() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // - Total Number of Clicks
    public int getTotalClicks() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet intResult = stmt.executeQuery("SELECT COUNT(*) FROM clicks ");
            while (intResult.next()) {
                return intResult.getInt("COUNT(*)");
            }
            intResult.close();
            stmt.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // - Total Number of Unique Clicks
    public int getUniqueClicks() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet intResult = stmt.executeQuery("SELECT COUNT(DISTINCT id) FROM clicks");
            while (intResult.next()) {
                return intResult.getInt("COUNT(DISTINCT id)");
            }
            intResult.close();
            stmt.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // - Total Cost of all clicks
    public float getTotalCost() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet intResult = stmt.executeQuery("SELECT SUM(cost) FROM clicks");
            while (intResult.next()) {
                float clickCost = intResult.getFloat("SUM(cost)");
                float imprCost = imprCalc.getImprCost();
                return clickCost + imprCost;
            }
            intResult.close();
            stmt.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // - Cost per Click AKA sum of costs / number of clicks
    public double getCPC() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet intResult = stmt.executeQuery("SELECT SUM(cost)/COUNT(*) FROM clicks");
            while (intResult.next()) {
                double totalCost = intResult.getDouble("SUM(cost)/COUNT(*)");
                return (double) (Math.round(totalCost * 100.0) / 100.0);
            }
            intResult.close();
            stmt.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public float getCTR() {
        float click = getTotalClicks();
        float impr = imprCalc.getImpr();
        return click / impr * 100;
    }

    public void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClickCalculator clickCalc = new ClickCalculator();
        System.out.println(clickCalc.getTotalClicks());
        System.out.println(clickCalc.getUniqueClicks());
        System.out.println(clickCalc.getTotalCost());
        System.out.println(clickCalc.getCPC());
        clickCalc.closeConn();
    }
}
