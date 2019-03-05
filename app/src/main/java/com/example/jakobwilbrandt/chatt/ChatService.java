package com.example.jakobwilbrandt.chatt;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.IRoomRTDB;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.IServerFactory;

import java.util.ArrayList;

public class ChatService extends Service {

    String TAG = "ChatService";
    private final int NEW_MESSAGE = 0;
    private final IBinder mBinder = new BoundBinder();
    private ArrayList<IRoom> Rooms = new ArrayList<>();
    private ArrayList<IMessage> Messages = new ArrayList<>();
    private IRoomRTDB roomRTDB;

    public ArrayList<IRoom> getRooms() {
        return Rooms;
    }

    public ArrayList<IMessage> getMessages(IRoom room) {
        if(room!=null){
        return roomRTDB.getMessagesFromRoom(room);}
        else{return null;}
    }



    public ChatService() {
    }

    public void setRoomRTDB(IRoomRTDB roomRTDB) {
        this.roomRTDB = roomRTDB;
    }

    //Function used to bind with the activities.
    public class BoundBinder extends Binder {
        ChatService getService() {
            // Return this instance of ChatService so clients can call public methods
            return ChatService.this;
        }
    }

    //When bound...
    @Override
    public IBinder onBind(Intent intent) {

        Log.d(TAG,getString(R.string.start_bound_service));
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //Initializing broadcast receiver in order to communicate with the chat service
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("NEW_DATA_FOR_SERVICE"));
        return super.onStartCommand(intent, flags, startId);
    }


    public void startListening(){
        roomRTDB.startListening();
    }

    @Override
    public boolean stopService(Intent name) {
        roomRTDB.stopListening();
        return super.stopService(name);
    }

    private void showNotification(String newMessage)
    {
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle(getString(R.string.app_name))
                .setContentText(newMessage)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        startForeground(1, notification);
    }


    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "broadCastIntent" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Rooms = roomRTDB.getUpdatedRooms();

            Intent intent2 = new Intent("NEW_DATA_FOR_ACTIVITIES");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);

        }
    };









}
