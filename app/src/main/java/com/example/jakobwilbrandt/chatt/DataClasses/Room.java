package com.example.jakobwilbrandt.chatt.DataClasses;

import java.util.ArrayList;
import java.util.UUID;

public class Room implements IRoom {


    private String name = "Default Room";
    private String roomDesc = "Standard room";
    private String roomId;
    private ArrayList<IMessage> messages;
    private Integer amountOfMsg = 0;

    public Room() {
        // Creating a random UUID (Universally unique identifier).
        UUID uuid = UUID.randomUUID();
        this.roomId = uuid.toString();
        messages = new ArrayList<>();
    }

    public Room(String name, String desc) {
        // Creating a random UUID (Universally unique identifier).
        this.roomDesc = desc;
        this.name = name;
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
    public void setAmountOfMsg(Integer amountOfMsg) {
        this.amountOfMsg = amountOfMsg;
    }

    @Override
    public Integer getAmountOfMsg() {
        return amountOfMsg;
    }

    @Override
    public String getDescription() {
        return roomDesc;
    }

    @Override
    public void setDescription(String description) {
        this.roomDesc = description;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


}
