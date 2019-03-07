package com.example.jakobwilbrandt.chatt.DataClasses;

import java.util.ArrayList;
/**
 * Created by Jakob Wilbrandt.
 * Using interfaces for decoupling
 */
public interface IRoom {


    String getName();

    void setName(String name);

    String getId();

    void setId(String id);

    ArrayList<IMessage> getMessages();

    void setMessages(ArrayList<IMessage> messages);

    void addMessage(IMessage message);

    void setAmountOfMsg(Integer amountOfMsg);

    Integer getAmountOfMsg();

    String getDescription();

    void setDescription(String description);

}
