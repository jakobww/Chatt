package com.example.jakobwilbrandt.chatt.serverFactory;

import com.example.jakobwilbrandt.chatt.DataClasses.IUser;

public interface IUserHandling {

    boolean CheckIfLoggedIn();

    void addUser(IUser user);

    String getCurrentUserId();

    void LogOut();

    String getAvatarUrl();

    String getUsername();

}
