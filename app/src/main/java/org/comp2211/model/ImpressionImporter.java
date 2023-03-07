package org.comp2211.model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.sql.*;
import java.io.*;
import java.util.List;

public class ImpressionImporter {
    private String imprFilePath;
    private String databaseFilePath = "src\\main\\java\\org\\comp2211\\resources\\testSQL\\test.db";

    /**
     * Initialise the connection to db.
     */
    public ImpressionImporter() {}

    /**
     * Drop the existing impression table and creates a new one.
     */
    public void initialise() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            Statement statement = conn.createStatement();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "impression", null);
            if (tables.next()) {
                tables.close();
                boolean dropExist = statement.execute("DROP TABLE impression;"); // drop old table if there is any
                if (dropExist) {
                    System.out.println("No table existed to drop");
                } else {
                    System.out.println("Existing table deleted");
                }
            }

            statement.execute("CREATE TABLE impression (impress_date timestamp, id varchar(255), gender tinyint, age varchar(255), income varchar(255), context varchar(255), cost varchar(255));");
            statement.close();
            tables.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setFilePath(String path) {
        this.imprFilePath = path;
    }

    public boolean insertRecord() {
        String insertStmt = "INSERT INTO impression (impress_date, id, gender, age, income, context, cost) VALUES (?,?,?,?,?,?,?)";
        try {
            // read a csv file
            FileReader impressionLog = new FileReader(imprFilePath);
            BufferedReader bufferedLog = new BufferedReader(impressionLog);
            bufferedLog.readLine(); // skip the header line
            CSVReader reader = new CSVReader(bufferedLog);
            List<String[]> logs = reader.readAll();
            impressionLog.close();
            bufferedLog.close();
            reader.close();
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(insertStmt);
            int i = 1000000;
            for (String[] entry : logs) {
                Timestamp impress_date = Timestamp.valueOf(entry[0]);
                String uid = entry[1];
                // Use ISO/IEC 5218 for gender representation.
                byte gender;
                if (entry[2].equals("Male")) {
                    gender = 1;
                } else if (entry[2].equals("Female")) {
                    gender = 2;
                } else {
                    gender = 0;
                }
                String age = entry[3];
                // TODO: 07/03/2023 Filtering input date
                String income = entry[4];
                String context = entry[5];
                String cost = entry[6];

                stmt.setTimestamp(1, impress_date);
                stmt.setString(2, uid);
                stmt.setByte(3, gender);
                stmt.setString(4, age);
                stmt.setString(5, income);
                stmt.setString(6, context);
                stmt.setString(7, cost);
                stmt.addBatch();
                stmt.clearParameters();
                i--;
                if (i == 0) {
                    stmt.executeBatch();
                    i = 5000000;
                }
            }

            stmt.executeBatch();
            conn.commit();
            stmt.close();
            conn.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Impression file not found.");
        } catch (IOException | CsvException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        ImpressionImporter impressionImporter = new ImpressionImporter();
        impressionImporter.initialise();
    impressionImporter.setFilePath(
        "D:\\Programming\\CS uni work\\Year 2\\Software Engineering Group\\COMP2211_AdAuction\\app\\src\\main\\java\\org\\comp2211\\resources\\csvFiles\\impression_log.csv");
        impressionImporter.insertRecord();
    }
}
