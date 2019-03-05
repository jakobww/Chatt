package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

import android.content.Context;

import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;

import java.util.ArrayList;

public interface IRoomRTDB {

    ArrayList<IRoom> getUpdatedRooms();
    void startListening();
    void addMessageToRoom(IRoom room, IMessage message);
    void stopListening();
    void setContext(Context context);
    void addRoom(IRoom room);


}
