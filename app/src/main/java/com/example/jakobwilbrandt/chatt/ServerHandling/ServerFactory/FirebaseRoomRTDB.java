package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

import android.util.Log;

import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseRoomRTDB implements IRoomRTDB {

    String TAG = "FirebaseRoomRTDB";
    ArrayList<IRoom> Rooms;

    public FirebaseRoomRTDB() {
        Rooms = new ArrayList<>();
    }

    @Override
    public ArrayList<IRoom> getUpdatedRooms() {
        return Rooms;
    }

    public void startListening(){
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rooms");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<IRoom>> t = new GenericTypeIndicator<ArrayList<IRoom>>() {};
                Rooms = dataSnapshot.getValue(t);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "The read failed: " + databaseError.getCode());
            }
        });
    }

    public void addMessageToRoom(IRoom room, IMessage message){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rooms");
        ref.child("rooms").child(room.getId()).setValue(message);
    }

}
