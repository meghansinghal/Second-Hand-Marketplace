package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.DBConnection;
import model.Delivery;

public class DeliveryDAO {

    public int createDelivery(Delivery delivery) {
        String query = "INSERT INTO Deliveries (order_id, status, pickup_date, tracking_id) VALUES (?, ?, ?, ?)";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            if (con == null) {
                System.err.println("Database connection failed: connection is null");
                return -1;
            }
            
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, delivery.getOrderId());
            ps.setString(2, delivery.getStatus());
            ps.setDate(3, new Date(delivery.getPickupDate().getTime()));
            ps.setString(4, delivery.getTrackingId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            ps.close();
        } catch (Exception e) {
            System.err.println("Error creating delivery: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
        return -1;
    }

    public Delivery findByOrderId(int orderId) {
        String query = "SELECT delivery_id, order_id, status, pickup_date, delivery_date, delivery_partner, tracking_id FROM Deliveries WHERE order_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int deliveryId = rs.getInt("delivery_id");
                String status = rs.getString("status");
                java.util.Date pickupDate = rs.getDate("pickup_date");
                java.util.Date deliveryDate = rs.getDate("delivery_date");
                String deliveryPartner = rs.getString("delivery_partner");
                String trackingId = rs.getString("tracking_id");

                Delivery delivery = new Delivery(orderId);
                delivery.setDeliveryId(deliveryId);
                delivery.setStatus(status);
                if (deliveryDate != null) {
                    delivery.setDeliveryDate(deliveryDate);
                }
                if (deliveryPartner != null) {
                    delivery.setDeliveryPartner(deliveryPartner);
                }
                return delivery;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateDeliveryStatus(int deliveryId, String status) {
        String query = "UPDATE Deliveries SET status = ? WHERE delivery_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, deliveryId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean assignDeliveryPartner(int deliveryId, String partnerName) {
        String query = "UPDATE Deliveries SET delivery_partner = ?, status = 'In Transit' WHERE delivery_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, partnerName);
            ps.setInt(2, deliveryId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean markDelivered(int deliveryId) {
        String query = "UPDATE Deliveries SET status = 'Delivered', delivery_date = NOW() WHERE delivery_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, deliveryId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}