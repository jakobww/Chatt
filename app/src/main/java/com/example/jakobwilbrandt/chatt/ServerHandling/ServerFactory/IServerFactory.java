package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

public interface IServerFactory {

    ILoginHandler CreateLoginHandler();
    IMessageRTDB CreateMessageRTDB();
    IRoomRTDB CreateRoomRTDB();
}
