package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

import com.example.jakobwilbrandt.chatt.BaseActivity;

public class FirebaseServerFactory implements IServerFactory {


    @Override
    public IUserHandling CreateUserHandler() {
        return new FirebaseUserHandling();
    }


    @Override
    public IRoomRTDB CreateRoomRTDB() {
        return new FirebaseRoomRTDB();
    }

    @Override
    public BaseActivity CreateLoginActivity() {
        return new FirebaseLoginAcitivity();
    }
}
