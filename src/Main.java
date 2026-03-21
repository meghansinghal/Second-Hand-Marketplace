import model.*;
import controller.*;
import view.*;

public class Main {

    public static void main(String[] args) {

        // Create objects
        Buyer buyer = new Buyer(1, "Meghan", "meghan@gmail.com", "1234", "9999999999");
        UserController userController = new UserController();
        UserView userView = new UserView();

        OrderController orderController = new OrderController();
        DeliveryController deliveryController = new DeliveryController();

        // Register
        userController.registerUser(buyer);

        // Login
        String email = userView.getEmail();
        String password = userView.getPassword();

        boolean isLoggedIn = userController.loginUser(buyer, email, password);

        if (isLoggedIn) {

            // User actions
            buyer.browseProducts();

            //PLACE ORDER (DB INTEGRATED)
            Order order = new Order(buyer.getUserId(), 1, 50000); // productId = 1 for now
            int orderId = orderController.placeOrder(order);

            // View orders
            orderController.viewOrders();

            //DELIVERY FLOW
            deliveryController.createDelivery(orderId);
            deliveryController.updateDeliveryStatus(orderId, "Shipped");

            // Optional cancel
            orderController.cancelOrder(orderId);

            // View delivery
            deliveryController.viewDelivery();

            // Track order (optional existing method)
            buyer.trackOrder();

        } else {
            userView.showMessage("Login failed.");
        }

        // Logout
        userController.logoutUser(buyer);
    }
}