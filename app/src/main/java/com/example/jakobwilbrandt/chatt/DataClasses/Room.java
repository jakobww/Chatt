package com.example.jakobwilbrandt.chatt.DataClasses;

import java.util.ArrayList;

public class Room implements IRoom {


    private String name;
    private ArrayList<IMessage> messages;

    public Room(String name) {
        this.name = name;
        messages = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public ArrayList<IMessage> getMessages() {
        return messages;
    }

    @Override
    public void addMessage(IMessage message) {
        messages.add(message);
    }


    public void setName(String name) {
        this.name = name;
    }
}
