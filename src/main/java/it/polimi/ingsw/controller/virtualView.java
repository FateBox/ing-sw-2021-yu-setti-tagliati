package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.connection.Client;
import it.polimi.ingsw.connection.Message;

public class virtualView implements Observer<Message>{
    Client client;

    virtualView()
    {

    }
    @Override
    //forwarda il messaggio alla view
    public void update(Message m) {

    }

}
