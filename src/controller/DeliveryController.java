package controller;

import dao.DeliveryDAO;
import model.Delivery;

public class DeliveryController {

    private final DeliveryDAO deliveryDAO = new DeliveryDAO();

    public int createDelivery(Delivery delivery) {
        return deliveryDAO.createDelivery(delivery);
    }

    public Delivery getDeliveryByOrder(int orderId) {
        return deliveryDAO.findByOrderId(orderId);
    }

    public boolean updateDeliveryStatus(int deliveryId, String status) {
        return deliveryDAO.updateDeliveryStatus(deliveryId, status);
    }

    public boolean assignDeliveryPartner(int deliveryId, String partnerName) {
        return deliveryDAO.assignDeliveryPartner(deliveryId, partnerName);
    }

    public boolean markDelivered(int deliveryId) {
        return deliveryDAO.markDelivered(deliveryId);
    }
}