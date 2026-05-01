package model;

import java.util.Date;

public class Message {

    private int messageId;
    private int senderId;
    private int recipientId;
    private String content;
    private Date timestamp;
    private boolean isRead;

    public Message(int senderId, int recipientId, String content) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.timestamp = new Date();
        this.isRead = false;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}