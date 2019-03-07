package com.example.jakobwilbrandt.chatt.serverFactory;

import android.content.Context;
import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;

import java.util.ArrayList;
/**
 * Created by Jakob Wilbrandt.
 * This interface is part of the serverfactory, which decouples the implementation of the realtime database from the rest of the code.
 * By doing so, it's possible to implement another realtime database more easily.
 */
public interface IRoomRTDB {


    ArrayList<IRoom> getUpdatedRooms();
    void startListening();
    void addMessageToRoom(IRoom room, IMessage message);
    void stopListening();
    void setContext(Context context);
    ArrayList<IMessage> getMessagesFromRoom(IRoom room);
    void addRoom(IRoom room);
    IRoom getRoomWithAllMessages(IRoom room);
    void setAmountOfMessagesToHold(int amountOfMessagesToHold);
    int getAmountOfMessagesToHold();
    ArrayList<IMessage> getRangeOfMessagesFromBottom(IRoom tempRoom, int amountFromBottom);


}
