package com.example.jakobwilbrandt.chatt.DataClasses;

import java.util.ArrayList;

public interface IRoom {

    String getName();

    String getId();

    ArrayList<IMessage> getMessages();

    void addMessage(IMessage message);

}
