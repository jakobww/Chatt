package com.example.jakobwilbrandt.chatt.NetworkMonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;
/**
 * Created by Jakob Wilbrandt.
 * Used for listening for callbacks, if internet connection is not here any longer.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    public static boolean getIsConnected() {
        return isConnected;
    }

    public static boolean isConnected = false;

    //TODO: Need to handle what happens if the internet connection is lost. Right now, the user is notified, but no further action is taken.


    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            final android.net.NetworkInfo wifi = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            final android.net.NetworkInfo mobile = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


            if (!wifi.isConnected() && !mobile.isConnected()) {
                Toast.makeText(context, com.example.jakobwilbrandt.chatt.R.string.no_internet, Toast.LENGTH_LONG).show();
                isConnected = false;
            } else {
                isConnected = true;
            }
        }catch (Exception e){
            Log.d("INTERNET CALLBACK FAIL", "Checking the internet has failed");
        }


    }
}
