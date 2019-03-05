package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

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
import java.util.Arrays;
import java.util.HashMap;

import flexjson.JSONDeserializer;

public class FirebaseRoomRTDB implements IRoomRTDB {

    private String TAG = "FirebaseRoomRTDB";
    private ArrayList<IRoom> Rooms;
    private ValueEventListener listener;
    private DatabaseReference ref;
    private Context context;

    public FirebaseRoomRTDB() {
        Rooms = new ArrayList<>();

        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("rooms");

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {

                    for (DataSnapshot newSnap : dataSnapshot.getChildren()) {

                        IRoom tempRoom = new Room();
                        IMessage tempMessage = new Message();
                        String name = (String) newSnap.child("name").getValue();
                        String roomId = (String) newSnap.child("id").getValue();
                        tempRoom.setName(name);
                        tempRoom.setId(roomId);

                        DataSnapshot newSnap2 = newSnap.child("messages");

                        for (DataSnapshot newSnap3 : newSnap2.getChildren()) {

                            tempMessage = newSnap3.getValue(Message.class);

                            tempRoom.addMessage(tempMessage);

                        }

                        boolean isRoomInList = false;
                        for(int i = 0; i < Rooms.size(); i++) {

                            if(Rooms.get(i).getId().equals(tempRoom.getId())){
                                isRoomInList = true;
                            }

                        }
                        if(!isRoomInList){
                        Rooms.add(tempRoom);}

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
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rooms");
        ref.child(room.getId()).child("messages").setValue(message);
    }

    @Override
    public void addRoom(IRoom room){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rooms");
        ref.child(room.getId()).setValue(room);
    }



}
