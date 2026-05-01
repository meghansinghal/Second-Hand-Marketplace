package view;

import model.Order;
import model.Product;
import model.User;
import model.Delivery;
import model.Payment;
import model.Message;

import java.text.SimpleDateFormat;
import java.util.List;

public class HtmlTemplates {

    private static final String CSS_STYLES = 
        "* { margin: 0; padding: 0; box-sizing: border-box; }" +
        "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; color: #333; }" +
        "header { background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%); color: white; padding: 20px 0; box-shadow: 0 4px 15px rgba(0,0,0,0.2); }" +
        ".container { max-width: 1200px; margin: 0 auto; padding: 0 15px; }" +
        "header h1 { font-size: 28px; font-weight: 600; }" +
        "main { padding: 40px 15px; }" +
        ".card { background: white; border-radius: 12px; box-shadow: 0 8px 25px rgba(0,0,0,0.12); margin-bottom: 25px; padding: 30px; transition: transform 0.3s, box-shadow 0.3s; }" +
        ".card:hover { transform: translateY(-2px); box-shadow: 0 12px 35px rgba(0,0,0,0.15); }" +
        ".nav-links { display: flex; gap: 12px; flex-wrap: wrap; margin-top: 15px; }" +
        ".btn { display: inline-block; padding: 12px 24px; margin: 8px 6px 8px 0; border: none; border-radius: 8px; text-decoration: none; font-weight: 600; cursor: pointer; transition: all 0.3s; }" +
        ".btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }" +
        ".btn-primary:hover { transform: translateY(-2px); box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4); }" +
        ".btn-secondary { background: #f0f0f0; color: #333; }" +
        ".btn-secondary:hover { background: #e0e0e0; }" +
        ".btn-success { background: #10b981; color: white; }" +
        ".btn-success:hover { background: #059669; }" +
        ".btn-danger { background: #ef4444; color: white; }" +
        ".btn-danger:hover { background: #dc2626; }" +
        "table { width: 100%; border-collapse: collapse; margin-top: 20px; }" +
        "th { background: #f3f4f6; padding: 15px; text-align: left; font-weight: 600; color: #374151; border-bottom: 2px solid #e5e7eb; }" +
        "td { padding: 15px; border-bottom: 1px solid #e5e7eb; }" +
        "tr:hover { background: #f9fafb; }" +
        "form label { display: block; margin-top: 16px; margin-bottom: 8px; font-weight: 600; color: #374151; }" +
        "form input, form select, form textarea { width: 100%; padding: 12px; border: 1px solid #d1d5db; border-radius: 8px; font-size: 14px; }" +
        "form input:focus, form select:focus, form textarea:focus { outline: none; border-color: #667eea; box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1); }" +
        "form button { margin-top: 20px; padding: 14px 28px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border: none; border-radius: 8px; cursor: pointer; font-weight: 600; font-size: 16px; }" +
        "form button:hover { transform: translateY(-2px); box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4); }" +
        ".alert { padding: 16px 20px; border-radius: 8px; margin-bottom: 20px; border-left: 4px solid; }" +
        ".alert-error { background: #fee2e2; color: #991b1b; border-left-color: #ef4444; }" +
        ".alert-success { background: #dcfce7; color: #166534; border-left-color: #10b981; }" +
        ".alert-info { background: #dbeafe; color: #1e40af; border-left-color: #3b82f6; }" +
        ".status-badge { display: inline-block; padding: 6px 12px; border-radius: 20px; font-size: 12px; font-weight: 600; }" +
        ".status-pending { background: #fef3c7; color: #92400e; }" +
        ".status-active { background: #dcfce7; color: #166534; }" +
        ".status-sold { background: #fee2e2; color: #991b1b; }" +
        ".status-completed { background: #dbeafe; color: #1e40af; }" +
        ".chat-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }" +
        ".chat-thread { display: flex; flex-direction: column; gap: 20px; }" +
        ".chat-container { background: #f8fafc; border-radius: 18px; padding: 20px; display: flex; flex-direction: column; gap: 14px; max-height: 600px; overflow-y: auto; }" +
        ".chat-bubble { max-width: 75%; padding: 16px 18px; border-radius: 18px; position: relative; line-height: 1.5; }" +
        ".chat-bubble.incoming { background: #e5e7eb; align-self: flex-start; }" +
        ".chat-bubble.outgoing { background: #6366f1; color: white; align-self: flex-end; }" +
        ".chat-meta { margin-top: 8px; font-size: 12px; color: rgba(55, 65, 81, 0.8); }" +
        ".chat-input-area { margin-top: 20px; }" +
        ".chat-list-card { padding: 18px; margin-bottom: 12px; border: 1px solid #e5e7eb; border-radius: 16px; transition: background 0.2s; }" +
        ".chat-list-card:hover { background: #f3f4f6; }" +
        ".chat-list-link { text-decoration: none; color: inherit; display: block; }" +
        ".chat-product-banner { background: #eef2ff; padding: 12px 16px; border-radius: 14px; margin-bottom: 14px; color: #3730a3; font-weight: 600; }" +
        ".grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 25px; }" +
        ".product-card { background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.1); transition: transform 0.3s; }" +
        ".product-card:hover { transform: translateY(-8px); box-shadow: 0 12px 30px rgba(0,0,0,0.15); }" +
        ".product-header { padding: 20px; }" +
        ".product-title { font-size: 18px; font-weight: 600; margin-bottom: 8px; }" +
        ".product-price { font-size: 24px; font-weight: 700; color: #667eea; margin-bottom: 10px; }" +
        ".product-condition { color: #6b7280; font-size: 14px; margin-bottom: 12px; }" +
        ".user-info { background: #f9fafb; padding: 12px 20px; color: #6b7280; font-size: 14px; }" +
        ".dashboard-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; flex-wrap: wrap; gap: 15px; }" +
        ".dashboard-header h2 { font-size: 28px; color: #1f2937; }" +
        "h2 { color: #1f2937; margin-bottom: 20px; font-size: 24px; }" +
        "h3 { color: #374151; margin: 20px 0 15px 0; font-size: 18px; }" +
        ".user-badge { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 8px 16px; border-radius: 20px; font-size: 14px; font-weight: 600; }" +
        ".stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 30px; }" +
        ".stat-card { background: white; padding: 20px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); text-align: center; }" +
        ".stat-number { font-size: 32px; font-weight: 700; color: #667eea; }" +
        ".stat-label { color: #6b7280; font-size: 14px; margin-top: 5px; }";

