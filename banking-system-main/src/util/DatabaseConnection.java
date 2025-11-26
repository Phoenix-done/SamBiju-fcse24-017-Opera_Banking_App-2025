package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:database/banking.db";
    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Database connected");
                initializeDatabase();
            } catch (ClassNotFoundException e) {
                throw new SQLException("SQLite JDBC driver not found", e);
            }
        }
        return connection;
    }

    private static void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Customer (" +
                    "customer_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "firstname VARCHAR(100) NOT NULL, " +
                    "surname VARCHAR(100) NOT NULL, " +
                    "address TEXT, " +
                    "phone VARCHAR(20), " +
                    "email VARCHAR(100), " +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            stmt.execute("CREATE TABLE IF NOT EXISTS Account (" +
                    "account_number VARCHAR(20) PRIMARY KEY, " +
                    "customer_id INTEGER NOT NULL, " +
                    "account_type VARCHAR(50) NOT NULL, " +
                    "balance DECIMAL(15, 2) DEFAULT 0.00, " +
                    "branch VARCHAR(100), " +
                    "interest_rate DECIMAL(5, 4), " +
                    "employer_name VARCHAR(200), " +
                    "employer_address TEXT, " +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (customer_id) REFERENCES Customer(customer_id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS Transaction (" +
                    "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "account_number VARCHAR(20) NOT NULL, " +
                    "transaction_type VARCHAR(20) NOT NULL, " +
                    "amount DECIMAL(15, 2) NOT NULL, " +
                    "transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "description TEXT, " +
                    "FOREIGN KEY (account_number) REFERENCES Account(account_number))");

            System.out.println("Database tables initialized");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }
}