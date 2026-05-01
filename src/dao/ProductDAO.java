package dao;

import model.DBConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public boolean createProduct(Product product, int sellerId) {
        String query = "INSERT INTO Products (title, description, price, product_condition, category, status, seller_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getCondition());
            ps.setString(5, product.getCategory());
            ps.setString(6, product.getStatus());
            ps.setInt(7, sellerId);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> findBySeller(int sellerId) {
        String query = "SELECT product_id, title, description, price, product_condition, category, status FROM Products WHERE seller_id = ?";
        List<Product> products = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("product_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String condition = rs.getString("product_condition");
                String category = rs.getString("category");
                String status = rs.getString("status");

                Product product = new Product(id, title, description, price, condition, category);
                product.setStatus(status);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> findAll() {
        String query = "SELECT product_id, title, description, price, product_condition, category, status FROM Products";
        List<Product> products = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("product_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String condition = rs.getString("product_condition");
                String category = rs.getString("category");
                String status = rs.getString("status");

                Product product = new Product(id, title, description, price, condition, category);
                product.setStatus(status);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product findById(int productId) {
        String query = "SELECT product_id, title, description, price, product_condition, category, status FROM Products WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String condition = rs.getString("product_condition");
                String category = rs.getString("category");
                String status = rs.getString("status");

                Product product = new Product(productId, title, description, price, condition, category);
                product.setStatus(status);
                return product;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getSellerIdByProduct(int productId) {
        String query = "SELECT seller_id FROM Products WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("seller_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean updateStatus(int productId, String status) {
        String query = "UPDATE Products SET status = ? WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}