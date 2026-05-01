package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/second_hand_marketplace";
    private static final String USER = "root";
    private static final String PASSWORD = "kalim";

   
public static Connection getConnection() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

        if (con != null) {
            System.out.println(" Database connected successfully");
        }

        return con;

    } catch (Exception e) {
        System.out.println("Database connection failed");
        e.printStackTrace();
        return null;
    }
}

}