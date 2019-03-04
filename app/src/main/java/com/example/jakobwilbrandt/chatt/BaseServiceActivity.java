package com.example.jakobwilbrandt.chatt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class BaseServiceActivity extends BaseActivity {

    protected ServiceConnection chatServiceConnection;
    protected ChatService chatService;
    protected boolean mBound = false;
    private final int NEW_MESSAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing broadcast receiver in order to communicate with the chat service
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(getString(R.string.broadcast_intent)));

        //Retrieve the current state of the service if saveInstanceState has been called
        if(savedInstanceState != null){
            mBound = savedInstanceState.getBoolean(getString(R.string.BOUND_STATE));
        }



    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "broadCastIntent" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Find out what the message was about.
            int message = intent.getIntExtra(getString(R.string.message_key),0);
            switch(message){

                /*case NEW_MESSAGE:
                    //TODO: Handle new message - shall be moved to sub class
                    break;
                default:
                    break;*/
            }
            Log.d(TAG, getString(R.string.got_message) + message);
        }
    };

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mBound = savedInstanceState.getBoolean(getString(R.string.BOUND_STATE));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putBoolean(getString(R.string.BOUND_STATE),mBound);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onResume() {

        //When returning the OnCreate is not called, hence we want to bind onResume.
        if (mBound == false) {
            Intent intent = new Intent(this, ChatService.class);
            bindService(intent, chatServiceConnection, Context.BIND_AUTO_CREATE);
        }

        super.onResume();
    }



    @Override
    protected void onPause() {
        if(mBound == true){
            //If activity is paused, we unbind from the service
            unbindService(chatServiceConnection);
            mBound = false;}
        super.onPause();
    }
}
