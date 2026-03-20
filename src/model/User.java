package model;

public class User {

    protected int userId;
    protected String name;
    protected String email;
    protected String password;
    protected String phone;

    public User() {}

    public User(int userId, String name, String email, String password, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public void register() {
        System.out.println(name + " registered successfully.");
    }

    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            System.out.println(name + " logged in successfully.");
            return true;
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    public void logout() {
        System.out.println(name + " logged out.");
    }
}