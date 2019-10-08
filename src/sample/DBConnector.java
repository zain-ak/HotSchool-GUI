package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static Connection con;

    public static Connection getConnection() {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/HotSummer?autoReconnect=true";
                String username = "root";
                String password = "zain";
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return con;
    }


}
