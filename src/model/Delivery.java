package model;

import java.util.Date;

public class Delivery {

    private int deliveryId;
    private int orderId;
    private String status;
    private Date pickupDate;
    private Date deliveryDate;
    private String deliveryPartner;
    private String trackingId;

    public Delivery(int orderId) {
        this.orderId = orderId;
        this.status = "Pending";
        this.pickupDate = new Date();
        this.trackingId = "TRACK" + System.currentTimeMillis();
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public String getDeliveryStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeliveryStatus(String status) {
        this.status = status;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPartner() {
        return deliveryPartner;
    }

    public void setDeliveryPartner(String deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }

    public String getTrackingId() {
        return trackingId;
    }
}