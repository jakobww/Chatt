package com.example.jakobwilbrandt.chatt.serverFactory;

public class ServerProducer {
    /**
     * Created by Jakob Wilbrandt.
     * This class is part of the serverfactory, which decouples the implementation of the realtime database from the rest of the code.
     * By doing so, it's possible to implement another realtime database more easily.
     */
    public static IServerFactory getFactory(String FactoryType){

        //If firebase is requested as a database - return that factory
        if(FactoryType.toLowerCase().equals("firebase")){
            return new FirebaseServerFactory();
        }
        else{
            //Default factory - can be changed in the future when new server solutions appears
            return new FirebaseServerFactory();
        }

    }

}
