package com.attendance;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            // ✅ Load MySQL JDBC Driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_attendance?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "srushti",
                    "1234"
            );
            return con;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}