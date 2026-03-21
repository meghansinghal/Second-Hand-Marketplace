package controller;

import model.Product;
import model.DBConnection;

import java.sql.*;

public class ProductController {

    // ADD PRODUCT
    public void addProduct(Product product) {
        String query = "INSERT INTO Product (title, description, price, product_condition, category, status, seller_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("ERROR: Cannot add product. DB not connected.");
                return;
            }

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getCondition());
            ps.setString(5, product.getCategory());
            ps.setString(6, "Available");
            ps.setInt(7, 1);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("SUCCESS: Product added successfully.");
            } else {
                System.out.println("ERROR: Failed to add product.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE PRODUCT
    public void updateProduct(int productId, String newTitle, double newPrice) {
        String query = "UPDATE Product SET title=?, price=? WHERE product_id=?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("ERROR: Cannot update. DB not connected.");
                return;
            }

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, newTitle);
            ps.setDouble(2, newPrice);
            ps.setInt(3, productId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("SUCCESS: Product updated successfully.");
            } else {
                System.out.println("ERROR: No product found with that ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MARK AS SOLD
    public void markProductSold(int productId) {
        String query = "UPDATE Product SET status='Sold' WHERE product_id=?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("ERROR: Cannot update. DB not connected.");
                return;
            }

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, productId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("SUCCESS: Product marked as sold.");
            } else {
                System.out.println("ERROR: Product not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}