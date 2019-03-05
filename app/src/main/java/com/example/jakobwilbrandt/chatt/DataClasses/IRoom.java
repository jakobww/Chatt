package com.example.jakobwilbrandt.chatt.DataClasses;

import java.util.ArrayList;

public interface IRoom {


    String getName();

    void setName(String name);

    String getId();

    void setId(String id);

    ArrayList<IMessage> getMessages();

    void setMessages(ArrayList<IMessage> messages);

    void addMessage(IMessage message);

}
