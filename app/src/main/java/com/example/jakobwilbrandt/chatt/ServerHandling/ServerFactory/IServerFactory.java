package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

public interface IServerProvider {

    ILoginHandler CreateLoginHandler();
    IMessageRTDB CreateMessageRTDB();
    IRoomRTDB CreateRoomRTDB();
}
