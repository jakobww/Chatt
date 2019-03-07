package com.example.jakobwilbrandt.chatt.serverFactory;

import com.example.jakobwilbrandt.chatt.BaseActivity;
/**
 * Created by Jakob Wilbrandt.
 * This interface is part of the serverfactory, which decouples the implementation of the realtime database from the rest of the code.
 * By doing so, it's possible to implement another realtime database more easily.
 */
public interface IServerFactory {

    IUserHandling CreateUserHandler();
    IRoomRTDB CreateRoomRTDB();
    BaseActivity CreateLoginActivity();

}
