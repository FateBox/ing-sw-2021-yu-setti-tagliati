package it.polimi.ingsw.client;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.LeaderCard;

import java.util.ArrayList;
import java.util.HashMap;

//NON STAMPA NIENTE, CHIAMA CLI/GUI PER STAMPA
//VIEW CONTROLLA INPUT, PREPARA MESSAGGIO (UTILIZZANDO STRUTTURE DATI) E NOTIFICA A CONNECTION DEL MESSAGGIO PREPARATO
public class view extends Observable implements Observer {
    //strutture dati
    //logica
    private boolean cli;
    private ArrayList<LeaderCard> leaderCards;
    private HashMap<Resource,Integer> price;
    public void askPayment()
    {
        if(cli)
        {   //crea qualche struttura per memorizzare come l'utente vuole pagare
            for (Resource r:price.keySet()) {
                //chiama un metodo di cli che prende un Resource come parametro e restituisce un intero.
            }
        }

    }
    @Override
    public void update(Object message) {

    }
}
