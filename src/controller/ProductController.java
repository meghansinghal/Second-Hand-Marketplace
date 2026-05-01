package controller;

import dao.ProductDAO;
import model.Product;

import java.util.List;

public class ProductController {

    private final ProductDAO productDAO = new ProductDAO();

    public boolean addProduct(Product product, int sellerId) {
        return productDAO.createProduct(product, sellerId);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public List<Product> getProductsBySeller(int sellerId) {
        return productDAO.findBySeller(sellerId);
    }

    public Product getProductById(int productId) {
        return productDAO.findById(productId);
    }

    public boolean markProductSold(int productId) {
        return productDAO.updateStatus(productId, "Sold");
    }

    public int getSellerIdByProduct(int productId) {
        return productDAO.getSellerIdByProduct(productId);
    }
}