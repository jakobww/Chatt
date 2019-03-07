package com.example.jakobwilbrandt.chatt.serverFactory;

import com.example.jakobwilbrandt.chatt.BaseActivity;
/**
 * Created by Jakob Wilbrandt.
 * This class is part of the serverfactory, which decouples the implementation of the realtime database from the rest of the code.
 * By doing so, it's possible to implement another realtime database more easily.
 */
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
