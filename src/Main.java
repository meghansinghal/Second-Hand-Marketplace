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

        OrderController orderController = new OrderController();
        DeliveryController deliveryController = new DeliveryController();

        // Register
        userController.registerUser(buyer);

        // Login
        String email = userView.getEmail();
        String password = userView.getPassword();

        boolean isLoggedIn = userController.loginUser(buyer, email, password);

        if (isLoggedIn) {

            // Browse products
            buyer.browseProducts();

            // Place Order (DB Integrated)
            Order order = new Order(buyer.getUserId(), 1, 50000);
            int orderId = orderController.placeOrder(order);

            // View Orders
            orderController.viewOrders();

            // Delivery Flow
            deliveryController.createDelivery(orderId);
            deliveryController.updateDeliveryStatus(orderId, "Shipped");

            // Track Order
            buyer.trackOrder();

        } else {
            userView.showMessage("Login failed.");
        }

        // Logout
        userController.logoutUser(buyer);

        // Product testing (Your module)
        ProductController controller = new ProductController();

        // Add product
        Product p1 = new Product(0, "Laptop", "Good condition", 50000, "Used", "Electronics");
        controller.addProduct(p1);

        // Update product
        controller.updateProduct(1, "Gaming Laptop", 60000);

        // Mark product sold
        controller.markProductSold(1);
    }
}