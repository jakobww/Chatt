package com.example.jakobwilbrandt.chatt.DataClasses;
/**
 * Created by Jakob Wilbrandt.
 * Using interfaces for decoupling
 */

public interface IMessage {

    String getSenderId();

    String getMessageContent();

    String getMessageId();

    String getTimeOfMessage();

}
