package com.example.jakobwilbrandt.chatt;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.jakobwilbrandt.chatt.Adapters.RoomAdapter;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;
import com.example.jakobwilbrandt.chatt.DataClasses.Message;
import com.example.jakobwilbrandt.chatt.DataClasses.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseServiceActivity {


    private ArrayList<IRoom> Rooms = new ArrayList();
    private RecyclerView recyclerView;
    //creating recyclerview adapter
    private RoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.room_rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                int position = viewHolder.getAdapterPosition();

                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("roomIndex", position);
                startActivity(intent);

            }
        };


        adapter = new RoomAdapter(getApplicationContext(), Rooms, clickListener);



        //Initializing broadcast receiver in order to communicate with the chat service
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("NEW_DATA_FOR_ACTIVITIES"));


        //Starting the Chat service, to run in the background.
        //Making sure the service is started: If it is, it will not be started twice. Android does not allow this
        Intent serviceIntent = new Intent(this, ChatService.class);
        startService(serviceIntent);

        //Initiating connection to the service and ready to bind service
        connectToService();



    }




    //This happens when the connection has been made.
    //Thus we are sure that the Rooms has been initialized.
    private void connectToService(){
        chatServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {

                ChatService.BoundBinder binder = (ChatService.BoundBinder) service;
                chatService = binder.getService();

                Rooms = chatService.getRooms();

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);

                chatService.setRoomRTDB(roomRTDB);
                chatService.startListening();
                mBound = true;


                /*Room room1;
                Message message;
                room1 = new Room();
                message = new Message("jakob","jhasdsjnf hejsa","22:30");
                room1.addMessage(message);
                room1.addMessage(message);
                room1.addMessage(message);
                roomRTDB.addRoom(room1);room1 = new Room();
                message = new Message("jakob","jhasdsjnf hejsa","22:30");
                room1.addMessage(message);
                room1.addMessage(message);
                room1.addMessage(message);
                roomRTDB.addRoom(room1);room1 = new Room();
                message = new Message("jakob","jhasdsjnf hejsa","22:30");
                room1.addMessage(message);
                room1.addMessage(message);
                room1.addMessage(message);
                roomRTDB.addRoom(room1);room1 = new Room();
                message = new Message("jakob","jhasdsjnf hejsa","22:30");
                room1.addMessage(message);
                room1.addMessage(message);
                room1.addMessage(message);
                roomRTDB.addRoom(room1);*/



            }

            public void onServiceDisconnected(ComponentName className) {
                // This is called when the connection with the service has been
                // unexpectedly disconnected -- that is, its process crashed.
                // Because it is running in our same process, we should never
                // see this happen.
                //ref: http://developer.android.com/reference/android/app/Service.html
                mBound = false;
                chatService = null;
                Log.d(TAG, getString(R.string.stop_bound_service));
            }
        };
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "broadCastIntent" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Rooms = chatService.getRooms();

            adapter.refreshRooms(Rooms);

        }
    };








}
