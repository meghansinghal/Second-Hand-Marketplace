package model;

import java.util.Date;

public class Delivery {

    private int orderId;
    private String deliveryStatus;
    private Date pickupDate;
    private Date deliveryDate;

    public Delivery(int orderId) {
        this.orderId = orderId;
        this.deliveryStatus = "Pending";
    }

    public int getOrderId() { return orderId; }
    public String getDeliveryStatus() { return deliveryStatus; }

    public void setDeliveryStatus(String status) {
        this.deliveryStatus = status;
    }
}