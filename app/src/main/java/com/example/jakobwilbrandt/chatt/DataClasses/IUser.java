package com.example.jakobwilbrandt.chatt.DataClasses;

/**
 * Created by Jakob Wilbrandt.
 * Using interfaces for decoupling
 * As of now this class is not used, since it is handled in the loginactivity for firebase which is part of the serverFactory
 * If the app is implemented with another realtime database (another factory), creation of these users would be handled in the corresponding login activity
 */

public interface IUser {

    IUser getUser();

    void setUser(IUser user);

}
