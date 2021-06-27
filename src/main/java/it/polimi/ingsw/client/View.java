package it.polimi.ingsw.client;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.enumeration.PlayerAction;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

//NON STAMPA NIENTE, CHIAMA CLI/GUI PER STAMPA
//VIEW CONTROLLA INPUT, PREPARA MESSAGGIO (UTILIZZANDO STRUTTURE DATI) E NOTIFICA A CONNECTION DEL MESSAGGIO PREPARATO
public class View extends Observable<Message> implements Observer<Message> {
    //attributi visualizzazione
    private boolean cli;
    private cli c;
    //private gui g;

    //informazioni comuni e variabili per operazioni
    private String nickClient;//nick of player of this client
    private ArrayList<String> nickList;//nick ordinati dei giocatori di playerlist
    private String currentPlayer; //nick of player in turn
    private HashMap<String, PlayerInformation> player;
    private PlayerInformation p; //client player pointer

    private ArrayList<DevCard> visibleDevGrid; //le carte di questa lista non vanno mai rimosse, solo aggiornate, quando un mazzo finisce nel server, si aggiorna qui con null
    private Resource[][] market;
    private Resource freeMarble;
    private boolean[] popeSpace;
    private ArrayList<Resource> marketChange;
    private HashMap<Resource,Integer> price;
    private HashMap<Resource,Integer> taking;
    private HashMap<Resource,Integer> specialTaking;
    private ArrayList<Resource> gain; //mandato dal server dopo market


    public View()
    {
        p = player.get(nickClient);
        market = new Resource[3][4];
        popeSpace = new boolean[3];
        price.put(Resource.COIN, 0);
        price.put(Resource.SERVANT, 0);
        price.put(Resource.SHIELD, 0);
        price.put(Resource.STONE, 0);
    }

    public void askInitially()
    {
      int n = nickList.indexOf(nickClient);
      c.initialLeader(player.get(nickClient),nickClient, n);
      for(int i : c.getInitialInput()) {
          switch (i){
              case 0:
                  p.getLeaderCards().
                  break;
              case 1:
                  break;
              case 2:
                  break;
              case 3:
                  break;
              case 4:
                  break;
          }
      }
    }

    public void askAction () {
        if (cli) {
            if (nickClient.equals(currentPlayer)) { //griglia 1 market 2 leader 3 produzione 4 plance 5
                c.action();
                switch (c.getAction()) {
                    case 1:
                        askDev();
                        break;
                    case 2:
                        askMarket();
                        break;
                    case 3:
                        askLeader();
                    case 4:
                        askSlot();
                        break;
                    case 5:
                        c.seeBoards(player, popeSpace, nickClient);
                        break;
                    default:
                        break;
                }
            } else {
                c.watch();
                switch (c.getAction()) {
                    case 1:
                        c.printDevGrid(visibleDevGrid);
                        break;
                    case 2:
                        c.printMarket(market, freeMarble);
                        break;
                    case 3:
                        c.seeBoards(player, popeSpace, nickClient);
                        break;
                    default:
                        break;

                }
            }
        }
    }
    public void askDepot() {

        c.depotAction(p);
        switch (c.getAction()) {
            case 1:
                askInsert();
                break;
            case 2:
                askSwap();
                break;
            case 3:
                break;
            default:
                return;
        }
        removeNullDepot();
    }

    private void removeNullDepot() {
        for (int i = 0; i<3; i++)
        {
            for (Resource r : p.getDepot().get(i))
            {
                if (r == null)
                {
                    p.getDepot().get(i).remove(null);
                }
            }
        }
        for (int i = 0; i<p.getLeaderDepots().size(); i++ )
        {
            for (Resource r : p.getLeaderDepots().get(i).getRow())
            {
                if(r == null) {
                    p.getLeaderDepots().get(i).getRow().remove(null);
                }
            }
        }
    }

