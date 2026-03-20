package model;

public class Buyer extends User {

    public Buyer(int userId, String name, String email, String password, String phone) {
        super(userId, name, email, password, phone);
    }

    public void browseProducts() {
        System.out.println("Browsing products...");
    }

    public void placeOrder() {
        System.out.println("Order placed successfully.");
    }

    public void trackOrder() {
        System.out.println("Tracking order...");
    }
}