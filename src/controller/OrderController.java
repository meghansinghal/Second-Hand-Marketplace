package controller;

import dao.OrderDAO;
import model.Order;

import java.util.List;

public class OrderController {

    private final OrderDAO orderDAO = new OrderDAO();

    public int placeOrder(Order order) {
        return orderDAO.createOrder(order);
    }

    public List<Order> getOrdersByBuyer(int buyerId) {
        return orderDAO.findOrdersByBuyer(buyerId);
    }

    public List<Order> getOrdersBySeller(int sellerId) {
        return orderDAO.findOrdersBySeller(sellerId);
    }

    public List<Order> getAllOrders() {
        return orderDAO.findAllOrders();
    }

    public boolean cancelOrder(int orderId) {
        return orderDAO.cancelOrder(orderId);
    }
}