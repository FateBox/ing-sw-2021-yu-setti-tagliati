package it.polimi.ingsw.controller;

import it.polimi.ingsw.connection.Message;

public class Decoder {
    //riceve messaggi da parte del View
    private Message m;

    public Decoder(Message m)
    {

    }

    public void solveMessage()
    {
        String opcod=m.getOpCod();
        switch (opcod.charAt(0))
        {
            case '0'://Connection message
            {

            }
            case '1'://Control message
            {
            }
        }
    }

}
