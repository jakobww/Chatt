package com.example.jakobwilbrandt.chatt.DataClasses;

public class Message implements IMessage {


    private String senderId;
    private String messageContent;
    private String timeOfMessage;


    public Message(String senderId, String messageContent, String timeOfMessage) {
        this.senderId = senderId;
        this.messageContent = messageContent;
        this.timeOfMessage = timeOfMessage;
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
    public String getTimeOfMessage() {
        return timeOfMessage;
    }


}
