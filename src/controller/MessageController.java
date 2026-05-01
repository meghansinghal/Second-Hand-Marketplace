package controller;

import dao.MessageDAO;
import model.Message;

import java.util.List;

public class MessageController {

    private final MessageDAO messageDAO = new MessageDAO();

    public int sendMessage(Message message) {
        return messageDAO.sendMessage(message);
    }

    public List<Message> getInbox(int userId) {
        return messageDAO.findInbox(userId);
    }

    public List<Integer> getConversationPartners(int userId) {
        return messageDAO.findConversationPartners(userId);
    }

    public List<Message> getConversation(int userId, int partnerId) {
        return messageDAO.findConversation(userId, partnerId);
    }

    public boolean markConversationAsRead(int partnerId, int userId) {
        return messageDAO.markConversationAsRead(partnerId, userId);
    }

    public boolean markAsRead(int messageId) {
        return messageDAO.markAsRead(messageId);
    }
}