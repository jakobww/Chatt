package com.example.jakobwilbrandt.chatt;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jakobwilbrandt.chatt.Adapters.MessageAdapter;
import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;
import com.example.jakobwilbrandt.chatt.DataClasses.Message;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by Jakob Wilbrandt.
 */
public class ChatActivity extends BaseServiceActivity {


    private ArrayList<IMessage> messages = new ArrayList();
    private RecyclerView recyclerView;
    private IRoom currentRoom;
    private int position = 0;
    private MessageAdapter adapter;
    private TextView title;
    //For checking if the recycler view is loading new messages
    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Initializing broadcast receiver in order to communicate with the chat service
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("NEW_DATA_FOR_ACTIVITIES"));

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.message_rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Connecting views with xml
        title = findViewById(R.id.title_messages_activity);
        ImageView btn = findViewById(R.id.send_button);
        final EditText inputTxt = findViewById(R.id.input_message);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the input message and add it to database
                if(!inputTxt.getText().toString().equals("")) {
                    String message = inputTxt.getText().toString();
                    inputTxt.setText("");
                    Date date = new Date();
                    String strDateFormat = "hh:mm:ss yyyy/MM/dd";
                    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                    String formattedDate= dateFormat.format(date);
                    IMessage msg = new Message(userHandling.getCurrentUserId(), message, formattedDate);
                    roomRTDB.addMessageToRoom(currentRoom, msg);
                }
                else {
                    //If the user did not enter anything
                    Toast.makeText(getApplicationContext(), R.string.please_enter_message,Toast.LENGTH_LONG).show();
                }
            }
        });

        //For listening to top scroll
        initScrollListener();

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

                //Getting the index of room pressed
                position = getIntent().getExtras().getInt("roomIndex");


                if(chatService.getRooms().size() > 0) {
                    //Getting the current room
                    currentRoom = chatService.getRooms().get(position);
                    title.setText(currentRoom.getName());
                }
                else{
                    finish();
                }

                messages = chatService.getMessages(currentRoom);

                //creating recyclerview adapter
                adapter = new MessageAdapter(getApplicationContext(), messages);

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(adapter.getItemCount()-1);


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

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "broadCastIntent" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //New data has arrived in service and the activityis notified
            messages = chatService.getMessages(currentRoom);
            adapter.refreshMessages(messages);
            recyclerView.scrollToPosition(adapter.getItemCount()-1);

        }
    };



    //For listening for top scrolling
    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                        //top of list!
                        loadMore();
                        isLoading = true;

                    }
                }
            }
        });


    }

    //Loading more elements into list
    private void loadMore() {
        //messages.add(0,null);
        //adapter.notifyItemInserted(0);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                int amountOfMessagesToHold = roomRTDB.getAmountOfMessagesToHold();
                roomRTDB.setAmountOfMessagesToHold(amountOfMessagesToHold + 10);
                IRoom room = chatService.getRoomRTDB().getRoomWithAllMessages(currentRoom);
                messages = chatService.getRoomRTDB().getRangeOfMessagesFromBottom(room,amountOfMessagesToHold + 10);
                adapter.refreshMessages(messages);
                isLoading = false;

            }
        }, 700);


    }

}
