package model;

public class Seller extends User {

    public Seller(int userId, String name, String email, String password, String phone) {
        super(userId, name, email, password, phone, "Seller");
    }

    public void createListing() {
        System.out.println("Product listed successfully.");
    }

    public void manageListings() {
        System.out.println("Managing listings...");
    }
}