package com.example.jakobwilbrandt.chatt.DataClasses;

public class Room implements IRoom {


    private String name;

    public Room(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
