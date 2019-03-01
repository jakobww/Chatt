package com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory;

public class FirebaseServerProvider implements IServerProvider {
    

    @Override
    public ILoginHandler CreateLoginHandler() {
        return new FirebaseLoginHandler();
    }

    @Override
    public IMessageRTDB CreateMessageRTDB() {
        return new FirebaseMessageRTDB();

    }

    @Override
    public IRoomRTDB CreateRoomRTDB() {
        return new FirebaseRoomRTDB();
    }
}
