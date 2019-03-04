package com.example.jakobwilbrandt.chatt;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;

import java.util.ArrayList;

public class ChatService extends Service {

    String TAG = "ChatService";
    private final int NEW_MESSAGE = 0;
    private final IBinder mBinder = new BoundBinder();
    private ArrayList<IRoom> Rooms = new ArrayList<>();
    private ArrayList<IMessage> Messages = new ArrayList<>();

    public ArrayList<IRoom> getRooms() {
        return Rooms;
    }

    public ArrayList<IMessage> getMessages(IRoom room) {
        return room.getMessages();
    }



    public ChatService() {
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

        return super.onStartCommand(intent, flags, startId);
    }

    private void broadCastMessage(int ACTION_CODE,String message) {
        Intent intent = new Intent(getString(R.string.broadcast_intent));
        switch (ACTION_CODE) {


            case NEW_MESSAGE:
                intent.putExtra(getString(R.string.message_key), NEW_MESSAGE);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                break;
            default:
                break;
        }
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






}
