package com.example.jakobwilbrandt.chatt.serverFactory;

import android.util.Log;
import com.example.jakobwilbrandt.chatt.DataClasses.IUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUserHandling implements IUserHandling {


    String TAG = "FirebaseHandler";
    boolean isLoggedIn = false;
    FirebaseAuth auth;


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
    public void addUser(IUser user) {
        //For firebase it is handled in LoginActivity.
    }

    @Override
    public String getCurrentUserId() {
        //TODO: implement getting current user id.
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        if(auth != null) {

            return auth.getCurrentUser().getUid().toString();
        }
        return "Anonymous";
    }

    @Override
    public String getUsername(){
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        if(auth != null) {

            return auth.getCurrentUser().getDisplayName().toString();
        }
        return "Anonymous";

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

    @Override
    public String getAvatarUrl(){
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        return user.getPhotoUrl().toString();
    }


}