    public static String page(String title, String body) {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>" + title + " | SecondHand Marketplace</title>" +
                "<style>" + CSS_STYLES + "</style>" +
                "</head>" +
                "<body>" +
                "<header>" +
                "<div class=\"container\">" +
                "<h1>🛍 SecondHand Marketplace</h1>" +
                "</div>" +
                "</header>" +
                "<main><div class=\"container\">" + body + "</div></main>" +
                "</body></html>";
    }

    public static String navigation(User user) {
        StringBuilder nav = new StringBuilder();
        nav.append("<div class=\"nav-links\" style=\"margin-bottom:30px;\">");
        nav.append("<a class=\"btn btn-secondary\" href=\"/\">Home</a>");
        nav.append("<a class=\"btn btn-secondary\" href=\"/products\">Browse</a>");
        
        if (user == null) {
            nav.append("<a class=\"btn btn-primary\" href=\"/login\">Login</a>");
            nav.append("<a class=\"btn btn-secondary\" href=\"/register\">Register</a>");
        } else {
            nav.append("<span class=\"user-badge\">👤 " + user.getName() + " (" + user.getRole() + ")</span>");
            if ("Seller".equals(user.getRole())) {
                nav.append("<a class=\"btn btn-primary\" href=\"/seller/dashboard\">Seller Panel</a>");
                nav.append("<a class=\"btn btn-secondary\" href=\"/product/add\">Add Listing</a>");
            } else if ("Buyer".equals(user.getRole())) {
                nav.append("<a class=\"btn btn-primary\" href=\"/buyer/dashboard\">My Dashboard</a>");
                nav.append("<a class=\"btn btn-secondary\" href=\"/orders\">Orders</a>");
            } else if ("Admin".equals(user.getRole())) {
                nav.append("<a class=\"btn btn-danger\" href=\"/admin/dashboard\">Admin Panel</a>");
            }
            nav.append("<a class=\"btn btn-secondary\" href=\"/messages\">Messages</a>");
            nav.append("<a class=\"btn btn-secondary\" href=\"/logout\">Logout</a>");
        }
        nav.append("</div>");
        return nav.toString();
    }

