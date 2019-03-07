package com.example.jakobwilbrandt.chatt.serverFactory;

import com.example.jakobwilbrandt.chatt.BaseActivity;

public interface IServerFactory {

    IUserHandling CreateUserHandler();
    IRoomRTDB CreateRoomRTDB();
    BaseActivity CreateLoginActivity();

}
