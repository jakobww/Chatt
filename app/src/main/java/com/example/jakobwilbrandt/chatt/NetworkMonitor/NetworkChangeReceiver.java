package com.example.jakobwilbrandt.chatt.NetworkMonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public static boolean getIsConnected() {
        return isConnected;
    }

    public static boolean isConnected = false;



    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (!wifi.isConnected() && !mobile.isConnected()) {
            Toast.makeText(context,"No internet connection. Please reconnect.", Toast.LENGTH_LONG).show();
            isConnected = false;
        }
        else{
            isConnected = true;
        }

    }
}