    public static String home(List<Product> products, User user, String message) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(user));
        if (message != null && !message.isEmpty()) {
            body.append("<div class=\"alert alert-info\">").append(message).append("</div>");
        }
        body.append("<div class=\"card\" style=\"text-align: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 50px;\">");
        body.append("<h2 style=\"color: white; font-size: 36px; margin-bottom: 15px;\">Welcome to SecondHand Marketplace</h2>");
        body.append("<p style=\"font-size: 18px; margin-bottom: 20px;\">Buy and sell quality used products with confidence</p>");
        body.append("<a class=\"btn btn-primary\" style=\"background: white; color: #667eea;\" href=\"/products\">Start Shopping</a>");
        body.append("</div>");
        body.append("<h2>Latest Listings</h2>");
        if (products.isEmpty()) {
            body.append("<div class=\"card\"><p>No products available yet.</p></div>");
        } else {
            body.append("<div class=\"grid\">");
            for (Product product : products) {
                body.append(productCardHtml(product, user));
            }
            body.append("</div>");
        }
        return page("Home", body.toString());
    }

    private static String productCardHtml(Product product, User user) {
        StringBuilder card = new StringBuilder();
        card.append("<div class=\"product-card\">");
        card.append("<div class=\"product-header\">");
        card.append("<div class=\"product-title\">").append(product.getTitle()).append("</div>");
        card.append("<div class=\"product-price\">$").append(String.format("%.2f", product.getPrice())).append("</div>");
        card.append("<div class=\"product-condition\">").append(product.getCondition()).append(" | ").append(product.getCategory()).append("</div>");
        card.append("<span class=\"status-badge status-" + (product.getStatus().equals("Available") ? "active" : "sold") + "\">").append(product.getStatus()).append("</span>");
        card.append("<p style=\"margin-top: 15px; color: #6b7280; font-size: 14px; line-height: 1.6;\">").append(product.getDescription()).append("</p>");
        card.append("<a href=\"/product/").append(product.getProductId()).append("\" class=\"btn btn-success\" style=\"width: 100%; text-align: center; display: inline-block; margin-top: 15px; text-decoration: none;\">View Details</a>");
        card.append("</div>");
        card.append("</div>");
        return card.toString();
    }

    public static String login(String message) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(null));
        if (message != null && !message.isEmpty()) {
            body.append("<div class=\"alert alert-error\">").append(message).append("</div>");
        }
        body.append("<div class=\"card\" style=\"max-width: 450px; margin: 40px auto;\">");
        body.append("<h2 style=\"text-align: center; margin-bottom: 30px;\">Login to Your Account</h2>");
        body.append("<form method=\"post\" action=\"/login\">");
        body.append("<label for=\"email\">Email Address</label>");
        body.append("<input id=\"email\" name=\"email\" type=\"email\" placeholder=\"your@email.com\" required>");
        body.append("<label for=\"password\">Password</label>");
        body.append("<input id=\"password\" name=\"password\" type=\"password\" placeholder=\"Enter password\" required>");
        body.append("<button type=\"submit\" class=\"btn btn-primary\" style=\"width: 100%;\">Sign In</button>");
        body.append("<p style=\"text-align: center; margin-top: 20px; color: #6b7280;\">");
        body.append("Don't have an account? <a href=\"/register\" style=\"color: #667eea; text-decoration: none; font-weight: 600;\">Create one</a>");
        body.append("</p>");
        body.append("</form></div>");
        return page("Login", body.toString());
    }

    public static String register(String message) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(null));
        if (message != null && !message.isEmpty()) {
            body.append("<div class=\"alert alert-error\">").append(message).append("</div>");
        }
        body.append("<div class=\"card\" style=\"max-width: 500px; margin: 40px auto;\">");
        body.append("<h2 style=\"text-align: center; margin-bottom: 30px;\">Create Your Account</h2>");
        body.append("<form method=\"post\" action=\"/register\">");
        body.append("<label for=\"name\">Full Name</label>");
        body.append("<input id=\"name\" name=\"name\" type=\"text\" placeholder=\"John Doe\" required>");
        body.append("<label for=\"email\">Email Address</label>");
        body.append("<input id=\"email\" name=\"email\" type=\"email\" placeholder=\"your@email.com\" required>");
        body.append("<label for=\"password\">Password</label>");
        body.append("<input id=\"password\" name=\"password\" type=\"password\" placeholder=\"Choose a strong password\" required>");
        body.append("<label for=\"phone\">Phone Number</label>");
        body.append("<input id=\"phone\" name=\"phone\" type=\"text\" placeholder=\"+1-234-567-8900\" required>");
        body.append("<label for=\"role\">I want to</label>");
        body.append("<select id=\"role\" name=\"role\" required><option value=\"Buyer\">Buy Items</option><option value=\"Seller\">Sell Items</option></select>");
        body.append("<button type=\"submit\" class=\"btn btn-primary\" style=\"width: 100%;\">Create Account</button>");
        body.append("<p style=\"text-align: center; margin-top: 20px; color: #6b7280;\">");
        body.append("Already have an account? <a href=\"/login\" style=\"color: #667eea; text-decoration: none; font-weight: 600;\">Sign in</a>");
        body.append("</p>");
        body.append("</form></div>");
        return page("Register", body.toString());
    }

    public static String addProduct(String message, User user) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(user));
        if (message != null && !message.isEmpty()) {
            body.append("<div class=\"alert alert-" + (message.contains("Unable") ? "error" : "success") + "\">").append(message).append("</div>");
        }
        body.append("<div class=\"card\" style=\"max-width: 700px; margin: 0 auto;\">");
        body.append("<h2>Create New Listing</h2>");
        body.append("<form method=\"post\" action=\"/product/add\">");
        body.append("<label for=\"title\">Product Title</label>");
        body.append("<input id=\"title\" name=\"title\" type=\"text\" placeholder=\"e.g., iPhone 12 Pro Max\" required>");
        body.append("<label for=\"description\">Description</label>");
        body.append("<textarea id=\"description\" name=\"description\" placeholder=\"Describe the product condition, features, and reason for selling...\" rows=\"5\" required></textarea>");
        body.append("<label for=\"price\">Price ($)</label>");
        body.append("<input id=\"price\" name=\"price\" type=\"number\" step=\"0.01\" placeholder=\"0.00\" required>");
        body.append("<label for=\"condition\">Condition</label>");
        body.append("<select id=\"condition\" name=\"condition\" required><option>Like New</option><option>Excellent</option><option>Good</option><option>Fair</option></select>");
        body.append("<label for=\"category\">Category</label>");
        body.append("<select id=\"category\" name=\"category\" required><option>Electronics</option><option>Furniture</option><option>Clothing</option><option>Books</option><option>Sports</option><option>Other</option></select>");
        body.append("<button type=\"submit\" class=\"btn btn-primary\" style=\"width: 100%;\">Publish Listing</button>");
        body.append("</form></div>");
        return page("Add Product", body.toString());
    }

    public static String buyerDashboard(User user, List<Order> orders, List<Delivery> deliveries) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(user));
        body.append("<div class=\"dashboard-header\"><h2>My Dashboard</h2></div>");
        
        body.append("<div class=\"stats-grid\">");
        body.append("<div class=\"stat-card\"><div class=\"stat-number\">").append(orders.size()).append("</div><div class=\"stat-label\">Total Orders</div></div>");
        long completedCount = orders.stream().filter(o -> "Delivered".equals(o.getStatus())).count();
        body.append("<div class=\"stat-card\"><div class=\"stat-number\">").append(completedCount).append("</div><div class=\"stat-label\">Completed</div></div>");
        body.append("</div>");

        body.append("<a class=\"btn btn-primary\" href=\"/products\">Browse Products</a>");
        
        body.append("<h3>Recent Orders</h3>");
        if (orders.isEmpty()) {
            body.append("<div class=\"card\"><p>No orders yet. <a href=\"/products\" style=\"color: #667eea;\">Start shopping!</a></p></div>");
        } else {
            body.append("<div class=\"card\"><table><thead><tr><th>Order ID</th><th>Amount</th><th>Status</th><th>Tracking</th></tr></thead><tbody>");
            for (Order order : orders) {
                Delivery delivery = deliveries != null ? deliveries.stream().filter(d -> d.getOrderId() == order.getOrderId()).findFirst().orElse(null) : null;
                body.append("<tr><td>#").append(order.getOrderId()).append("</td>");
                body.append("<td>$").append(String.format("%.2f", order.getTotalAmount())).append("</td>");
                body.append("<td><span class=\"status-badge status-active\">").append(order.getStatus()).append("</span></td>");
                body.append("<td>").append(delivery != null ? delivery.getTrackingId() : "N/A").append("</td></tr>");
            }
            body.append("</tbody></table></div>");
        }
        
        return page("My Dashboard", body.toString());
    }

    public static String sellerDashboard(User user, List<Product> products, List<Order> orders) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(user));
        body.append("<div class=\"dashboard-header\"><h2>Seller Dashboard</h2><a class=\"btn btn-primary\" href=\"/product/add\">+ New Listing</a></div>");
        
        body.append("<div class=\"stats-grid\">");
        long availableCount = products.stream().filter(p -> "Available".equals(p.getStatus())).count();
        body.append("<div class=\"stat-card\"><div class=\"stat-number\">").append(products.size()).append("</div><div class=\"stat-label\">Total Listings</div></div>");
        body.append("<div class=\"stat-card\"><div class=\"stat-number\">").append(availableCount).append("</div><div class=\"stat-label\">Available</div></div>");
        body.append("<div class=\"stat-card\"><div class=\"stat-number\">").append(orders.size()).append("</div><div class=\"stat-label\">Orders Received</div></div>");
        body.append("</div>");

        body.append("<h3>Your Listings</h3>");
        if (products.isEmpty()) {
            body.append("<div class=\"card\"><p>No listings yet. <a href=\"/product/add\" style=\"color: #667eea;\">Create one!</a></p></div>");
        } else {
            body.append("<div class=\"grid\">");
            for (Product product : products) {
                body.append(productCardHtml(product, user));
            }
            body.append("</div>");
        }
        
        return page("Seller Dashboard", body.toString());
    }

    public static String adminDashboard(User user, List<User> allUsers, List<Order> allOrders) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(user));
        body.append("<h2>Admin Dashboard</h2>");
        
        body.append("<div class=\"stats-grid\">");
        body.append("<div class=\"stat-card\"><div class=\"stat-number\">").append(allUsers.size()).append("</div><div class=\"stat-label\">Users</div></div>");
        body.append("<div class=\"stat-card\"><div class=\"stat-number\">").append(allOrders.size()).append("</div><div class=\"stat-label\">Total Orders</div></div>");
        body.append("</div>");

        body.append("<h3>Recent Users</h3>");
        body.append("<div class=\"card\"><table><thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Action</th></tr></thead><tbody>");
        for (User u : allUsers) {
            body.append("<tr><td>").append(u.getName()).append("</td><td>").append(u.getEmail()).append("</td><td>").append(u.getRole()).append("</td><td>View</td></tr>");
        }
        body.append("</tbody></table></div>");
        
        return page("Admin Dashboard", body.toString());
    }

    public static String orders(User user, List<Order> orders, List<Delivery> deliveries, List<Payment> payments) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(user));
        body.append("<h2>My Orders</h2>");
        
        if (orders.isEmpty()) {
            body.append("<div class=\"card\"><p>No orders yet.</p></div>");
        } else {
            body.append("<div class=\"card\"><table><thead><tr><th>Order ID</th><th>Amount</th><th>Status</th><th>Tracking</th><th>Payment</th></tr></thead><tbody>");
            for (Order order : orders) {
                Delivery delivery = deliveries != null ? deliveries.stream().filter(d -> d.getOrderId() == order.getOrderId()).findFirst().orElse(null) : null;
                Payment payment = payments != null ? payments.stream().filter(p -> p.getOrderId() == order.getOrderId()).findFirst().orElse(null) : null;
                body.append("<tr><td>#").append(order.getOrderId()).append("</td>");
                body.append("<td>$").append(String.format("%.2f", order.getTotalAmount())).append("</td>");
                body.append("<td><span class=\"status-badge status-active\">").append(order.getStatus()).append("</span></td>");
                body.append("<td>").append(delivery != null ? delivery.getTrackingId() : "N/A").append("</td>");
                body.append("<td><span class=\"status-badge status-" + (payment != null && "Completed".equals(payment.getStatus()) ? "completed" : "pending") + "\">").append(payment != null ? payment.getStatus() : "Pending").append("</span></td></tr>");
            }
            body.append("</tbody></table></div>");
        }
        
        return page("Orders", body.toString());
    }

    public static String messages(User user, List<User> partners) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(user));
        body.append("<div class=\"chat-header\"><div><h2>Messages</h2><p style=\"color:#6b7280;\">Continue your conversations with buyers and sellers.</p></div></div>");
        
        if (partners.isEmpty()) {
            body.append("<div class=\"card\"><p>No conversations yet. Visit a product page and send a message to start chatting.</p></div>");
        } else {
            body.append("<div class=\"card\">");
            for (User partner : partners) {
                body.append("<div class=\"chat-list-card\">");
                body.append("<a class=\"chat-list-link\" href=\"/messages?partner_id=").append(partner.getUserId()).append("\">");
                body.append("<strong>").append(partner.getName()).append("</strong>");
                body.append("<div style=\"color:#6b7280; margin-top: 8px;\">Role: ").append(partner.getRole()).append("</div>");
                body.append("</a>");
                body.append("</div>");
            }
            body.append("</div>");
        }
        
        return page("Messages", body.toString());
    }

    public static String chatThread(User user, User partner, List<Message> thread, int partnerId, int productId, String productTitle, String message) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(user));
        body.append("<div class=\"chat-thread\">");
        body.append("<div class=\"chat-header\">");
        body.append("<div class=\"chat-partner\">Chat with ").append(partner.getName()).append("</div>");
        body.append("<div class=\"chat-toolbar\">");
        body.append("<a class=\"btn btn-secondary\" href=\"/messages\">Back to Chats</a>");
        if (productId > 0) {
            body.append("<a class=\"btn btn-primary\" href=\"/product/" + productId + "\">View Product</a>");
        }
        body.append("</div>");
        body.append("</div>");
        
        if (productId > 0 && productTitle != null && !productTitle.isEmpty()) {
            body.append("<div class=\"chat-product-banner\">Product: <strong>").append(productTitle).append("</strong></div>");
        }
        if (message != null && !message.isEmpty()) {
            body.append("<div class=\"alert alert-success\">" + message + "</div>");
        }
        
        body.append("<div class=\"chat-container\">");
        if (thread.isEmpty()) {
            body.append("<div class=\"card\"><p>No messages yet in this conversation.</p></div>");
        } else {
            for (Message msg : thread) {
                boolean outgoing = msg.getSenderId() == user.getUserId();
                body.append("<div class=\"chat-bubble " + (outgoing ? "outgoing" : "incoming") + "\">");
                body.append(msg.getContent());
                body.append("<div class=\"chat-meta\">" + (outgoing ? "You" : partner.getName()) + " · " + new java.text.SimpleDateFormat("MMM d, hh:mm a").format(msg.getTimestamp()) + "</div>");
                body.append("</div>");
            }
        }
        body.append("</div>");
        
        body.append("<div class=\"chat-input-area\"><form method=\"post\" action=\"/message/send\">");
        body.append("<input type=\"hidden\" name=\"recipient_id\" value=\"").append(partnerId).append("\">");
        if (productId > 0) {
            body.append("<input type=\"hidden\" name=\"product_id\" value=\"").append(productId).append("\">");
        }
        body.append("<textarea name=\"content\" rows=\"4\" placeholder=\"Type a reply...\" style=\"resize: none; width: 100%; padding: 14px; border: 1px solid #d1d5db; border-radius: 12px; margin-top: 10px;\" required></textarea>");
        body.append("<button type=\"submit\" class=\"btn btn-success\" style=\"width: 100%; margin-top: 16px;\">Send Reply</button>");
        body.append("</form></div>");
        
        body.append("</div>");
        return page("Chat with " + partner.getName(), body.toString());
    }

    public static String productDetail(Product product, User user, User seller, String message) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(user));
        if (message != null && !message.isEmpty()) {
            body.append("<div class=\"alert alert-" + (message.contains("Unable") ? "error" : "success") + "\">").append(message).append("</div>");
        }
        body.append("<div class=\"card\">");
        body.append("<div style=\"display: grid; grid-template-columns: 1fr 1fr; gap: 30px;\">");
        
        // Product info
        body.append("<div>");
        body.append("<h2 style=\"color: #1f2937; margin-bottom: 15px;\">").append(product.getTitle()).append("</h2>");
        body.append("<div style=\"font-size: 32px; font-weight: 700; color: #667eea; margin-bottom: 20px;\">$").append(String.format("%.2f", product.getPrice())).append("</div>");
        body.append("<div style=\"margin-bottom: 20px;\"><span class=\"status-badge status-active\">").append(product.getStatus()).append("</span> ");
        body.append("<span class=\"status-badge\" style=\"background: #dbeafe; color: #1e40af;\">").append(product.getCondition()).append("</span></div>");
        body.append("<div style=\"color: #6b7280; margin-bottom: 15px;\"><strong>Category:</strong> ").append(product.getCategory()).append("</div>");
        body.append("<div style=\"color: #6b7280; margin-bottom: 20px; line-height: 1.8;\"><strong>Description:</strong><br>").append(product.getDescription()).append("</div>");
        
        if ("Available".equalsIgnoreCase(product.getStatus())) {
            if (user != null && "Buyer".equals(user.getRole())) {
                body.append("<a class=\"btn btn-success\" href=\"/payment?product_id=").append(product.getProductId()).append("\" style=\"font-size: 16px; padding: 14px 30px;\">Proceed to Checkout</a>");
            } else if (user == null) {
                body.append("<div class=\"alert alert-info\"><a href=\"/login\" style=\"color: #0f4c81; text-decoration: underline;\">Login as Buyer</a> to purchase this item.</div>");
            }
        } else {
            body.append("<div class=\"alert alert-error\">This item is no longer available.</div>");
        }
        body.append("</div>");
        
        // Seller info
        body.append("<div style=\"background: #f9fafb; padding: 20px; border-radius: 12px; height: fit-content;\">");
        body.append("<h3 style=\"margin-bottom: 20px;\">Seller Information</h3>");
        if (seller != null) {
            body.append("<div style=\"margin-bottom: 20px;\">");
            body.append("<div style=\"font-size: 18px; font-weight: 600; color: #1f2937; margin-bottom: 5px;\">").append(seller.getName()).append("</div>");
            body.append("<div style=\"color: #6b7280; margin-bottom: 10px;\">").append(seller.getEmail()).append("</div>");
            body.append("<div style=\"color: #6b7280;\">").append(seller.getPhone()).append("</div>");
            body.append("</div>");
            
            if (user != null && !user.getRole().equals("Seller")) {
                body.append("<a class=\"btn btn-primary\" href=\"/messages?partner_id=").append(seller.getUserId()).append("&product_id=").append(product.getProductId()).append("\" style=\"width: 100%; display: inline-flex; justify-content: center;\">Chat about this product</a>");
            }
        } else {
            body.append("<p>Seller information not available.</p>");
        }
        body.append("</div>");
        
        body.append("</div>");
        body.append("</div>");
        return page(product.getTitle() + " - Product Details", body.toString());
    }

    public static String paymentPage(Product product, User user, String message) {
        StringBuilder body = new StringBuilder();
        body.append(navigation(user));
        if (message != null && !message.isEmpty()) {
            body.append("<div class=\"alert alert-" + (message.contains("Error") ? "error" : "info") + "\">").append(message).append("</div>");
        }
        body.append("<div style=\"display: grid; grid-template-columns: 1fr 1fr; gap: 30px; margin-top: 30px;\">");
        
        // Order summary
        body.append("<div class=\"card\"><h3>Order Summary</h3>");
        body.append("<div style=\"margin: 20px 0; padding: 15px; background: #f9fafb; border-radius: 8px;\">");
        body.append("<div style=\"display: flex; justify-content: space-between; margin-bottom: 10px;\"><span>Product:</span><span>").append(product.getTitle()).append("</span></div>");
        body.append("<div style=\"display: flex; justify-content: space-between; margin-bottom: 10px;\"><span>Price:</span><span>$").append(String.format("%.2f", product.getPrice())).append("</span></div>");
        body.append("<div style=\"display: flex; justify-content: space-between; margin-bottom: 10px;\"><span>Shipping:</span><span>$0.00</span></div>");
        body.append("<div style=\"border-top: 2px solid #e5e7eb; padding-top: 10px; display: flex; justify-content: space-between; font-weight: 700; font-size: 16px;\"><span>Total:</span><span>$").append(String.format("%.2f", product.getPrice())).append("</span></div>");
        body.append("</div></div>");
        
        // Payment form
        body.append("<div class=\"card\"><h3>Payment Details</h3>");
        body.append("<form method=\"post\" action=\"/payment/confirm\">");
        body.append("<input type=\"hidden\" name=\"product_id\" value=\"").append(product.getProductId()).append("\">");
        body.append("<input type=\"hidden\" name=\"amount\" value=\"").append(product.getPrice()).append("\">");
        body.append("<label for=\"method\">Payment Method</label>");
        body.append("<select id=\"method\" name=\"method\" required><option value=\"Credit Card\">Credit Card</option><option value=\"Debit Card\">Debit Card</option><option value=\"UPI\">UPI</option><option value=\"Digital Wallet\">Digital Wallet</option></select>");
        body.append("<label for=\"card\">Card/Account Details</label>");
        body.append("<input id=\"card\" name=\"card\" type=\"text\" placeholder=\"Enter payment details\" required>");
        body.append("<div style=\"background: #e6f4ff; border: 1px solid #91caff; padding: 12px; border-radius: 8px; margin-top: 15px; font-size: 14px;\">");
        body.append("✓ Secure payment gateway<br>✓ Your information is encrypted<br>✓ No additional charges");
        body.append("</div>");
        body.append("<button type=\"submit\" class=\"btn btn-success\" style=\"width: 100%; margin-top: 20px; padding: 14px; font-size: 16px;\">Complete Payment</button>");
        body.append("</form></div>");
        
        body.append("</div>");
        return page("Checkout", body.toString());
    }
}