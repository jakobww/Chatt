package com.example.jakobwilbrandt.chatt.DataClasses;

public class Message implements IMessage {


    private String sender;
    private String messageContent;
    private String timeOfMessage;

    public Message(String sender, String messageContent, String timeOfMessage) {
        this.sender = sender;
        this.messageContent = messageContent;
        this.timeOfMessage = timeOfMessage;
    }

    @Override
    public String getSenderId() {
        return sender;
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
