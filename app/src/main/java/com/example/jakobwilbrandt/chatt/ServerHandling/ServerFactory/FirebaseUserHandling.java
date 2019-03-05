package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.jakobwilbrandt.chatt.DataClasses.IUser;
import com.example.jakobwilbrandt.chatt.NetworkMonitor.NetworkChangeReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

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
    public ArrayList<IUser> LoadAllUsers() {

        //TODO: loading users from firebase
        return null;
    }

    @Override
    public void addUser(IUser user) {
        //TODO: adding user to db
    }

    @Override
    public String getCurrentUserId() {
        //TODO: implement getting current user id.
        return "random";
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
