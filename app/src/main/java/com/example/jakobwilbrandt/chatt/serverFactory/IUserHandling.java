package com.example.jakobwilbrandt.chatt.serverFactory;

import com.example.jakobwilbrandt.chatt.DataClasses.IUser;
/**
 * Created by Jakob Wilbrandt.
 * This interface is part of the serverfactory, which decouples the implementation of the realtime database from the rest of the code.
 * By doing so, it's possible to implement another realtime database more easily.
 */
public interface IUserHandling {

    boolean CheckIfLoggedIn();

    void addUser(IUser user);

    String getCurrentUserId();

    void LogOut();

    String getAvatarUrl();

    String getUsername();

}
