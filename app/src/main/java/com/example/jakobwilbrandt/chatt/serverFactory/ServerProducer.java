package com.example.jakobwilbrandt.chatt.serverFactory;

public class ServerProducer {

    public static IServerFactory getFactory(String FactoryType){

        if(FactoryType.toLowerCase().equals("firebase")){
            return new FirebaseServerFactory();
        }
        else{
            //Default factory - can be changed in the future when new server solutions appears
            return new FirebaseServerFactory();
        }

    }

}
