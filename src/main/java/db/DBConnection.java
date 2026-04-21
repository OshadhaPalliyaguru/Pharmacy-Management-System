package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection; // Removed 'static'

    // ⚠️ CHECK YOUR PORT: MySQL default is 3306. You have 3307.
    // If you are using XAMPP, check which port MySQL is actually running on.
    private static final String URL = "jdbc:mysql://localhost:3307/pharmacy_db";
    private static final String USER = "root";
    private static final String PASSWORD = "3690";

    private DBConnection() throws SQLException {
        try {
            // Load the driver explicitly (sometimes needed in older Java versions)
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to connect to Database");
        }
    }

    public static DBConnection getInstance() throws SQLException {
        // If instance is null OR the internal connection is closed, create a new one
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Changed from 'static' to public so 'getInstance().getConnection()' works
    public Connection getConnection() {
        return connection;
    }
}