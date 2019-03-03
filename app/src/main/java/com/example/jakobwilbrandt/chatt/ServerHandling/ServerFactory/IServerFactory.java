package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

import com.example.jakobwilbrandt.chatt.BaseActivity;

public interface IServerFactory {

    IUserHandling CreateUserHandler();
    IMessageRTDB CreateMessageRTDB();
    IRoomRTDB CreateRoomRTDB();
    BaseActivity CreateLoginActivity();
}
