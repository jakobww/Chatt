package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jakobwilbrandt.chatt.NetworkMonitor.NetworkChangeReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUserHandling implements IUserHandling {


    String TAG = "FirebaseHandler";
    boolean isLoggedIn = false;


    @Override
    public boolean CheckIfLoggedIn() {
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    isLoggedIn = true;
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    isLoggedIn = false;

                }

            }
        };

        return isLoggedIn;
    }

    @Override
    public boolean ConnectUserToDb() {



        return false;
    }


}
