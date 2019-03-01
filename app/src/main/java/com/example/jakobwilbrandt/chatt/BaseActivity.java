package com.example.jakobwilbrandt.chatt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Network;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jakobwilbrandt.chatt.NetworkMonitor.NetworkChangeReceiver;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.ILoginHandler;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.IServerFactory;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.ServerProducer;

public class BaseActivity extends AppCompatActivity {

    String TAG = "BaseActivity";
    protected NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new NetworkChangeReceiver(), filter);


        IServerFactory serverFactory = ServerProducer.getFactory("firebase");
        ILoginHandler loginHandler = serverFactory.CreateLoginHandler();
        boolean isLoggedIn = loginHandler.CheckIfLoggedIn();
        if(isLoggedIn){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();


        }
        else{
            Toast.makeText(this,"User is not logged in", Toast.LENGTH_LONG).show();
        }

    }


}
