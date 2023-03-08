package org.comp2211.model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.sql.*;
import java.io.*;
import java.util.List;

public class ServerLogImporter {
    private String clickFilePath;
    private DatabaseManager dbManager;

    /**
     * Initialise the connection to db.
     */
    public ServerLogImporter() {
        dbManager = new DatabaseManager();
    }

    /**
     * Drop the existing server click table and creates a new one.
     */
    public void initialise() {
        try {
            Connection conn = dbManager.getConn();
            Statement statement = conn.createStatement();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "server_log", null);
            if (tables.next()) {
                tables.close();
                boolean dropExist = statement.execute("DROP TABLE server_log;"); // drop old table if there is any
                if (dropExist) {
                    System.out.println("No table existed to drop");
                } else {
                    System.out.println("Existing table deleted");
                }
            }

            statement.execute("CREATE TABLE server_log (entry_date timestamp, id varchar(255), exit_date timestamp null, pages_viewed int, conversion tinyint);");
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
        String insertStmt = "INSERT INTO server_log (entry_date, id, exit_date, pages_viewed, conversion) VALUES (?,?,?,?,?)";
        try {
            // read a csv file
            FileReader serverLog = new FileReader(clickFilePath);
            BufferedReader bufferedLog = new BufferedReader(serverLog);
            bufferedLog.readLine(); // skip the header line
            CSVReader reader = new CSVReader(bufferedLog);
            List<String[]> logs = reader.readAll();
            serverLog.close();
            bufferedLog.close();
            reader.close();
            Connection conn = dbManager.getConn();
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(insertStmt);
            int i = 1000000;
            for (String[] entry : logs) {
                Timestamp entry_date = Timestamp.valueOf(entry[0]);
                String uid = entry[1];
                Timestamp exit_date;
                if (entry[2].equals("n/a")) {
                    exit_date = null;
                } else {
                    exit_date = Timestamp.valueOf(entry[2]);
                }
                int pagesViewed = Integer.parseInt(entry[3]);
                // Conversion. 1 for Yes, 0 for No, 9 for not applicable.
                byte conversion = 9;
                if (entry[4].equals("Yes")) {
                    conversion = 1;
                } else if (entry[4].equals("No")) {
                    conversion = 0;
                }

                stmt.setTimestamp(1, entry_date);
                stmt.setString(2, uid);
                stmt.setTimestamp(3, exit_date);
                stmt.setInt(4, pagesViewed);
                stmt.setByte(5, conversion);
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
            System.out.println("Server log file not found.");
        } catch (IOException | CsvException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check if the provided csv file is a valid impression file.
     * @return True if the file is valid.
     */
    public boolean isValid(String path) {
        try {
            FileReader serverLog = new FileReader(path);
            BufferedReader bufferedLog = new BufferedReader(serverLog);
            String header = bufferedLog.readLine();
            bufferedLog.close();
            serverLog.close();
            String[] headerList = header.split(",");
            if (headerList[0].equals("Entry Date") && headerList[1].equals("ID") && headerList[2].equals("Exit Date")
                    && headerList[3].equals("Pages Viewed") && headerList[4].equals("Conversion")) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        ServerLogImporter importer = new ServerLogImporter();
        importer.initialise();
        importer.setFilePath(
                "D:\\Downloads\\2_week_campaign_1\\2_week_campaign_2\\server_log.csv");
        importer.insertRecord();

    }
}
