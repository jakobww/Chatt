package com.example.jakobwilbrandt.chatt.UserHandling;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseLoginHandler implements ILoginHandler {


    String TAG = "FirebaseHandler";
    boolean isLoggedIn = false;

    @Override
    public boolean CheckLogin() {
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
}
