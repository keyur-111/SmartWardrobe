package com.smartwardrobe.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public final class DBConnection {
    private static final String URL = setting(
            "SMARTWARDROBE_DB_URL",
            "smartwardrobe.db.url",
            "jdbc:mysql://localhost:3306/smart_wardrobe?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true");
    private static final String USER = setting(
            "SMARTWARDROBE_DB_USER",
            "smartwardrobe.db.user",
            "root");
    private static final String PASSWORD = setting(
            "SMARTWARDROBE_DB_PASSWORD",
            "smartwardrobe.db.password",
            "");

    private DBConnection() {
    }

    private static String setting(
            String environmentVariable,
            String systemProperty,
            String defaultValue) {
        String value = System.getProperty(systemProperty);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(environmentVariable);
        }
        return value == null ? defaultValue : value;
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }

    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
