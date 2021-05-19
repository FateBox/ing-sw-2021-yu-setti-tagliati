package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumeration.*;

import java.util.ArrayList;
import java.util.Collections;

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

public class MarketExecutor extends ActionController{

    private ArrayList<Resource> gain;

    public MarketExecutor(GameController gameController) {
        super(gameController);
    }

    public void choiseResource (int input) //l'input va da 0 a 6. Prende le risorse dal mercato e fa avanzare se è presente un segnalino fede
    {

        if (input < 3) { //da 0 a 2
            gain = new ArrayList<Resource>(game.getRow(input)); //input va da 0 a 2 per la riga
            game.insertRow(input);
        }
        else { //da 3 a 6
            gain = new ArrayList<Resource>(game.getCol(input-3)); //input va da 0 a 3 per la colonna
            game.insertRow(input-3); //da 0 a 3
        }

        for (Resource r : gain)
        {
            if (r == Resource.FAITH)
            {
                game.forwardPlayer(game.getIndexPlayer(game.getCurrentP()), 1);
            }
        }

    }

    public void automaticChange () //modifica automatica: non si hanno carte leader attive o se ne possiede una sola relativa al mercato
    {
        if (game.getCurrentP().getMarketDiscounts().isEmpty())
        {
            game.modifyResources(Resource.WHITE, gain);
        }
        else
        {
            game.modifyResources(game.getCurrentP().getMarketDiscounts().get(0), gain);
        }
    }

    public void semiAutomaticChange (int leaderInput) //il giocatore comunica solo quale delle 2 carte leader mercato usare, la modifica è automatica per tutti i bianchi
    {
        Resource r = game.getCurrentP().getMarketDiscounts().get(leaderInput);
        game.modifyResources(r, gain);
    }

    public void manualChange (int[] leaderInput) throws Exception //vengono usate entrambe le carte leader. ogni input è relativo ad una singola biglia bianca. 0 prima carta leader, 1 seconda carta leader
    {
        ArrayList<Resource> s = new ArrayList<Resource>(Collections.frequency(gain, Resource.WHITE));
        if (leaderInput.length == s.size()) {
            for (int i = 0; i < leaderInput.length; i++) {
                s.add(game.getCurrentP().getMarketDiscounts().get(leaderInput[i]));
            }
            game.leadersResources(s, gain);
        }
        else //questo errore in realtà non si dovrebbe verificare, poiché fa parte del controllo dell'input da parte del client
        {
            throw new Exception ("The number of whites does not match");
        }
    }

    //conclusa prima fase. Unica modifica al model: Market

    //seconda fase. Inserimento risorse deposito. //questa parte avviene prevalentemente nel client. Il server si limita a verificare che sia corretta la versione finale

    public void checkDepot (ArrayList<ArrayList<Resource>> depots, ArrayList<Resource> removed) throws Exception
    {

        //controllo correttezza
        for(int i = 0; i< depots.size(); i++){ //considera tutti i piani
            if (depots.get(i).size()>0 && i<3) // se il piano è normale e non è vuoto allora
            {
                if( depots.get(0).size() > i+1 || depots.get(i).size() != Collections.frequency(depots.get(i), depots.get(i).get(0)))// controlla che il numero di elementi del primo tipo della riga sia pari al numero di elementi totali della riga
                {
                    throw new Exception ("The resources in the depot are wrong");
                }
            }
            if (depots.get(i).size()>0 && i>2) //considera i piani speciali
            {
                if(depots.get(i).size() > 2 || depots.get(i).size() != Collections.frequency(depots.get(i),game.getCurrentP().getSpecialdepot(i-2)))// controlla che il numero di elementi del primo tipo della riga sia pari al numero di elementi totali della riga
                {
                    throw new Exception ("The resources in the leader depot are wrong");
                }
            }
        }
        //se si arriva qui senza errori, il model viene modificato
        game.forwardOtherPlayers(game.getIndexPlayer(game.getCurrentP()), removed.size()); //i giocatori vengono fatti avanzare
        for(int i = 0; i<depots.size(); i++)
        {
            game.getCurrentP().setDepots(depots); //il deposito inviato al server viene copiato nel deposito del model
        }


    }
}
