package model;

import java.util.Date;

public class Order {

    private int orderId;
    private int buyerId;
    private int productId;
    private double totalAmount;
    private String status;
    private Date orderDate;

    public Order(int buyerId, int productId, double totalAmount) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.totalAmount = totalAmount;
        this.status = "Placed";
        this.orderDate = new Date();
    }

    public int getBuyerId() { return buyerId; }
    public int getProductId() { return productId; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public Date getOrderDate() { return orderDate; }
}