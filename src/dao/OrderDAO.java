package dao;

import model.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int createOrder(Order order) {
        String query = "INSERT INTO Orders (buyer_id, product_id, total_amount, status, order_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getBuyerId());
            ps.setInt(2, order.getProductId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getStatus());
            ps.setDate(5, new Date(order.getOrderDate().getTime()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.err.println("[OrderDAO] createOrder failed: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public List<Order> findOrdersByBuyer(int buyerId) {
        String query = "SELECT order_id, buyer_id, product_id, total_amount, status, order_date FROM Orders WHERE buyer_id = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int productId = rs.getInt("product_id");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("status");
                java.util.Date orderDate = rs.getDate("order_date");

                Order order = new Order(buyerId, productId, totalAmount);
                order.setStatus(status);
                order.setOrderId(orderId);
                order.setOrderDate(orderDate);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public boolean cancelOrder(int orderId) {
        String query = "UPDATE Orders SET status = 'Cancelled' WHERE order_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> findOrdersBySeller(int sellerId) {
        String query = "SELECT o.order_id, o.buyer_id, o.product_id, o.total_amount, o.status, o.order_date " +
                       "FROM Orders o JOIN Products p ON o.product_id = p.product_id WHERE p.seller_id = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int buyerId = rs.getInt("buyer_id");
                int productId = rs.getInt("product_id");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("status");
                java.util.Date orderDate = rs.getDate("order_date");

                Order order = new Order(buyerId, productId, totalAmount);
                order.setStatus(status);
                order.setOrderId(orderId);
                order.setOrderDate(orderDate);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> findAllOrders() {
        String query = "SELECT order_id, buyer_id, product_id, total_amount, status, order_date FROM Orders";
        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int buyerId = rs.getInt("buyer_id");
                int productId = rs.getInt("product_id");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("status");
                java.util.Date orderDate = rs.getDate("order_date");

                Order order = new Order(buyerId, productId, totalAmount);
                order.setStatus(status);
                order.setOrderId(orderId);
                order.setOrderDate(orderDate);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
}