package com.example.jakobwilbrandt.chatt;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;
import com.example.jakobwilbrandt.chatt.serverFactory.IRoomRTDB;
/**
 * Created by Jakob Wilbrandt.
 */
import java.util.ArrayList;

public class ChatService extends Service {

    String TAG = "ChatService";
    private final int NEW_MESSAGE = 0;
    private final IBinder mBinder = new BoundBinder();
    private ArrayList<IRoom> Rooms = new ArrayList<>();
    private ArrayList<IMessage> Messages = new ArrayList<>();
    private IRoomRTDB roomRTDB;
    private Notification notification;
    private NotificationCompat.Builder mBuilder;
    public ArrayList<IRoom> getRooms() {
        return Rooms;
    }

    public ArrayList<IMessage> getMessages(IRoom room) {
        if(room!=null){
        return roomRTDB.getMessagesFromRoom(room);}
        else{return null;}
    }

    public IRoomRTDB getRoomRTDB() {
        return roomRTDB;
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
    public void onCreate() {
        super.onCreate();

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

    public void showNotification(String message, String title) {


        Resources r = getResources();
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }


    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "broadCastIntent" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //When data is received the messages list is updated using observer pattern and the activities are notified
            Rooms = roomRTDB.getUpdatedRooms();
            Intent intent2 = new Intent("NEW_DATA_FOR_ACTIVITIES");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
            showNotification(getString(com.example.jakobwilbrandt.chatt.R.string.new_data),getString(com.example.jakobwilbrandt.chatt.R.string.app_name));

        }
    };









}
