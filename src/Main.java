import model.*;
import controller.*;
import view.*;

public class Main {

    public static void main(String[] args) {

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
    }
}