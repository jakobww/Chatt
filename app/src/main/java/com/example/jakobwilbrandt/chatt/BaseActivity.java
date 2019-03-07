package com.example.jakobwilbrandt.chatt;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.jakobwilbrandt.chatt.NetworkMonitor.NetworkChangeReceiver;
import com.example.jakobwilbrandt.chatt.serverFactory.IRoomRTDB;
import com.example.jakobwilbrandt.chatt.serverFactory.IServerFactory;
import com.example.jakobwilbrandt.chatt.serverFactory.IUserHandling;
import com.example.jakobwilbrandt.chatt.serverFactory.ServerProducer;
/**
 * Created by Jakob Wilbrandt.
 * Activity used both chat and and main, in order not to duplicate code.
 */
public class BaseActivity extends AppCompatActivity {

    String TAG = "BaseActivity";
    protected NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    protected IServerFactory serverFactory = ServerProducer.getFactory("firebase");
    protected IRoomRTDB roomRTDB;
    protected IUserHandling userHandling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomRTDB = serverFactory.CreateRoomRTDB();
        userHandling = serverFactory.CreateUserHandler();

    }

    //Inflating the menu with the settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_loginout);
        boolean isLoggedIn = userHandling.CheckIfLoggedIn();
        if(isLoggedIn){
            item.setTitle(R.string.action_logout);
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    userHandling.LogOut();
                    BaseActivity baseActivity = serverFactory.CreateLoginActivity();
                    Intent intent = new Intent(BaseActivity.this, baseActivity.getClass());
                    startActivity(intent);
                    finish();
                    return true;
                }
            });

        }
        else{
            item.setTitle(R.string.action_login);
        }


        return true;
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
