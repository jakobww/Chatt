package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

import com.example.jakobwilbrandt.chatt.BaseActivity;

public interface IServerFactory {

    IUserHandling CreateLoginHandler();
    IMessageRTDB CreateMessageRTDB();
    IRoomRTDB CreateRoomRTDB();
    BaseActivity CreateLoginActivity();
}
