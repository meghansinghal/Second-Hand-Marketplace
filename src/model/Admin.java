package model;

public class Admin extends User {

    public Admin(int userId, String name, String email, String password, String phone) {
        super(userId, name, email, password, phone);
    }

    public void manageUsers() {
        System.out.println("Managing users...");
    }

    public void removeListing() {
        System.out.println("Listing removed.");
    }
}