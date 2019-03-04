package com.example.jakobwilbrandt.chatt;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jakobwilbrandt.chatt.Adapters.MessageAdapter;
import com.example.jakobwilbrandt.chatt.Adapters.RoomAdapter;
import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;
import com.example.jakobwilbrandt.chatt.DataClasses.Message;
import com.example.jakobwilbrandt.chatt.DataClasses.Room;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseServiceActivity {

    private List<IMessage> messages = new ArrayList();
    private RecyclerView recyclerView;
    private IRoom currentRoom;
    private int position = 0;

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


        position = getIntent().getExtras().getInt("roomIndex");

        if(chatService.getRooms().size() != 0){
        currentRoom = chatService.getRooms().get(position);}

    }



    //This happens when the connection has been made.
    //Thus we are sure that the Rooms has been initialized.
    private void connectToService(){
        chatServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                ChatService.BoundBinder binder = (ChatService.BoundBinder) service;
                chatService = binder.getService();


                messages = chatService.getMessages(currentRoom);

                IMessage message = new Message(serverFactory.CreateUserHandler().getCurrentUserId(),"hej med dig hvordan går det her går det godt!!!!!","22");
                serverFactory.CreateRoomRTDB().addMessageToRoom(currentRoom,message);

                //creating recyclerview adapter
                MessageAdapter adapter = new MessageAdapter(getApplicationContext(), messages);

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
