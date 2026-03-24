package controller;

import model.DBConnection;
import java.sql.*;

public class DeliveryController {

    public void createDelivery(int orderId) {

        try (Connection con = DBConnection.getConnection()) {

            // ✅ Check if order exists
            String checkQuery = "SELECT * FROM Orders WHERE order_id=?";
            PreparedStatement check = con.prepareStatement(checkQuery);
            check.setInt(1, orderId);
            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                System.out.println("Order does not exist!");
                return;
            }

            // ✅ Insert delivery
            String query = "INSERT INTO Delivery (order_id, delivery_status) VALUES (?, 'Pending')";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, orderId);
            ps.executeUpdate();

            System.out.println("Delivery created.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDeliveryStatus(int orderId, String status) {
        String query = "UPDATE Delivery SET delivery_status=? WHERE order_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();

            System.out.println("Delivery updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewDelivery() {
        String query = "SELECT * FROM Delivery";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("\nOrder ID: " + rs.getInt("order_id"));
                System.out.println("Status: " + rs.getString("delivery_status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}