package com.example.jakobwilbrandt.chatt;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jakobwilbrandt.chatt.Adapters.RoomAdapter;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;
import com.example.jakobwilbrandt.chatt.DataClasses.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseServiceActivity {


    private List<IRoom> Rooms = new ArrayList();
    private RecyclerView recyclerView;
    //creating recyclerview adapter
    RoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.room_rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));







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
                IRoom room = new Room("Loove room");
                Rooms.add(room);


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
                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);

                mBound = true;

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








}
