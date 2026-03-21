package model;

public class Product {

    private int productId;
    private String title;
    private String description;
    private double price;
    private String condition;
    private String category;
    private String status;

    // Constructor
    public Product(int productId, String title, String description, double price, String condition, String category) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.category = category;
        this.status = "Available";
    }

    // ===== GETTERS (REQUIRED FOR DB) =====
    public int getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    // ===== SETTERS (optional but useful) =====
    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ===== METHODS =====
    public void updateDetails(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public void markAsSold() {
        this.status = "Sold";
    }

    public void displayProduct() {
        System.out.println("\nProduct ID: " + productId);
        System.out.println("Title: " + title);
        System.out.println("Price: " + price);
        System.out.println("Status: " + status);
    }
}