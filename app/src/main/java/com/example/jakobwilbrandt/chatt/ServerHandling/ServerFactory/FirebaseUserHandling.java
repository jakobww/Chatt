package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.jakobwilbrandt.chatt.NetworkMonitor.NetworkChangeReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUserHandling implements IUserHandling {


    String TAG = "FirebaseHandler";
    boolean isLoggedIn = false;


    @Override
    public boolean CheckIfLoggedIn() {
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null) {
            isLoggedIn = true;
        }
        else{
            isLoggedIn = false;
        }

        return isLoggedIn;
    }

    @Override
    public boolean ConnectUserToDb() {



        return false;
    }

    @Override
    public void LogOut() {
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null) {
            auth.signOut();
        }
        else{
            Log.d(TAG,"User not logged in and tried to log out");
        }
    }


}
