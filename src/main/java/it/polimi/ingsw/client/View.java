package it.polimi.ingsw.client;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.enumeration.MessageType;
import it.polimi.ingsw.enumeration.PlayerAction;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;

import java.lang.reflect.Array;
import java.util.*;

//NON STAMPA NIENTE, CHIAMA CLI/GUI PER STAMPA
//VIEW CONTROLLA INPUT, PREPARA MESSAGGIO (UTILIZZANDO STRUTTURE DATI) E NOTIFICA A CONNECTION DEL MESSAGGIO PREPARATO
public class View extends Observable<Message> implements Observer<Message> {
    //attributi visualizzazione
    private boolean cli;
    private Cli c;
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
        c = new Cli();
        market = new Resource[3][4];
        popeSpace = new boolean[3];
        player = new HashMap<>(4);
        price = new HashMap<>(4);
        taking = new HashMap<>(4);
        specialTaking = new HashMap<>(4);
        marketChange = new ArrayList<>();
    }

    //metodi get

    public Cli getC() {
        return c;
    }

    public ArrayList<DevCard> getVisibleDevGrid() {
        return visibleDevGrid;
    }

    public Resource[][] getMarket() {
        return market;
    }

    public Resource getFreeMarble() {
        return freeMarble;
    }

    public boolean[] getPopeSpace() {
        return popeSpace;
    }

    public ArrayList<Resource> getMarketChange() {
        return marketChange;
    }

    public HashMap<Resource, Integer> getPrice() {
        return price;
    }

    public HashMap<Resource, Integer> getTaking() {
        return taking;
    }

    public HashMap<Resource, Integer> getSpecialTaking() {
        return specialTaking;
    }

    public ArrayList<Resource> getGain() {
        return gain;
    }

    //metodi set

    public void setCli (boolean b)
    {
        this.cli = b;
    }

    public void setPlayerView(String nc, ArrayList<String> nl)
    {
        setNickClient(nc);
        setNickList(nl);
        setCurrentPlayer(nickList.get(0));
        setPlayer();
        p = player.get(nickClient);
    }

    public void setNickList(ArrayList<String> nl) {
        nickList = new ArrayList<>(nl);
    }

    public void setPlayer() {
        for (String s : nickList) {
            player.put(s, new PlayerInformation(s));
        }
    }

    public void setCurrentPlayer(String s) {
        currentPlayer = s;
    }

    public void setNickClient(String nc) {
        nickClient = nc;
    }

    public void setFreeMarble(Resource freeMarble) {
        this.freeMarble = freeMarble;
    }

    public void setGameView(Resource[][] m, Resource fm, boolean[] ps, ArrayList<DevCard> deck)
    {
        setMarket(m);
        setFreeMarble(fm);
        setPopeSpace(ps);
        setVisibleDevGrid(deck);
    }

    public void setVisibleDevGrid(ArrayList<DevCard> deck) {
        visibleDevGrid = new ArrayList<DevCard>(deck);
    }

    public void setPopeSpace(boolean[] ps) {
        System.arraycopy(ps, 0, popeSpace, 0, 3);
    }

    public void setMarket(Resource[][] market) {
        for (int i = 0; i<3; i++) //per ognuna delle 3 righe
        {
            System.arraycopy(market[i], 0, this.market[i], 0, 4); //copia tutti e 4 gli elementi
        }
    }

    public void setGain(ArrayList<Resource> gain) {
        this.gain = new ArrayList<>(gain);
    }

    //metodi gestione input
    public void askInitially()
    {
        int n = nickList.indexOf(nickClient);
        if(cli)
        {
            c.initialLeader(player.get(nickClient),nickClient, n);
        }
        else
        {

        }
        Message message=new Message();
        message.setPlayerNick(p.getNick());
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.CHOOSE_LEADER);
        message.setIdLeader1(p.getLeaderCards().get(c.getInitialInput()[0]-1).getID());
        message.setIdLeader2(p.getLeaderCards().get(c.getInitialInput()[1]-1).getID());
        if(cli)
        {
            c.initialResource(n);
        }
        else
        {

        }
        for (int i:c.getInitialInput()) {
            switch (i)
            {

                case 1:
                {
                    message.getResources().add(Resource.COIN);
                    break;
                }
                case 2:
                {
                    message.getResources().add(Resource.SERVANT);
                    break;
                }
                case 3:
                {
                    message.getResources().add(Resource.SHIELD);
                    break;
                }
                case 4:
                {
                    message.getResources().add(Resource.STONE);
                    break;
                }
                default:
                {}
            }
        }

    }

    public void askAction () {
        if (cli) {
            if (nickClient.equals(currentPlayer)) { //griglia 1 market 2 leader 3 produzione 4 plance 5
                c.action(p, popeSpace, nickClient, player);
                switch (c.getAction()) {
                    case 1:
                        askDev();
                        break;
                    case 2:
                        askMarket();
                        break;
                    case 3:
                        askLeader();
                        break;
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

        while (c.getAction() != 3) {
            c.depotAction(p);
            switch (c.getAction()) {
                case 1:
                    askInsert();
                    break;
                case 2:
                    askSwap();
                    break;
                default:
                    return;
            }
        }
    }

    public void askInsert(){
        Resource r;
        c.chooseInsert(p, gain);
        if(gain.isEmpty()) {
            return;
        }
        r = gain.get(c.getChooseInput()-1);
        switch (c.getDepotInput())
        {
            case 1:
                if(p.getDepot().get(0).size()>0) {
                    System.out.println("The row is full");
                    return;
                }
                p.getDepot().get(0).add(r);
                break;
            case 2:
                if(p.getDepot().get(1).size()>1) {
                    System.out.println("The row is full");
                    return;
                }
                p.getDepot().get(1).add(r);
                break;
            case 3:
                if(p.getDepot().get(2).size()>2) {
                    System.out.println("The row is full\n");
                    return;
                }
                p.getDepot().get(2).add(r);
                break;
            case 4:
                if(p.getLeaderDepots().get(0).getQuantity()>1) {
                    System.out.println("The row is full\n");
                    return;
                }
                p.getLeaderDepots().get(0).getRow().add(r);
                break;
            case 5:
                if(p.getLeaderDepots().get(0).getQuantity()>1) {
                    System.out.println("The row is full\n");
                    return;
                }
                p.getLeaderDepots().get(1).getRow().add(r);
                break;
            default:
                return;
        }
        gain.remove(c.getChooseInput()-1);
    }

   public void askSwap() {
       Resource[][] arrayDepot = new Resource[5][3];
       Resource aux;
       for (int i = 0; i < 3; i++) {
           int j = 0;
           for (Resource r : p.getDepot().get(i)) {
               arrayDepot[i][j] = r;
               j++;
           }
       }
       for (int i = 0; i < p.getLeaderDepots().size(); i++) {
           int j = 0;
           for (Resource r : p.getLeaderDepots().get(i).getRow()) {
               arrayDepot[i + 3][j] = r;
               j++;
           }
       }
       c.chooseSwap(p, arrayDepot);
       int[] c1 = cellConvert(c.getChooseInput());
       int[] c2 = cellConvert(c.getDepotInput());
       aux = arrayDepot[c1[0]][c1[1]];
       arrayDepot[c1[0]][c1[1]] = arrayDepot[c2[0]][c2[1]];
       arrayDepot[c2[0]][c2[1]] = aux;

       for (int i = 0; i < 3; i++) {
           ArrayList<Resource> a = new ArrayList<Resource>();
           for (Resource r : arrayDepot[i]) {
               if (r != null) {
                   a.add(r);
               }
           }
           p.getDepot().set(i, a);
       }

       for (int i = 0; i < p.getLeaderDepots().size(); i++) {

           ArrayList<Resource> b = new ArrayList<Resource>();
           for (Resource r : arrayDepot[i+3]) {
               if (r != null) {
                   b.add(r);
               }
               p.getLeaderDepots().get(i).setRow(b);
           }
       }
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
                r = new int[]{3, 0};
                break;
            case 7:
                r = new int[]{3, 1};
                break;
            case 8:
                r = new int[]{4, 0};
                break;
            case 9:
                r = new int[]{4, 1};
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
                case 1:
                    //messaggio di scarto
                    break;
                case 2:
                    //messaggio attivazione
                    break;
                default:
                    return;
            }
        }
    }

    public void askMarket() {
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
                if (market[c.getChooseInput()-1][i] == Resource.WHITE)
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
            c.chooseSlot(p);//testato
        int i =0, o=0;
            ArrayList<Resource> anyIn = new ArrayList<>();
            ArrayList<Resource> anyOut = new ArrayList<>();
            for(int  n : c.getDevSlotInput()) {
                if (n == 0) { //base
                    i = i + 2;
                    o = o + 1;
                }
                if (n == 4 || n == 5)
                    o = o + 1;
            }
                //anyIn
                    if (i>0)
                        c.chooseAny(i, false);
                    for (int h = 0; h < i; h++) {
                        switch (c.getAnyInput()[h]) {
                            case 1:
                                anyIn.add(Resource.COIN);
                                break;
                            case 2:
                                anyIn.add(Resource.SERVANT);
                                break;
                            case 3:
                                anyIn.add(Resource.SHIELD);
                                break;
                            case 4:
                                anyIn.add(Resource.STONE);
                                break;
                            default:
                                break;
                        }
                    }
                    if (o>0)
                        c.chooseAny(o, true);
                    for (int h = o; h < o; h++){
                    switch (c.getAnyInput()[h]) {
                        case 1:
                            anyOut.add(Resource.COIN);
                            break;
                        case 2:
                            anyOut.add(Resource.SERVANT);
                            break;
                        case 3:
                            anyOut.add(Resource.SHIELD);
                            break;
                        case 4:
                            anyOut.add(Resource.STONE);
                            break;
                        case 5:
                            anyOut.add(Resource.FAITH);
                            break;
                        default:
                            break;
                    }
                }
            productionExpense(anyIn);
            askPayment();
            return;
        }

    public void productionExpense(ArrayList<Resource> anyInput) {
        price.put(Resource.COIN, 0); //reset del prezzo a zero per ogni risorsa
        price.put(Resource.SERVANT, 0);
        price.put(Resource.SHIELD, 0);
        price.put(Resource.STONE, 0);
        for (Resource r : anyInput) //aggiunta delle risorse di anyInput al prezzo
        {
            price.put(r, price.get(r)+1);
        }
        for (int i : c.getDevSlotInput()) //per ogni devSlot vengono aggiunte le risorse delle inputResource
        {
            if(i!=6 && i!=-1) {
                ArrayList<Resource> ar = new ArrayList<>(p.getDevSlots().get(i).getInputResource()); //copia delle inputResource
                for (Resource r : ar) {
                    price.put(r, price.get(r) + 1);
                }
            }
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
                    //uso carta leader
                    if (p.getLeaderDiscount().size() >0)
                    {
                        c.discountLeader(p);
                        switch (c.getLeaderInput())
                        {
                            case 1:
                                break;
                            case 2:
                                r1 = p.getLeaderDiscount().get(0);
                                r2 = null;
                                break;
                            case 3:
                                r1 = null;
                                r2 = p.getLeaderDiscount().get(1);
                                break;
                            case 4:
                                r1 = p.getLeaderDiscount().get(0);
                                r2 = p.getLeaderDiscount().get(1);
                                break;
                            default:
                                return;
                        }

                    } //fino a qui testato
                        purchaseExpense(r1, r2);
                        askPayment();
                }

            } else {
                //exception
            }
        }

    private void purchaseExpense(Resource r1, Resource r2) {
        ArrayList<Resource> ar = visibleDevGrid.get(c.getChooseInput()-1).getCostList();//prende il costo della carta

        price.put(Resource.COIN, 0); //reset del prezzo a zero per ogni risorsa
        price.put(Resource.SERVANT, 0);
        price.put(Resource.SHIELD, 0);
        price.put(Resource.STONE, 0);

        for (Resource r : ar) {
            price.put(r, price.get(r) + 1);
        }
        if(r1 != null)
        {
            price.put(r1, price.get(r1)-1);
            if (price.get(r1)<0)
                price.put(r1,0);
        }
        if(r2 != null)
        {
            price.put(r2, price.get(r1)-1);
            if (price.get(r2)<0)
                price.put(r2,0);
        }


        c.printExpense(price);
    }

    public void askPayment()
    {
                if (cli) {
                    c.printDepot(p);
                    c.printStrongBox(p); //testato
                for (Resource r : price.keySet()) {
                    if(price.get(r)>0) {
                        c.depotTaking(r, "");
                        taking.put(r, c.getResourcesInput());
                        for (SpecialDepot sd : p.getLeaderDepots()) {
                            if (r == sd.getRes()) {
                                c.depotTaking(r, "special");
                                specialTaking.put(r,c.getResourcesInput());
                            }
                        }
                    }
                }
            }

    }

    //getters

    // metodi get

    public String getNickClient() {
        return nickClient;
    }

    public ArrayList<String> getNickList() {
        return nickList;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public HashMap<String, PlayerInformation> getPlayer() {
        return player;
    }

    public PlayerInformation getP() {
        return p;
    }

    public void showError(String text)
    {
        //fai stampare errore da cli o gui.
    }
    @Override
    public void update(Message message) {
        switch (message.getType()) {
            case UPDATE: {
                switch (message.getPlayerAction()) {
                    case LEADER_READY: {
                        p.setLeaderCards(message.getLeaderDeck());
                        //print leader,ask player which leader he want to keep then choose initial resources
                        break;
                    }

                    case CHOOSE_LEADER:// receive this and the game begin, all information in view should be initialized
                    {
                        p.setLeaderCards(message.getLeaderDeck());
                        p.setDepot(message.getDepot());
                        //game is actually started.
                        //ask first player which action he wants to play
                        askAction();
                        break;
                    }
                    case MARKET1: {
                        market = message.getMarket();
                        freeMarble = message.getFreeMarble();
                        gain = message.getResources();

                        break;
                    }
                    case MARKET2: {

                        break;
                    }
                    case PRODUCTION: {

                        break;
                    }
                    case PURCHASE: {

                        break;
                    }
                    case USE_LEADER: {

                        break;
                    }
                    case DISCARD_LEADER: {

                        break;
                    }
                    case END_TURN: {

                        break;
                    }
                    case END_ACTION: {
                        //ristampa askaction
                        break;
                    }

                }
                break;
            }

            case WIN: {
                //if mp stampa ranking,
                //else(sp) stampa testo nel messaggio
                //dare un bottone, su click chiude tutto.
                break;
            }
            case ERROR: {
                if (this.nickClient == message.getPlayerNick()) {
                    showError(message.getText());
                    //askAction();
                }
                break;
            }
        }
    }
}
