package controller;

import model.Order;
import model.DBConnection;

import java.sql.*;

public class OrderController {

    // PLACE ORDER
    public int placeOrder(Order order) {
        String query = "INSERT INTO Orders (buyer_id, product_id, total_amount, status, order_date) VALUES (?, ?, ?, ?, NOW())";

        try (Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getBuyerId());
            ps.setInt(2, order.getProductId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getStatus());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                System.out.println("Order placed with ID: " + orderId);
                return orderId;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // VIEW ORDERS
    public void viewOrders() {
        String query = "SELECT * FROM Orders";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("\nOrder ID: " + rs.getInt("order_id"));
                System.out.println("Buyer ID: " + rs.getInt("buyer_id"));
                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Status: " + rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CANCEL ORDER
    public void cancelOrder(int orderId) {
        String query = "UPDATE Orders SET status='Cancelled' WHERE order_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, orderId);
            ps.executeUpdate();

            System.out.println("Order cancelled.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}