    private void addNullDepot() {
    //questa funzione aggiunge elementi nulli al deposito dove non ci sono risorse per permettere lo scambio di celle
        for (int i = 0; i<3; i++)
        {
            while (p.getDepot().get(i).size()<i+1)
            {
                p.getDepot().get(i).add(null);
            }
        }
        for (int i = 0; i<p.getLeaderDepots().size(); i++ )
        {
           while(p.getLeaderDepots().get(i).getQuantity()<2)
           {
               p.getLeaderDepots().get(i).getRow().add(null);
           }
        }
    }

    public void askInsert(){
        Resource r;
        c.chooseInsert(p);
        r = gain.get(c.getChooseInput());
        switch (c.getDepotInput())
        {
            case 1:
                p.getDepot().get(0).add(r);
                break;
            case 2:
                p.getDepot().get(1).add(r);
                break;
            case 3:
                p.getDepot().get(2).add(r);
                break;
            case 4:
                p.getLeaderDepots().get(0).getRow().add(r);
                break;
            case 5:
                p.getLeaderDepots().get(1).getRow().add(r);
                break;
            default:
                return;
        }
        gain.remove(c.chooseInput);
        askDepot();
    }

    public void askSwap() {
        int[] p1, p2 = new int[2];
        Resource r,s;
        ArrayList<Resource> pointer;
        addNullDepot();
        c.chooseSwap(p);
        p1 = cellConvert(c.chooseInput);
        p2 = cellConvert(c.depotInput);
        if (c.chooseInput > 5)
        {
            pointer = p.getLeaderDepots().get(p1[0]).getRow();
            r = p.getLeaderDepots().get(p1[0]).getRow().get(p1[1]);
        }
        else
        {
            pointer = p.getDepot().get(p1[0]);
            r = p.getDepot().get(p1[0]).get(p1[1]);
        }

        if (c.depotInput>5)
        {
            s = p.getLeaderDepots().get(p2[0]).getRow().get(p2[1]);
            pointer.set(p1[1], s);
            p.getLeaderDepots().get(p2[0]).getRow().set(p2[1], r);
        }
        else
        {
            s = p.getDepot().get(p2[0]).get(p2[1]);
            pointer.set(p1[1],s);
            p.getDepot().get(p2[0]).set(p2[1], r);

        }
        removeNullDepot();

    }

    private int[] cellConvert(int c)
    {
        int [] r;
        switch(c)
        {
            case 0:
                r = new int[]{0, 0};
                break;
            case 1:
                r = new int[]{1, 0};
                break;
            case 2:
                r = new int[]{1, 1};
                break;
            case 3:
                r = new int[]{2, 0};
                break;
            case 4:
                r = new int[]{2, 1};
                break;
            case 5:
                r = new int[]{2, 2};
                break;
            //leader
            case 6:
                r = new int[]{0, 0}; //i1 sta per il leader deposito, i2 sta per l'elemento della riga
                break;
            case 7:
                r = new int[]{0, 1}; //i1 sta per il leader deposito, i2 sta per l'elemento della riga
                break;
            case 8:
                r = new int[]{1, 0}; //i1 sta per il leader deposito, i2 sta per l'elemento della riga
                break;
            case 9:
                r = new int[]{1, 1}; //i1 sta per il leader deposito, i2 sta per l'elemento della riga
                break;
            default:
                r = new int[] {-1, -1};
                break;
        }
        return r;
    }


    private void askLeader() {
        if (cli)
        {
            c.chooseLeader(p, nickClient);
            if (c.getLeaderInput() == 0 || c.getLeaderInput()-1 > p.getLeaderCards().size())
            {return;}//se size è 2, leaderinput può essere MAX 3
            c.interactLeader();
            switch (c.getChooseInput()){
                case 0:
                    //messaggio di scarto
                    break;
                case 1:
                    //messaggio attivazione
                    break;
                default:
                    return;
            }
        }
    }

