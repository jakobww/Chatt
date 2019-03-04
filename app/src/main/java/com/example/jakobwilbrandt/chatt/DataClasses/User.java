package com.example.jakobwilbrandt.chatt.DataClasses;



public class User implements IUser {

    private String avatarPath;

    @Override
    public IUser getUser() {
        return this;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
