import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.*;
import dao.*;
import model.*;
import view.HtmlTemplates;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WebServer {

    private final UserController userController = new UserController();
    private final ProductController productController = new ProductController();
    private final OrderController orderController = new OrderController();
    private final PaymentController paymentController = new PaymentController();
    private final DeliveryController deliveryController = new DeliveryController();
    private final MessageController messageController = new MessageController();
    private final Map<String, User> sessions = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        new WebServer().startServer(8080);
    }

    public void startServer(int port) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HomeHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/logout", new LogoutHandler());
        server.createContext("/products", new ProductsHandler());
        server.createContext("/product", new ProductHandler());
        server.createContext("/payment", new PaymentHandler());
        server.createContext("/payment/confirm", new PaymentConfirmHandler());
        server.createContext("/order/place", new OrderPlaceHandler());
        server.createContext("/orders", new OrdersHandler());
        server.createContext("/buyer/dashboard", new BuyerDashboardHandler());
        server.createContext("/seller/dashboard", new SellerDashboardHandler());
        server.createContext("/admin/dashboard", new AdminDashboardHandler());
        server.createContext("/messages", new MessagesHandler());
        server.createContext("/message/send", new MessageSendHandler());
        server.setExecutor(null);
        System.out.println("Web server started on http://localhost:" + port);
        server.start();
    }

    private User getCurrentUser(HttpExchange exchange) {
        String sessionId = getSessionId(exchange);
        if (sessionId == null) {
            return null;
        }
        return sessions.get(sessionId);
    }

    private String getSessionId(HttpExchange exchange) {
        Headers headers = exchange.getRequestHeaders();
        List<String> cookies = headers.get("Cookie");
        if (cookies == null) {
            return null;
        }
        for (String cookie : cookies) {
            String[] parts = cookie.split(";");
            for (String part : parts) {
                String[] keyValue = part.trim().split("=", 2);
                if (keyValue.length == 2 && "SESSION_ID".equals(keyValue[0])) {
                    return keyValue[1];
                }
            }
        }
        return null;
    }

    private void redirect(HttpExchange exchange, String path) throws IOException {
        exchange.getResponseHeaders().add("Location", path);
        exchange.sendResponseHeaders(302, -1);
        exchange.close();
    }

    private Map<String, String> parseFormBody(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> data = new HashMap<>();
        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2) {
                String key = java.net.URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
                String value = java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8);
                data.put(key, value);
            }
        }
        return data;
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private class HomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            User user = getCurrentUser(exchange);
            List<Product> products = productController.getAllProducts();
            String response = HtmlTemplates.home(products, user, null);
            sendResponse(exchange, response);
        }
    }

    private class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, HtmlTemplates.login(null));
                return;
            }
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> form = parseFormBody(exchange);
                String email = form.getOrDefault("email", "");
                String password = form.getOrDefault("password", "");
                User user = userController.loginUser(email, password);
                if (user == null) {
                    sendResponse(exchange, HtmlTemplates.login("Invalid email or password."));
                    return;
                }
                String id = UUID.randomUUID().toString();
                sessions.put(id, user);
                Headers headers = exchange.getResponseHeaders();
                headers.add("Set-Cookie", "SESSION_ID=" + id + "; Path=/; HttpOnly");
                redirect(exchange, "/");
                return;
            }
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, HtmlTemplates.register(null));
                return;
            }
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> form = parseFormBody(exchange);
                String name = form.getOrDefault("name", "");
                String email = form.getOrDefault("email", "");
                String password = form.getOrDefault("password", "");
                String phone = form.getOrDefault("phone", "");
                String role = form.getOrDefault("role", "Buyer");
                User user;
                if ("Seller".equals(role)) {
                    user = new model.Seller(0, name, email, password, phone);
                } else if ("Admin".equals(role)) {
                    user = new model.Admin(0, name, email, password, phone);
                } else {
                    user = new model.Buyer(0, name, email, password, phone);
                }
                boolean created = userController.registerUser(user);
                if (!created) {
                    sendResponse(exchange, HtmlTemplates.register("Unable to create account. Please try again."));
                    return;
                }
                redirect(exchange, "/login");
                return;
            }
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private class LogoutHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String sessionId = getSessionId(exchange);
            if (sessionId != null) {
                sessions.remove(sessionId);
            }
            redirect(exchange, "/");
        }
    }

    private class ProductsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            User user = getCurrentUser(exchange);
            List<Product> products = productController.getAllProducts();
            String response = HtmlTemplates.home(products, user, null);
            sendResponse(exchange, response);
        }
    }

    private class ProductHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            
            // Handle /product/add
            if (path.equals("/product/add")) {
                handleProductAdd(exchange);
                return;
            }
            
            // Handle /product/{id}
            if (path.startsWith("/product/") && path.length() > 9) {
                handleProductDetail(exchange);
                return;
            }
            
            exchange.sendResponseHeaders(404, -1);
        }
        
        private void handleProductAdd(HttpExchange exchange) throws IOException {
            User user = getCurrentUser(exchange);
            if (user == null || !"Seller".equals(user.getRole())) {
                redirect(exchange, "/login");
                return;
            }
            if ("GET".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, HtmlTemplates.addProduct(null, user));
                return;
            }
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> form = parseFormBody(exchange);
                String title = form.getOrDefault("title", "");
                String description = form.getOrDefault("description", "");
                String priceText = form.getOrDefault("price", "0");
                String condition = form.getOrDefault("condition", "");
                String category = form.getOrDefault("category", "");
                double price = 0;
                try {
                    price = Double.parseDouble(priceText);
                } catch (NumberFormatException e) {
                    sendResponse(exchange, HtmlTemplates.addProduct("Price must be a valid number.", user));
                    return;
                }
                Product product = new Product(0, title, description, price, condition, category);
                boolean created = productController.addProduct(product, user.getUserId());
                if (!created) {
                    sendResponse(exchange, HtmlTemplates.addProduct("Unable to add product.", user));
                    return;
                }
                redirect(exchange, "/products");
                return;
            }
            exchange.sendResponseHeaders(405, -1);
        }
        
        private void handleProductDetail(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            try {
                String path = exchange.getRequestURI().getPath();
                String idStr = path.substring(9); // Remove "/product/"
                int productId = Integer.parseInt(idStr);
                
                User user = getCurrentUser(exchange);
                Product product = productController.getProductById(productId);
                
                if (product == null) {
                    sendResponse(exchange, HtmlTemplates.page("Product Not Found", "<div class=\"card\"><h2>Product not found</h2><p><a href=\"/products\">Back to products</a></p></div>"), 404);
                    return;
                }
                
                // Get seller information
                User seller = null;
                int sellerId = productController.getSellerIdByProduct(productId);
                if (sellerId > 0) {
                    seller = userController.getUserById(sellerId);
                }

                String response = HtmlTemplates.productDetail(product, user, seller, null);
                sendResponse(exchange, response);
            } catch (Exception e) {
                sendResponse(exchange, HtmlTemplates.page("Error", "<div class=\"card\"><h2>Error loading product</h2><p>" + e.getMessage() + "</p></div>"), 500);
            }
        }
    }

    private class PaymentHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            User user = getCurrentUser(exchange);
            if (user == null || !"Buyer".equals(user.getRole())) {
                redirect(exchange, "/login");
                return;
            }
            try {
                String query = exchange.getRequestURI().getQuery();
                String productIdStr = "";
                if (query != null) {
                    for (String param : query.split("&")) {
                        if (param.startsWith("product_id=")) {
                            productIdStr = param.substring(11);
                            break;
                        }
                    }
                }
                if (productIdStr.isEmpty()) {
                    redirect(exchange, "/products");
                    return;
                }

                int productId = Integer.parseInt(productIdStr);
                Product product = productController.getProductById(productId);
                if (product == null) {
                    sendResponse(exchange, HtmlTemplates.page("Error", "<div class=\"card\"><h2>Product not found</h2></div>"), 404);
                    return;
                }
                String response = HtmlTemplates.paymentPage(product, user, null);
                sendResponse(exchange, response);
            } catch (Exception e) {
                sendResponse(exchange, HtmlTemplates.page("Error", "<div class=\"card\"><h2>Error loading payment page</h2></div>"), 500);
            }
        }
    }

    private class PaymentConfirmHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            User user = getCurrentUser(exchange);
            if (user == null || !"Buyer".equals(user.getRole())) {
                redirect(exchange, "/login");
                return;
            }
            try {
                Map<String, String> form = parseFormBody(exchange);
                String productIdStr = form.getOrDefault("product_id", "0");
                String amountStr = form.getOrDefault("amount", "0");
                String method = form.getOrDefault("method", "Credit Card");

                int productId = Integer.parseInt(productIdStr);
                double amount = Double.parseDouble(amountStr);

                // Verify product exists
                Product product = productController.getProductById(productId);
                if (product == null) {
                    sendResponse(exchange, HtmlTemplates.page("Error",
                        "<div class=\"card\"><h2>Product Not Found</h2>" +
                        "<p>The product you are trying to purchase does not exist.</p>" +
                        "<a href=\"/products\" class=\"btn\">Back to Products</a></div>"), 404);
                    return;
                }

                // Check product is still available before creating an order
                if (!"Available".equals(product.getStatus())) {
                    sendResponse(exchange, HtmlTemplates.page("Product Unavailable",
                        "<div class=\"card\"><h2>Product No Longer Available</h2>" +
                        "<p>Sorry, <strong>" + product.getTitle() + "</strong> has already been sold.</p>" +
                        "<a href=\"/products\" class=\"btn\">Browse Other Products</a></div>"), 409);
                    return;
                }

                // Create the order
                Order order = new Order(user.getUserId(), productId, amount);
                int orderId = orderController.placeOrder(order);

                if (orderId <= 0) {
                    sendResponse(exchange, HtmlTemplates.page("Order Failed",
                        "<div class=\"card\"><h2>Order Creation Failed</h2>" +
                        "<p>We could not create your order. This may be due to a database connectivity issue.</p>" +
                        "<p>Please check that MySQL is running and the database credentials in <code>DBConnection.java</code> are correct.</p>" +
                        "<a href=\"/product/" + productId + "\" class=\"btn\">Try Again</a></div>"), 500);
                    return;
                }

                // Process payment
                Payment payment = new Payment(orderId, amount, method);
                int paymentId = paymentController.processPayment(payment);

                if (paymentId <= 0) {
                    // Order was created but payment failed — cancel the order to keep data consistent
                    orderController.cancelOrder(orderId);
                    sendResponse(exchange, HtmlTemplates.page("Payment Failed",
                        "<div class=\"card\"><h2>Payment Processing Failed</h2>" +
                        "<p>Your order was cancelled. Please try again.</p>" +
                        "<a href=\"/product/" + productId + "\" class=\"btn\">Try Again</a></div>"), 500);
                    return;
                }

                // Confirm the payment status
                if (!paymentController.confirmPayment(paymentId)) {
                    sendResponse(exchange, HtmlTemplates.page("Payment Error",
                        "<div class=\"card\"><h2>Payment Confirmation Failed</h2>" +
                        "<p>Your payment could not be confirmed. Please contact support.</p>" +
                        "<a href=\"/orders\" class=\"btn\">View Orders</a></div>"), 500);
                    return;
                }

                // Mark product as sold and create delivery record
                productController.markProductSold(productId);
                Delivery delivery = new Delivery(orderId);
                deliveryController.createDelivery(delivery);

                redirect(exchange, "/orders");

            } catch (NumberFormatException e) {
                sendResponse(exchange, HtmlTemplates.page("Invalid Request",
                    "<div class=\"card\"><h2>Invalid Request</h2>" +
                    "<p>Invalid product or amount value. Please go back and try again.</p>" +
                    "<a href=\"/products\" class=\"btn\">Back to Products</a></div>"), 400);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, HtmlTemplates.page("Error",
                    "<div class=\"card\"><h2>Unexpected Error</h2>" +
                    "<p>" + e.getClass().getSimpleName() + ": " + e.getMessage() + "</p>" +
                    "<a href=\"/products\" class=\"btn\">Back to Products</a></div>"), 500);
            }
        }
    }

    private class MessageSendHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            User user = getCurrentUser(exchange);
            if (user == null) {
                redirect(exchange, "/login");
                return;
            }
            
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> form = parseFormBody(exchange);
                String recipientIdStr = form.getOrDefault("recipient_id", "0");
                String content = form.getOrDefault("content", "");
                String productIdStr = form.getOrDefault("product_id", "0");
                
                try {
                    int recipientId = Integer.parseInt(recipientIdStr);
                    Message message = new Message(user.getUserId(), recipientId, content);
                    messageController.sendMessage(message);
                    String redirectPath = "/messages?partner_id=" + recipientId;
                    if (!productIdStr.equals("0") && !productIdStr.isEmpty()) {
                        redirectPath += "&product_id=" + productIdStr;
                    }
                    redirect(exchange, redirectPath);
                } catch (Exception e) {
                    sendResponse(exchange, HtmlTemplates.page("Error", "<div class=\"card\"><h2>Error sending message</h2></div>"), 500);
                }
                return;
            }
            
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private class OrderPlaceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            User user = getCurrentUser(exchange);
            if (user == null || !"Buyer".equals(user.getRole())) {
                redirect(exchange, "/login");
                return;
            }
            Map<String, String> form = parseFormBody(exchange);
            String productIdText = form.getOrDefault("product_id", "0");
            String priceText = form.getOrDefault("price", "0");
            int productId;
            double price;
            try {
                productId = Integer.parseInt(productIdText);
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                redirect(exchange, "/products");
                return;
            }
            Order order = new Order(user.getUserId(), productId, price);
            int orderId = orderController.placeOrder(order);
            if (orderId < 0) {
                redirect(exchange, "/products");
                return;
            }
            productController.markProductSold(productId);
            redirect(exchange, "/orders");
        }
    }

    private class OrdersHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            User user = getCurrentUser(exchange);
            if (user == null || !"Buyer".equals(user.getRole())) {
                redirect(exchange, "/login");
                return;
            }
            List<Order> orders = orderController.getOrdersByBuyer(user.getUserId());
            List<Delivery> deliveries = new ArrayList<>();
            List<Payment> payments = new ArrayList<>();
            for (Order order : orders) {
                Delivery delivery = deliveryController.getDeliveryByOrder(order.getOrderId());
                if (delivery != null) {
                    deliveries.add(delivery);
                }
                Payment payment = paymentController.getPaymentByOrder(order.getOrderId());
                if (payment != null) {
                    payments.add(payment);
                }
            }
            String response = HtmlTemplates.orders(user, orders, deliveries, payments);
            sendResponse(exchange, response);
        }
    }

    private class BuyerDashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            User user = getCurrentUser(exchange);
            if (user == null || !"Buyer".equals(user.getRole())) {
                redirect(exchange, "/login");
                return;
            }
            List<Order> orders = orderController.getOrdersByBuyer(user.getUserId());
            List<Delivery> deliveries = new ArrayList<>();
            for (Order order : orders) {
                Delivery delivery = deliveryController.getDeliveryByOrder(order.getOrderId());
                if (delivery != null) {
                    deliveries.add(delivery);
                }
            }
            String response = HtmlTemplates.buyerDashboard(user, orders, deliveries);
            sendResponse(exchange, response);
        }
    }

    private class SellerDashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            User user = getCurrentUser(exchange);
            if (user == null || !"Seller".equals(user.getRole())) {
                redirect(exchange, "/login");
                return;
            }
            List<Product> products = productController.getProductsBySeller(user.getUserId());
            List<Order> orders = orderController.getOrdersBySeller(user.getUserId());
            String response = HtmlTemplates.sellerDashboard(user, products, orders);
            sendResponse(exchange, response);
        }
    }

    private class AdminDashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            User user = getCurrentUser(exchange);
            if (user == null || !"Admin".equals(user.getRole())) {
                redirect(exchange, "/login");
                return;
            }
            java.util.List<User> allUsers = userController.getAllUsers();
            java.util.List<Order> allOrders = orderController.getAllOrders();
            String response = HtmlTemplates.adminDashboard(user, allUsers, allOrders);
            sendResponse(exchange, response);
        }
    }

    private class MessagesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            User user = getCurrentUser(exchange);
            if (user == null) {
                redirect(exchange, "/login");
                return;
            }

            String query = exchange.getRequestURI().getQuery();
            int partnerId = 0;
            int productId = 0;
            if (query != null) {
                for (String param : query.split("&")) {
                    if (param.startsWith("partner_id=")) {
                        try {
                            partnerId = Integer.parseInt(param.substring(11));
                        } catch (NumberFormatException ignored) {}
                    }
                    if (param.startsWith("product_id=")) {
                        try {
                            productId = Integer.parseInt(param.substring(11));
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }

            if (partnerId > 0) {
                User partner = userController.getUserById(partnerId);
                if (partner == null) {
                    sendResponse(exchange, HtmlTemplates.page("Conversation Not Found", "<div class=\"card\"><h2>Conversation not found</h2><p>The selected chat partner does not exist.</p></div>"), 404);
                    return;
                }

                List<Message> thread = messageController.getConversation(user.getUserId(), partnerId);
                messageController.markConversationAsRead(partnerId, user.getUserId());
                String response = HtmlTemplates.chatThread(user, partner, thread, partnerId, productId, getProductTitle(productId), null);
                sendResponse(exchange, response);
                return;
            }

            List<Integer> partnerIds = messageController.getConversationPartners(user.getUserId());
            List<User> partners = new ArrayList<>();
            for (Integer id : partnerIds) {
                User partner = userController.getUserById(id);
                if (partner != null) {
                    partners.add(partner);
                }
            }
            String response = HtmlTemplates.messages(user, partners);
            sendResponse(exchange, response);
        }
    }

    private String getProductTitle(int productId) {
        if (productId <= 0) {
            return "";
        }
        Product product = productController.getProductById(productId);
        return product != null ? product.getTitle() : "";
    }
}