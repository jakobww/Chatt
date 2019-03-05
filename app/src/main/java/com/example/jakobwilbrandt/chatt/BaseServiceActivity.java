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

        //Retrieve the current state of the service if saveInstanceState has been called
        if(savedInstanceState != null){
            mBound = savedInstanceState.getBoolean(getString(R.string.BOUND_STATE));
        }



    }



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
