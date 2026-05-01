package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.DBConnection;
import model.Payment;

public class PaymentDAO {

    public int createPayment(Payment payment) {
        String query = "INSERT INTO Payments (order_id, amount, payment_method, status, payment_date) VALUES (?, ?, ?, ?, ?)";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            if (con == null) {
                System.err.println("Database connection failed: connection is null");
                return -1;
            }
            
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, payment.getOrderId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getPaymentMethod());
            ps.setString(4, payment.getStatus());
            ps.setDate(5, new Date(payment.getPaymentDate().getTime()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            ps.close();
        } catch (Exception e) {
            System.err.println("Error creating payment: " + e.getMessage());
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

    public Payment findByOrderId(int orderId) {
        String query = "SELECT payment_id, order_id, amount, payment_method, status, payment_date FROM Payments WHERE order_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int paymentId = rs.getInt("payment_id");
                double amount = rs.getDouble("amount");
                String paymentMethod = rs.getString("payment_method");
                String status = rs.getString("status");
                java.util.Date paymentDate = rs.getDate("payment_date");

                Payment payment = new Payment(orderId, amount, paymentMethod);
                payment.setPaymentId(paymentId);
                payment.setStatus(status);
                payment.setPaymentDate(paymentDate);
                return payment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePaymentStatus(int paymentId, String status) {
        String query = "UPDATE Payments SET status = ? WHERE payment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, paymentId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}