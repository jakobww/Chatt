package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

import com.example.jakobwilbrandt.chatt.DataClasses.IUser;

import java.util.ArrayList;

public interface IUserHandling {

    boolean CheckIfLoggedIn();

    ArrayList<IUser> LoadAllUsers();

    void addUser(IUser user);

    String getCurrentUserId();

    void LogOut();

    String getAvatarUrl();

}
