package com.example.jakobwilbrandt.chatt.DataClasses;

import java.util.UUID;
/**
 * Created by Jakob Wilbrandt.
 * Since a message can be sent to any given room, the messages has no receiver id. This allows for further decoupling of the code.
 * For example it would be possible, at a later stage, to send messages to many rooms at the same time, only identifying the message using the message id.
 */
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
