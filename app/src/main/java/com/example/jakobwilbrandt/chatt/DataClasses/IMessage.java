package com.example.jakobwilbrandt.chatt.DataClasses;

import java.sql.Time;

public interface IMessage {

    String getSenderId();

    String getMessageContent();

    String getMessageId();

    String getTimeOfMessage();

}
