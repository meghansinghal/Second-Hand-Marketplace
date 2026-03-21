import model.*;
import controller.*;
import view.*;

public class Main {

    public static void main(String[] args) {

        // Driver test
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("SUCCESS: Driver loaded");
        } catch (Exception e) {
            System.out.println("ERROR: Driver NOT loaded");
            e.printStackTrace();
        }

        // Create objects
        Buyer buyer = new Buyer(1, "Meghan", "meghan@gmail.com", "1234", "9999999999");
        UserController userController = new UserController();
        UserView userView = new UserView();

        // Register
        userController.registerUser(buyer);

        // Login
        String email = userView.getEmail();
        String password = userView.getPassword();

        boolean isLoggedIn = userController.loginUser(buyer, email, password);

        if (isLoggedIn) {
            buyer.browseProducts();
            buyer.placeOrder();
            buyer.trackOrder();
        } else {
            userView.showMessage("Login failed.");
        }

        // Logout
        userController.logoutUser(buyer);

        // Product testing
        ProductController controller = new ProductController();

        // Add product
        Product p1 = new Product(0, "Laptop", "Good condition", 50000, "Used", "Electronics");
        controller.addProduct(p1);

        // Update
        controller.updateProduct(1, "Gaming Laptop", 60000);

        // Mark sold
        controller.markProductSold(1);
    }
}