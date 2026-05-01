package dao;

import model.DBConnection;
import model.Message;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public int sendMessage(Message message) {
        String query = "INSERT INTO Messages (sender_id, recipient_id, content, timestamp, is_read) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, message.getSenderId());
            ps.setInt(2, message.getRecipientId());
            ps.setString(3, message.getContent());
            ps.setDate(4, new Date(message.getTimestamp().getTime()));
            ps.setBoolean(5, false);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Integer> findConversationPartners(int userId) {
        String query = "SELECT DISTINCT partner_id FROM (" +
                "SELECT sender_id AS partner_id FROM Messages WHERE recipient_id = ? " +
                "UNION " +
                "SELECT recipient_id AS partner_id FROM Messages WHERE sender_id = ? " +
                ") AS partners";
        List<Integer> partners = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                partners.add(rs.getInt("partner_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partners;
    }

    public List<Message> findConversation(int userId, int partnerId) {
        String query = "SELECT message_id, sender_id, recipient_id, content, timestamp, is_read " +
                "FROM Messages WHERE (sender_id = ? AND recipient_id = ?) OR (sender_id = ? AND recipient_id = ?) " +
                "ORDER BY timestamp ASC";
        List<Message> messages = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setInt(2, partnerId);
            ps.setInt(3, partnerId);
            ps.setInt(4, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int messageId = rs.getInt("message_id");
                int senderId = rs.getInt("sender_id");
                int recipientId = rs.getInt("recipient_id");
                String content = rs.getString("content");
                java.util.Date timestamp = rs.getDate("timestamp");
                boolean isRead = rs.getBoolean("is_read");

                Message message = new Message(senderId, recipientId, content);
                message.setMessageId(messageId);
                message.setTimestamp(timestamp);
                message.setRead(isRead);
                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public boolean markConversationAsRead(int partnerId, int userId) {
        String query = "UPDATE Messages SET is_read = true WHERE sender_id = ? AND recipient_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, partnerId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Message> findInbox(int recipientId) {
        String query = "SELECT message_id, sender_id, recipient_id, content, timestamp, is_read FROM Messages WHERE recipient_id = ? ORDER BY timestamp DESC";
        List<Message> messages = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, recipientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int messageId = rs.getInt("message_id");
                int senderId = rs.getInt("sender_id");
                String content = rs.getString("content");
                java.util.Date timestamp = rs.getDate("timestamp");
                boolean isRead = rs.getBoolean("is_read");

                Message message = new Message(senderId, recipientId, content);
                message.setMessageId(messageId);
                message.setTimestamp(timestamp);
                message.setRead(isRead);
                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    public boolean markAsRead(int messageId) {
        String query = "UPDATE Messages SET is_read = true WHERE message_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, messageId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}