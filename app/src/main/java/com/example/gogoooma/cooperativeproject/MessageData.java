package com.example.gogoooma.cooperativeproject;

public class MessageData {
    String m_sender;
    String m_receiver;
    String m_message;

    public MessageData(String sender, String receiver, String message) {
        m_sender = sender;
        m_receiver = receiver;
        m_message = message;
    }

    public String getSender() {
        return m_sender;
    }

    public String getReceiver() {
        return m_receiver;
    }

    public String getMessage() {
        return m_message;
    }
}