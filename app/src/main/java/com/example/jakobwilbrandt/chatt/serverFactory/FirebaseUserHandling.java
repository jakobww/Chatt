package com.example.jakobwilbrandt.chatt.serverFactory;

import android.util.Log;
import com.example.jakobwilbrandt.chatt.DataClasses.IUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/**
 * Created by Jakob Wilbrandt.
 * This class is part of the serverfactory, which decouples the implementation of the realtime database from the rest of the code.
 * By doing so, it's possible to implement another realtime database more easily.
 */
public class FirebaseUserHandling implements IUserHandling {


    String TAG = "FirebaseHandler";
    boolean isLoggedIn = false;
    FirebaseAuth auth;


    //Used in loginactivity
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

            return auth.getCurrentUser().getUid();
        }
        return "Anonymous";
    }

    @Override
    public String getUsername(){
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        if(auth != null) {

            return auth.getCurrentUser().getDisplayName();
        }
        return "Anonymous";

    }


    //Used in the options menu
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

    //Used in the messages
    @Override
    public String getAvatarUrl(){
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        return user.getPhotoUrl().toString();
    }


}
