package it.polimi.ingsw.client;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

//NON STAMPA NIENTE, CHIAMA CLI/GUI PER STAMPA
//VIEW CONTROLLA INPUT, PREPARA MESSAGGIO (UTILIZZANDO STRUTTURE DATI) E NOTIFICA A CONNECTION DEL MESSAGGIO PREPARATO
public class view extends Observable implements Observer {
    //strutture dati
    //logica
    private boolean cli;
    private cli c;
    //private gui g;
    private String playerClient;
    private Player currentPlayer;
    private ArrayList<LeaderCard> leaderCards;
    private HashMap<Resource,Integer> price;

    public void askAction (){
        if (cli)
        {
            c.action();
            switch (c.getAction())
            {
                case 1:
                    askDev();
                    askPayment();
                    break;
                case 2:
                    c.chooseMarket();
                    askMarket();
                    break;
                case 3:
                    c.chooseSlot();
                    askSlot();
                    break;
                case 4:
                    c.seeBoards();
                    break;
                default:
                    break;
            }
        }
    }

    private void askMarket() {
        c.chooseMarket();

    }

    public void askSlot(){

    }

    public void askDev()
    {
        if (cli)
        {
            c.chooseDev();
            if (c.getChooseInput() == 0)
            {
                break;
            }
            else if(c.getChooseInput()>1 && c.getChooseInput()<13) {
                if (devGrid.get() == null)
                {
                    System.out.println("Deck is void");
                    break();
                }
                if (!playerClient.getDevelopmentDiscounts().isEmpty())
                {
                    c.discountLeader(playerClient.getDevelopmentDiscounts().size());
                }
            }
            else
            {
                //exception
            }
            c.position();
            if ()

        }
    }

    public void askPayment()
    {
        if(cli)
        {
            //crea qualche struttura per memorizzare come l'utente vuole pagare
            for (Resource r:price.keySet()) { //chiama un metodo di cli che prende un Resource come parametro e restituisce un intero.
                c.resourcesPay(r);
            }
        }

    }
    @Override
    public void update(Object message) {

    }
}
