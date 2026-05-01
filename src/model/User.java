package model;

public class User {

    protected int userId;
    protected String name;
    protected String email;
    protected String password;
    protected String phone;
    protected String role;

    public User() {}

    public User(int userId, String name, String email, String password, String phone) {
        this(userId, name, email, password, phone, "Buyer");
    }

    public User(int userId, String name, String email, String password, String phone, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public void register() {
        System.out.println(name + " registered successfully.");
    }

    public boolean login(String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        if (this.email != null && this.email.equals(email) && this.password.equals(password)) {
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