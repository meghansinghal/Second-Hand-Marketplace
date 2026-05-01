package controller;

import dao.PaymentDAO;
import model.Payment;

public class PaymentController {

    private final PaymentDAO paymentDAO = new PaymentDAO();

    public int processPayment(Payment payment) {
        return paymentDAO.createPayment(payment);
    }

    public Payment getPaymentByOrder(int orderId) {
        return paymentDAO.findByOrderId(orderId);
    }

    public boolean confirmPayment(int paymentId) {
        return paymentDAO.updatePaymentStatus(paymentId, "Completed");
    }

    public boolean initiateRefund(int paymentId) {
        return paymentDAO.updatePaymentStatus(paymentId, "Refunded");
    }
}
