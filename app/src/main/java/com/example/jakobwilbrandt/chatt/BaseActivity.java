package com.example.jakobwilbrandt.chatt;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jakobwilbrandt.chatt.NetworkMonitor.NetworkChangeReceiver;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.IServerFactory;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.ServerProducer;

public class BaseActivity extends AppCompatActivity {

    String TAG = "BaseActivity";
    protected NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    protected IServerFactory serverFactory = ServerProducer.getFactory("firebase");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkChangeReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (networkChangeReceiver!=null) {
            unregisterReceiver(networkChangeReceiver);
            networkChangeReceiver=null;
        }

    }
}
