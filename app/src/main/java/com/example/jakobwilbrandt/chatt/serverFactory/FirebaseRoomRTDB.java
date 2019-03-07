package com.example.jakobwilbrandt.chatt.serverFactory;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;
import com.example.jakobwilbrandt.chatt.DataClasses.Message;
import com.example.jakobwilbrandt.chatt.DataClasses.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseRoomRTDB implements IRoomRTDB {

    private String TAG = "FirebaseRoomRTDB";
    private ArrayList<IRoom> Rooms;
    private ArrayList<IRoom> RoomsWithAllMessages;
    private ArrayList<Integer> lastMessageCounts;
    private ValueEventListener listener;
    private DatabaseReference ref;
    private Context context;
    Integer totalMsgNumber = 0;
    private int amountOfMessagesToHold = 50;

    public FirebaseRoomRTDB() {
        Rooms = new ArrayList<>();
        RoomsWithAllMessages = new ArrayList<>();

        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("rooms");

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Rooms.clear();
                RoomsWithAllMessages.clear();

                if (dataSnapshot != null) {

                    for (DataSnapshot newSnap : dataSnapshot.getChildren()) {
                        totalMsgNumber = 0;
                        IRoom tempRoom = new Room();
                        IRoom tempRoom2 = new Room();
                        IMessage tempMessage = new Message();
                        String name = (String) newSnap.child("name").getValue();
                        String desc = (String) newSnap.child("description").getValue();
                        String roomId = (String) newSnap.child("id").getValue();
                        tempRoom.setName(name);
                        tempRoom.setDescription(desc);
                        tempRoom.setId(roomId);
                        tempRoom2.setName(name);
                        tempRoom2.setDescription(desc);
                        tempRoom2.setId(roomId);


                        DataSnapshot newSnap2 = newSnap.child("messages");

                        for (DataSnapshot newSnap3 : newSnap2.getChildren()) {


                            tempMessage = newSnap3.getValue(Message.class);

                            tempRoom.addMessage(tempMessage);
                            tempRoom2.addMessage(tempMessage);
                            totalMsgNumber++;
                        }

                        RoomsWithAllMessages.add(tempRoom2);

                        if(tempRoom.getMessages().size() > amountOfMessagesToHold){
                            ArrayList<IMessage> tempList = getRangeOfMessagesFromBottom(tempRoom,amountOfMessagesToHold);
                            tempRoom.setMessages(tempList);
                        }

                        tempRoom.setAmountOfMsg(totalMsgNumber);

                        Rooms.add(tempRoom);

                    }

                }


                Intent intent = new Intent("NEW_DATA_FOR_SERVICE");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "The read failed: " + databaseError.getCode());
            }

        };

    }



    @Override
    public ArrayList<IRoom> getUpdatedRooms() {
        return Rooms;
    }

    @Override
    public IRoom getRoomWithAllMessages(IRoom room){
        String roomId = room.getId();
        for(int i = 0; i < RoomsWithAllMessages.size();i++){
            if(roomId.equals(RoomsWithAllMessages.get(i).getId())){
                return RoomsWithAllMessages.get(i);
            }
        }
        return null;
    }

    public void startListening(){


// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(listener);


    }

    public void stopListening(){
        ref.removeEventListener(listener);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void addMessageToRoom(IRoom room, IMessage message){

        int nextMessage = room.getAmountOfMsg();
        nextMessage++;
        room.setAmountOfMsg(nextMessage);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rooms");
        ref.child(room.getId()).child("messages").child(Integer.toString(nextMessage)).setValue(message);
    }

    @Override
    public ArrayList<IMessage> getMessagesFromRoom(IRoom room){
        String roomId = room.getId();
        for(int i = 0; i < Rooms.size();i++){
            if(roomId.equals(Rooms.get(i).getId())){
                return Rooms.get(i).getMessages();
            }
        }
        return null;
    }


    @Override
    public void addRoom(IRoom room){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rooms");
        ref.child(room.getId()).setValue(room);
    }

    @Override
    public int getAmountOfMessagesToHold(){
        return amountOfMessagesToHold;
    }

    @Override
    public void setAmountOfMessagesToHold(int amountOfMessagesToHold){
        this.amountOfMessagesToHold = amountOfMessagesToHold;
    }

    @Override
    public ArrayList<IMessage> getRangeOfMessagesFromBottom(IRoom tempRoom, int amountFromBottom){

        int counter = amountFromBottom;
        if(amountFromBottom > tempRoom.getMessages().size())
        {
            amountFromBottom = tempRoom.getMessages().size();
        }
        int i = 0;
        ArrayList<IMessage> tempList = new ArrayList<>();
        while(counter > 0 && i < tempRoom.getMessages().size()){
            tempList.add(tempRoom.getMessages().get(tempRoom.getMessages().size() - amountFromBottom + i));
            counter--;
            i++;
        }

        return tempList;

    }



}
