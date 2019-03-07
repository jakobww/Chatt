package com.example.jakobwilbrandt.chatt.DataClasses;

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