    private void askMarket() {
        //mercato1
        int w=0;
        int i;
        c.chooseMarket(market, freeMarble);
        if (c.getChooseInput() > 3) //column
        {
            for(i = 0; i<3; i++) //3 elements
            {
                if (market[i][c.getChooseInput()-4] == Resource.WHITE)
                    w++;
            }
        }
        else if (c.getChooseInput() >0)
        {
            for(i = 0; i<4; i++)
            {
                if (market[i][c.getChooseInput()-1] == Resource.WHITE)
                    w++;
            }
        }

        else{
            return;
        }
        switch(p.getLeaderMarket().size())
        {
            case 0:
                break;
            case 1:
                for (i = 0; i<w; i++)
                {
                    marketChange.add(p.getLeaderMarket().get(0));
                }
                break;
            case 2:
                c.marketLeader(w);
                for(int r : c.getExchangeInput())
                {
                    marketChange.add(p.getLeaderMarket().get(r));
                }
                break;
            default:
                return;
        }

    }

    public void askSlot(){
            c.chooseSlot(p);
            for(int  n : c.getDevSlotInput())
            {
                if (n == 0)
                {
                    c.chooseAny(2, "to pay");
                    for (int i = 0; i<2; i++) {
                        switch (c.getAnyInput()[i]) {
                            case 0:
                                ((BasicSlot) p.getDevSlots().get(0)).addInputResource(i, Resource.COIN);
                                break;
                            case 1:
                                ((BasicSlot) p.getDevSlots().get(0)).addInputResource(i, Resource.SERVANT);
                                break;
                            case 2:
                                ((BasicSlot) p.getDevSlots().get(0)).addInputResource(i, Resource.SHIELD);
                                break;
                            case 3:
                                ((BasicSlot) p.getDevSlots().get(0)).addInputResource(i, Resource.STONE);
                                break;
                            case 4:
                                ((BasicSlot) p.getDevSlots().get(0)).addInputResource(i, Resource.FAITH);
                                break;
                            default:
                                return;
                        }
                    }

                    c.chooseAny(1, "to take for the basic slot ");
                    switch (c.getAnyInput()[0]) {
                        case 0:
                            ((BasicSlot) p.getDevSlots().get(0)).addOutputResource(Resource.COIN);
                            break;
                        case 1:
                            ((BasicSlot) p.getDevSlots().get(0)).addOutputResource(Resource.SERVANT);
                            break;
                        case 2:
                            ((BasicSlot) p.getDevSlots().get(0)).addOutputResource(Resource.SHIELD);
                            break;
                        case 3:
                            ((BasicSlot) p.getDevSlots().get(0)).addOutputResource(Resource.STONE);
                            break;
                        case 4:
                            ((BasicSlot) p.getDevSlots().get(0)).addOutputResource(Resource.FAITH);
                            break;
                        default:
                            return;
                    }
                }
                else if (n == 4 || n == 5)
                {
                    c.chooseAny(1, "to take for the leader slot");
                    switch (c.getAnyInput()[0]) {
                        case 0:
                            ((LeaderSlot) p.getDevSlots().get(n)).addOutputResource(Resource.COIN);
                            break;
                        case 1:
                            ((LeaderSlot) p.getDevSlots().get(n)).addOutputResource(Resource.SERVANT);
                            break;
                        case 2:
                            ((LeaderSlot) p.getDevSlots().get(n)).addOutputResource(Resource.SHIELD);
                            break;
                        case 3:
                            ((LeaderSlot) p.getDevSlots().get(n)).addOutputResource(Resource.STONE);
                            break;
                        case 4:
                            ((LeaderSlot) p.getDevSlots().get(n)).addOutputResource(Resource.FAITH);
                            break;
                        default:
                            return;
                    }
                }
                else if (n == 6)
                {
                   return;
                }
            }
            productionExpense();
            askPayment();
            return;
        }

