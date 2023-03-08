package org.comp2211.model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.sql.*;
import java.io.*;
import java.util.List;

public class ClickImporter {
    private String clickFilePath;
    private String databaseFilePath = "src\\main\\java\\org\\comp2211\\resources\\testSQL\\test.db";

    /**
     * Initialise the connection to db.
     */
    public ClickImporter() {}

    /**
     * Drop the existing click table and creates a new one.
     */
    public void initialise() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            Statement statement = conn.createStatement();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "clicks", null);
            if (tables.next()) {
                tables.close();
                boolean dropExist = statement.execute("DROP TABLE clicks;"); // drop old table if there is any
                if (dropExist) {
                    System.out.println("No table existed to drop");
                } else {
                    System.out.println("Existing table deleted");
                }
            }

            statement.execute("CREATE TABLE clicks (click_date timestamp, id varchar(255), cost varchar(255));");
            statement.close();
            tables.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setFilePath(String path) {
        this.clickFilePath = path;
    }

    public boolean insertRecord() {
        String insertStmt = "INSERT INTO clicks (click_date, id, cost) VALUES (?,?,?)";
        try {
            // read a csv file
            FileReader clickLog = new FileReader(clickFilePath);
            BufferedReader bufferedLog = new BufferedReader(clickLog);
            bufferedLog.readLine(); // skip the header line
            CSVReader reader = new CSVReader(bufferedLog);
            List<String[]> logs = reader.readAll();
            clickLog.close();
            bufferedLog.close();
            reader.close();
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(insertStmt);
            int i = 1000000;
            for (String[] entry : logs) {
                Timestamp impress_date = Timestamp.valueOf(entry[0]);
                String uid = entry[1];
                String cost = entry[2];

                stmt.setTimestamp(1, impress_date);
                stmt.setString(2, uid);
                stmt.setString(3, cost);
                stmt.addBatch();
                stmt.clearParameters();
                i--;
                if (i == 0) {
                    stmt.executeBatch();
                    i = 1000000;
                }
            }
            stmt.executeBatch();
            conn.commit();
            stmt.close();
            conn.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Click file not found.");
        } catch (IOException | CsvException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check if the provided csv file is a valid click file.
     * @return True if the file is valid.
     */
    public boolean isValid(String path) {
        try {
            FileReader clickLog = new FileReader(path);
            BufferedReader bufferedLog = new BufferedReader(clickLog);
            String header = bufferedLog.readLine();
            bufferedLog.close();
            clickLog.close();
            String[] headerList = header.split(",");
            if (headerList[0].equals("Date") && headerList[1].equals("ID") && headerList[2].equals("Click Cost")) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        ClickImporter clickImporter = new ClickImporter();
        clickImporter.initialise();
    clickImporter.setFilePath(
        "COMP2211_AdAuction/app/src/main/java/org/comp2211/resources/csvFiles/click_log.csv");
        clickImporter.insertRecord();
    }
}

//SELECT strftime('%d-%m-%Y %H:%M:%f', click_date/1000.0, 'unixepoch') FROM clicks
