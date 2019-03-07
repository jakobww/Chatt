package com.example.jakobwilbrandt.chatt.DataClasses;
/**
 * Created by Jakob Wilbrandt.
 * As of now this class is not used, since it is handled in the loginactivity for firebase which is part of the serverFactory
 * if the app is implemented with another realtime database (another factory), creation of these users would be handled in the corresponding login activity
 */
public class User implements IUser {

    IUser user;

    public User(IUser user) {
        this.user = user;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public void setUser(IUser user) {
        this.user = user;
    }
}
