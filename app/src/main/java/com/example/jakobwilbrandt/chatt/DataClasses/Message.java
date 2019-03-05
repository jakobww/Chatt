package com.example.jakobwilbrandt.chatt.DataClasses;

import java.util.UUID;

public class Message implements IMessage {


    private String senderId;
    private String messageContent;
    private String timeOfMessage;
    private String messageId;


    public Message(){

    }

    public Message(String senderId, String messageContent, String timeOfMessage) {
        this.senderId = senderId;
        this.messageContent = messageContent;
        this.timeOfMessage = timeOfMessage;
        // Creating a random UUID (Universally unique identifier).
        UUID uuid = UUID.randomUUID();
        this.messageId = uuid.toString();
    }

    @Override
    public String getSenderId() {
        return senderId;
    }



    @Override
    public String getMessageContent() {
        return messageContent;
    }

    @Override
    public String getMessageId() {
        return messageId;
    }

    @Override
    public String getTimeOfMessage() {
        return timeOfMessage;
    }


}
