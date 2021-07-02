package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumeration.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.SpecialDepot;
import it.polimi.ingsw.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * informazioni richieste
 1)  scelta della riga o colonna, un intero da 0 a 6
 2)  se possiede 2 leader mercato serve la scelta di come vuole trasformare le biglie bianche per ogni biglia bianca
 3)  come inserire nel deposito  e nei depositi leader le risorse, come spostare le risorse all'interno del deposito

 * operazioni da eseguire
 1) tenere traccia della scelta di trasformazione delle biglie bianche
 1) verifica che la trasformazione sia corretta
 2) verificare che l'inserimento sia corretto
 3) in caso positivo, modifica mercato, effettua l'inserimento, assegna i punti fede
 4) in caso negativo, fa in modo che al client arrivi un messaggio d'errore corrispondente

 *
 *  * il controller del server riceve per ogni messaggio del client le informazioni necessarie e sufficienti per modificare il model
 *  * il controller verifica secondo le regole la validità delle mosse
 *  * in caso affermativo modifica il client, altrimenti manda un messaggio di errore
 *  * il controller si aspetta comunque che gli input siano corretti, anche se non leciti secondo le regole

 **/

public class MarketExecutor implements ActionExecutor {

    private ArrayList<Resource> gain;
    private Game game;
    public MarketExecutor(Game game) {
        this.game=game;
        gain=new ArrayList<>();
    }

    public void choiceResource(int input) //l'input va da 0 a 6. Prende le risorse dal mercato e fa avanzare se è presente un segnalino fede
    {
        boolean faith=false;
        if (input < 3) { //da 0 a 2
            gain = new ArrayList<Resource>(game.getRow(input)); //input va da 0 a 2 per la riga
            game.insertRow(input);
        }
        else { //da 3 a 6
            gain = new ArrayList<Resource>(game.getCol(input-3)); //input va da 0 a 3 per la colonna
            game.insertCol(input-3); //da 0 a 3
        }

        for (Resource r : gain)
        {
            if (r == Resource.FAITH)
            {
                game.forwardPlayer(game.getIndexPlayer(game.getCurrentP()), 1);
                faith=true;
            }
        }
        gain.removeIf(resource -> resource.equals(Resource.FAITH) );
        if(input<3)
        {
            game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+ " chose row "+ input);
        }else {
            game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+ " chose column "+ (input-3));
        }
        if (faith)
        game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+" forward by 1 box, current position " + game.getCurrentP().getFaithLocation()+"/24");
    }

    public void manualChange (ArrayList<Resource> list)
    {
        int i=0;
        if(list.size()!=0)
        {
            for (Resource r:gain) {
                if(r==Resource.WHITE)
                {
                    r=list.get(i);
                    i++;
                }
            }
            game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+ " used leader ability.");
        }
        else
        {
            gain.removeIf(r -> r.equals(Resource.WHITE));
        }
        game.getCurrentP().setGain(gain);
        System.out.println(gain);
    }

    //market 2
    public boolean checkDepot (ArrayList<ArrayList<Resource>> depots, ArrayList<SpecialDepot> specialDepots)
    {
        for(ArrayList<Resource> row:depots) {
            if (!Util.isDepotCorrect(depots)) {
                return false;
            }
        }
        for (SpecialDepot s:specialDepots)
        {
            if(!s.isCorrect())
                return false;
        }
        return true;
    }

    public boolean checkNumResource(HashMap<Resource,Integer> clientCount)//make sure player didn't send a depot that is not his.
    {
        System.out.println(clientCount);
        //count each resource from player's gain and depot+leaderDepot server side then compare them to client side
        for (Resource r: clientCount.keySet()) {
            if((game.getCurrentP().getNumResourceDepot(r)+game.getCurrentP().getNumResourceSp(r)+Collections.frequency(game.getCurrentP().getGain(),r))!=clientCount.get(r))
            {
                return false;
            }
        }

        return true;
    }

    public boolean checkResArray(ArrayList<Resource> discount)//make sure that every thing inside given array is contained by player.marketDiscount
    {
        for(Resource res: discount)
        {
            if (!game.getCurrentP().getMarketDiscounts().contains(res))
                return false;
        }
        return true;
    }
}
