package dao;

import model.DBConnection;
import model.User;
import model.Buyer;
import model.Seller;
import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean createUser(User user) {
        String query = "INSERT INTO Users (name, email, password, phone, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findByEmail(String email) {
        String query = "SELECT user_id, name, email, password, phone, role FROM Users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String role = rs.getString("role");

                switch (role) {
                    case "Seller":
                        return new Seller(userId, name, email, password, phone);
                    case "Admin":
                        return new Admin(userId, name, email, password, phone);
                    default:
                        return new Buyer(userId, name, email, password, phone);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public User findById(int userId) {
        String query = "SELECT user_id, name, email, password, phone, role FROM Users WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String role = rs.getString("role");

                switch (role) {
                    case "Seller":
                        return new Seller(userId, name, email, password, phone);
                    case "Admin":
                        return new Admin(userId, name, email, password, phone);
                    default:
                        return new Buyer(userId, name, email, password, phone);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> findAllUsers() {
        String query = "SELECT user_id, name, email, password, phone, role FROM Users";
        List<User> users = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String role = rs.getString("role");

                switch (role) {
                    case "Seller":
                        users.add(new Seller(userId, name, email, password, phone));
                        break;
                    case "Admin":
                        users.add(new Admin(userId, name, email, password, phone));
                        break;
                    default:
                        users.add(new Buyer(userId, name, email, password, phone));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}