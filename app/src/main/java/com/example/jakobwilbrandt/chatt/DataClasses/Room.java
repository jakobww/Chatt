package com.example.jakobwilbrandt.chatt.DataClasses;

import java.util.ArrayList;
import java.util.UUID;

public class Room implements IRoom {


    private String name = "Default Room";
    private String roomId;
    private ArrayList<IMessage> messages;

    public Room() {
        // Creating a random UUID (Universally unique identifier).
        UUID uuid = UUID.randomUUID();
        this.roomId = uuid.toString();
        messages = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return roomId;
    }

    @Override
    public void setId(String id) {
        this.roomId = id;
    }

    @Override
    public ArrayList<IMessage> getMessages() {
        return messages;
    }

    @Override
    public void setMessages(ArrayList<IMessage> messages) {
        this.messages = messages;
    }

    @Override
    public void addMessage(IMessage message) {
        messages.add(message);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


}