    private void productionExpense() {
        price.put(Resource.COIN, 0);
        price.put(Resource.SERVANT, 0);
        price.put(Resource.SHIELD, 0);
        price.put(Resource.STONE, 0);
        for (int i : c.getDevSlotInput())
        {
            ArrayList<Resource> ar = p.getDevSlots().get(i).getInputResource();
            price.put(Resource.COIN, price.get(Resource.COIN)+Collections.frequency(ar, Resource.COIN));
            price.put(Resource.SERVANT, price.get(Resource.SERVANT)+Collections.frequency(ar, Resource.SERVANT));
            price.put(Resource.SHIELD, price.get(Resource.SHIELD)+Collections.frequency(ar, Resource.SHIELD));
            price.put(Resource.STONE, price.get(Resource.STONE)+Collections.frequency(ar, Resource.STONE));
        }
        c.printExpense(price);
    }

    public void askDev()
    {
        int idDev;
        int idSlot;
        Resource r1 = null;
        Resource r2 = null;
        if (cli) {
            c.chooseDev(visibleDevGrid);
            if (c.getChooseInput() == 0) {
                return;
            } else if (c.getChooseInput() > 0 && c.getChooseInput() < 13) {
                if (visibleDevGrid.get(c.getChooseInput()-1) == null) {
                    System.out.println("Deck is void");
                    return;
                }
                    idDev = visibleDevGrid.get(c.getChooseInput()).getId();
                    c.position();
                    if (c.getPositionInput() < 1 || c.getPositionInput() > 3) {
                        return;
                    }
                    idSlot = c.getPositionInput();
                    if (p.getLeaderDiscount().size() >0)
                    {
                        c.discountLeader(p.getLeaderDiscount().size());
                        switch (c.getLeaderInput())
                        {
                            case 0:
                                break;
                            case 1:
                                r1 = p.getLeaderDiscount().get(0);
                                r2 = null;
                                break;
                            case 2:
                                r1 = null;
                                r2 = p.getLeaderDiscount().get(1);
                                break;
                            case 3:
                                r1 = p.getLeaderDiscount().get(0);
                                r2 = p.getLeaderDiscount().get(1);
                                break;
                            default:
                                return;
                        }

                    }
                        purchaseExpense(r1, r2);
                        askPayment();
                }

            } else {
                //exception
            }
        }

    private void purchaseExpense(Resource r1, Resource r2) {
        ArrayList<Resource> ar = visibleDevGrid.get(c.getChooseInput()-1).getCostList();
        price.put(Resource.COIN, Collections.frequency(ar, Resource.COIN));
        price.put(Resource.SERVANT, Collections.frequency(ar, Resource.SERVANT));
        price.put(Resource.SHIELD, Collections.frequency(ar, Resource.SHIELD));
        price.put(Resource.STONE, Collections.frequency(ar, Resource.STONE));
        if(r1 != null)
        {price.put(r1, price.get(r1)-1);}
        if(r2 != null)
        {price.put(r2, price.get(r1)-1);}
        for (Resource r : price.keySet())
        {
            if (price.get(r)<1)
            {
                price.remove(r);
            }
        }
        c.printExpense(price);
    }

    public void askPayment()
    {
                if (cli) {
                    c.printDepot(p);
                    c.printStrongBox(p);
                for (Resource r : price.keySet()) { //chiama un metodo di cli che prende un Resource come parametro e restituisce un intero.
                    c.depotTaking(r);
                    taking.put(r, c.getResourcesInput());
                    for (SpecialDepot sd : p.getLeaderDepots()) {
                        if (r == sd.getRes()){
                            c.specialDepotTaking(r);
                        }
                    }
                    c.specialDepotTaking(r);
                }
            }

    }

    public void showError(String text)
    {
        //fai stampare errore da cli o gui.
    }
    @Override
    public void update(Message message) {
        /*switch (message.getType())
        {
            case UPDATE:
            {
                switch (message.getPlayerAction())
                {
                    case
                }
            }

            case ERROR:
            {
                if (this.nickClient==message.getPlayerNick())
                {
                    showError(message.getText());
                }
            }
        }*/
    }
}
