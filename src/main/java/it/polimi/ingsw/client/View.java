package it.polimi.ingsw.client;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.client.gui.Gui;
import it.polimi.ingsw.client.gui.MainController;
import it.polimi.ingsw.connection.Client;
import it.polimi.ingsw.enumeration.MessageType;
import it.polimi.ingsw.enumeration.PlayerAction;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.util.*;

/**
 * Checks inputs and prepares messages to send to the server connection.
 * It doesn't print anything, it invokes Cli / Gui for information display.
 */

public class View implements Observer<Message> {
    // visualization attributes
    private boolean cli;
    private Cli c;
    private Gui g;


    private MainController mc;
    // common informations and operation variables
    private String nickClient;//nick of player of this client
    private ArrayList<String> nickList;//ordered list of players nicknames in playerlist
    private String currentPlayer; //nick of player in turn
    private HashMap<String, PlayerInformation> playersInfo;
    private PlayerInformation p; //client player pointer
    private Client clientConnection;
    private ArrayList<DevCard> visibleDevGrid; //the cards in this list are never removed, just updated
    //when a deck goes to the server, it will be updated here with null
    private Resource[][] market;
    private Resource freeMarble;
    private boolean[] popeSpace;
    private ArrayList<Resource> marketChange;
    private HashMap<Resource,Integer> price;
    private HashMap<Resource,Integer> taking;
    private HashMap<Resource,Integer> specialTaking;
    private ArrayList<Resource> discountRes;
    private ArrayList<Resource> gain; // sent by the server after market

    //Backup // they are updated in the same way as the depot, special depot and normal gain
    ArrayList<ArrayList<Resource>> depotBackUp;
    ArrayList<Resource> gainBackUp;
    ArrayList<SpecialDepot> spDepotBackUp;


    public View(Cli c)
    {
        this.c=c;
        cli=true;
        initialization();
    }

    public View(Gui gui)
    {
        g = gui;
        cli=false;
        initialization();
    }

    //metodi get
    public void setMc(MainController mc) {
        this.mc = mc;
    }

    private void initialization() {
        market = new Resource[3][4];
        popeSpace = new boolean[3];
        playersInfo = new HashMap<>();
        price = new HashMap<>(4);
        taking = new HashMap<>(4);
        specialTaking = new HashMap<>(4);
        marketChange = new ArrayList<>();
        depotBackUp = new ArrayList<ArrayList<Resource>>();
        gainBackUp = new ArrayList<Resource>();
        spDepotBackUp = new ArrayList<SpecialDepot>();
        discountRes = new ArrayList<Resource>();
    }

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

    /**
     * Sets the client-side view.
     * @param nc Client nickname.
     * @param nl Nicknames list.
     */
    public void setPlayerView(String nc, ArrayList<String> nl)
    {
        setNickClient(nc);
        setNickList(nl);
        setCurrentPlayer(nickList.get(0));
        setPlayer();
        p = playersInfo.get(nickClient);
    }

    public void setNickList(ArrayList<String> nl) {
        nickList = new ArrayList<>(nl);
    }

    /**
     * Sets the player information registerfor each player.
     */
    public void setPlayer() {
        for (String s : nickList) {
            playersInfo.put(s, new PlayerInformation(s));
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

    /*
    public void setGameView(Resource[][] m, Resource fm, boolean[] ps, ArrayList<DevCard> deck)
    {
        setMarket(m);
        setFreeMarble(fm);
        setPopeSpace(ps);
        setVisibleDevGrid(deck);
    }*/

    public void setVisibleDevGrid(ArrayList<DevCard> deck) {
        visibleDevGrid = new ArrayList<DevCard>(deck);
    }

    public void setPopeSpace(boolean[] ps) {
        System.arraycopy(ps, 0, popeSpace, 0, 3);
    }

    /**
     * Initializes the market of this match.
     * @param market Market of resources.
     */
    public void setMarket(Resource[][] market) {
        for (int i = 0; i<3; i++) //per ognuna delle 3 righe
        {
            System.arraycopy(market[i], 0, this.market[i], 0, 4); //copia tutti e 4 gli elementi
        }
    }

    public void setGain(ArrayList<Resource> gain) {
        this.gain = new ArrayList<>(gain);
    }

    /**
     * Handles the inputs in the initialization phase.
     */
    public void askInitially()
    {
        int n = nickList.indexOf(nickClient);
        if(cli)
        {
            c.initialLeader(playersInfo.get(nickClient),nickClient, n);
        }
        else
        {
            //gui
            while (mc==null){
                try {
                    wait(100);
                }catch (Exception e){

                }

            }
            mc.initialLeader(playersInfo.get(nickClient),nickClient, n);
            while (!mc.isDone()){
                try{
                    wait(100);
                }catch (Exception e){

                }
            }
        }
        Message message=new Message();
        message.setPlayerNick(p.getNick());
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.CHOOSE_LEADER);

        message.setResources(new ArrayList<>());
        if(cli)
        {
            message.setIdLeader1(p.getLeaderCards().get(c.getInitialInput()[0]-1).getID());
            message.setIdLeader2(p.getLeaderCards().get(c.getInitialInput()[1]-1).getID());
            c.initialResource(n);
        }
        else
        {
            message.setIdLeader1(p.getLeaderCards().get(mc.getInitialInput()[0]-1).getID());
            message.setIdLeader2(p.getLeaderCards().get(mc.getInitialInput()[1]-1).getID());
            mc.initialResource(n);
        }
        int[] k;
        if(cli){
            k=c.getInitialInput();
        }else {
            k=mc.getInitialInput();
        }
        for (int i:k) {
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
        clientConnection.write(message);

    }

    /**
     * Handles the operations of the players. It shows the board for the players, the possible operation if it is
     * their turn, or the possible actions if it isn't.
     */
    public void askAction () {

        int action;


        if (nickClient.equals(currentPlayer)) { //griglia 1 market 2 leader 3 produzione 4 plance 5
            if(cli) {
                c.action(p, popeSpace, nickClient, playersInfo);
                action = c.getAction();
            } else {
                mc.action(p,popeSpace,nickClient,playersInfo);
                while (!mc.isDone()){
                    try{
                        wait(100);
                    }catch (Exception e){

                    }
                }

                action = mc.getAction();
            }


            switch (action) {
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
                    if(cli) {
                        c.seeBoards(playersInfo, popeSpace, nickClient);
                    } else {
                        mc.seeBoards();
                    }

                    askAction();
                    break;

                case 6:
                    Message message = new Message();
                    message.setPlayerNick(p.getNick());
                    message.setType(MessageType.ACTION);
                    message.setPlayerAction(PlayerAction.END_TURN);
                    clientConnection.write(message);
                    break;

                default:
                    break;
            }
        } else {

            if(cli) {
                c.watch();
                action = c.getAction();
            } else {
                mc.watch();
                while (!mc.isDone()){
                    try{
                        wait(100);
                    }catch (Exception e){

                    }
                }
                action = mc.getAction();
            }

            switch (action) {

                case 1:
                    c.printDevGrid(visibleDevGrid);
                    askAction();
                    break;

                case 2:
                    c.printMarket(market, freeMarble);
                    askAction();
                    break;

                case 3:
                    c.seeBoards(playersInfo, popeSpace, nickClient);
                    askAction();
                    break;

                default:
                    break;

            }
        }
    }

    //viene chiamato al posto di askAction per la gestione del deposito dopo il mercato.
    //invia al server il deposito, il deposito speciale e le risorse non inserite
    //Corrisponde a MARKET2

    /**
     * It is called instead of askAction to handle the management of depot after the market operation.
     * It sends to the server the depot, the special depot and the resources not yet inserted.
     * It corresponds to MARKET2.
     */
    public void askDepot() {

        //When askDepot is called, the depots and gain are reset to the last update's informations.
        //In this way, when it's an error message with wrong depot, it would be renewed.
        //p.setDepot(depotBackUp);
        //gain = new ArrayList<Resource>(gainBackUp);
        //p.setLeaderDepots(spDepotBackUp);

        int action;

        if(cli) {
            action = c.getAction();
        } else {
            action = mc.getAction();
        }

        while (action != 3) {
            if(cli) {
                c.depotAction(p);
                action = c.getAction();

            } else {
                mc.depotAction(p);
                while (!mc.isDone()){
                    try{
                        wait(100);
                    }catch (Exception e){

                    }
                }
                action = mc.getAction();
            }

            switch (action) {
                case 1:
                    askInsert();
                    break;
                case 2:
                    askSwap();
                    break;
                default:
                    break;
            }
        }

        Message message=new Message();
        message.setPlayerNick(p.getNick());
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.MARKET2);
        //informazioni messaggio
        message.setDepot(p.getDepot());
        message.setSpecialDepots(p.getLeaderDepots());
        message.setResources(gain);
        clientConnection.write(message);
    }

    /**
     * Auxiliary method of askDepot, it allows to insert an element in the depot.
     */
    public void askInsert(){

        int chooseInput;
        int depotInput;

        Resource r;
        if(cli) {
            c.chooseInsert(p, gain);
        } else {
            mc.chooseInsert(p,gain);
            while (!mc.isDone()){
                try{
                    wait(100);
                }catch (Exception e){

                }
            }
        }

        if(gain.isEmpty()) {
            return;
        }

        if(cli) {
            chooseInput = c.getChooseInput();
            depotInput = c.getDepotInput();
        } else {
            chooseInput = mc.getChooseInput();
            depotInput = mc.getDepotInput();
        }

        r = gain.get(chooseInput-1);


        switch (depotInput) {
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
        gain.remove(chooseInput-1);

    }

    /**
     * Auxiliary method of askDepot, it allows to swap two cells of the depot by converting it in a matrix firstly.
     */
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

        int[] c1;
        int[] c2;

        if(cli) {
            c.chooseSwap(p, arrayDepot);
            c1 = cellConvert(c.getChooseInput());
            c2 = cellConvert(c.getDepotInput());

        } else {

            //gui
            mc.chooseSwap(p);
            while (!mc.isDone()){
                try{
                    wait(100);
                }catch (Exception e){

                }
            }

            c1 = cellConvert(mc.getChooseInput());
            c2 = cellConvert(mc.getDepotInput());
        }

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

    /**
     * Auxiliary method of askSwap, it converts the player's input into cell indexes.
     * @param c Number that the player inputs.
     * @return Cell indexes.
     */
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

    /**
     * Corresponds to useLeader and discardLeader.
     * It asks which cards the player wants to activate or discard.
     */
    private void askLeader() {

        int chooseInput;
        int leaderInput;

        if (cli)
        {
            c.chooseLeader(p, nickClient);
            if (c.getLeaderInput() == 0 || c.getLeaderInput()-1 > p.getLeaderCards().size())
            {   askAction();
                return;
            }//if size is 2, leaderinput can be MAX 3
            c.interactLeader();
            chooseInput = c.getChooseInput();
            leaderInput = c.getLeaderInput();

        } else{
            //gui
            mc.chooseLeader(p, nickClient);
            while (!mc.isDone()){
                try{
                    wait(100);
                }catch (Exception e){

                }
            }

            if(mc.getLeaderInput() == 0 || mc.getLeaderInput()-1 > p.getLeaderCards().size()) {
                askAction();
                return;
            }
            mc.interactLeader();
            while (!mc.isDone()){
                try{
                    wait(100);
                }catch (Exception e){

                }
            }
            chooseInput = mc.getChooseInput();
            leaderInput = mc.getLeaderInput();

        }

        Message message = new Message();
        switch (chooseInput) {
            case 1:
                message = new Message();
                message.setPlayerNick(p.getNick());
                message.setType(MessageType.ACTION);
                message.setPlayerAction(PlayerAction.DISCARD_LEADER);
                // information sent
                message.setIdLeader1(p.getLeaderCards().get(leaderInput - 1).getID());
                break;
            case 2:
                message = new Message();
                message.setPlayerNick(p.getNick());
                message.setType(MessageType.ACTION);
                message.setPlayerAction(PlayerAction.USE_LEADER);
                //informazioni mandate
                message.setIdLeader1(p.getLeaderCards().get(leaderInput - 1).getID());
                break;
            default:
                return;
        }
        clientConnection.write(message);
    }

    /**
     * Asks the player to choose the market resources.
     * It corresponds to Market1.
     */
    public void askMarket() {
        int w=0;
        int i;
        marketChange=new ArrayList<>();

        int chooseInput;

        if (cli) {
            c.chooseMarket(market, freeMarble);
            chooseInput = c.getChooseInput();
        } else {
            mc.chooseMarket(market, freeMarble);
            while (!mc.isDone()){
                try {
                    wait(100);
                }catch (Exception e){

                }
            }
            chooseInput = mc.getChooseInput();
        }

        if (chooseInput > 3) { //column
            for(i = 0; i<3; i++) { //3 elements

                if (market[i][chooseInput-4] == Resource.WHITE) w++;
            }

        } else if (chooseInput >0) {
            for(i = 0; i<4; i++) {
                if (market[chooseInput-1][i] == Resource.WHITE) w++;
            }

        } else{
            askAction();
            return;
        }

        switch(p.getLeaderMarket().size()) {
            /*case 0:
                   for (i = 0; i < w; i++) {
                       marketChange.add(null);
                   }
                   break;*/
            case 1:
                for (i = 0; i < w; i++) {
                    marketChange.add(p.getLeaderMarket().get(0));
                }
                break;

            case 2:
                if(cli) {
                    c.marketLeader(w, p);
                    for (int h : c.getExchangeInput()) {
                        marketChange.add(p.getLeaderMarket().get(h)); //non c'è bisogno di mettere -1
                    }
                } else {
                    mc.marketLeader(w, p);
                    while (!mc.isDone()){
                        try{
                            wait(100);
                        }catch (Exception e){

                        }
                    }

                    for (int h : mc.getExchangeInput()) {
                        marketChange.add(p.getLeaderMarket().get(h));
                    }
                }
                break;

            default:
                break;

        }

        if(cli) {
            chooseInput = c.getChooseInput();
        } else {
            chooseInput = mc.getChooseInput();
        }

        Message message = new Message();
        message.setPlayerNick(p.getNick());
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.MARKET1);
        // information sent:
        message.setRowCol(chooseInput-1);
        message.setResources(marketChange);
        clientConnection.write(message);
    }



    /**
     * Corresponds to PRODUCTION.
     */
    public void askSlot(){

        int i =0, o=0;
        ArrayList<Resource> anyIn = new ArrayList<>();
        ArrayList<Resource> anyOut = new ArrayList<>();
        ArrayList<Integer> slot = new ArrayList<>();

        int[] devSlotInput;
        int[] anyInput;


        if(cli) {
            c.chooseSlot(p);
            devSlotInput = c.getDevSlotInput();
        } else {
            mc.chooseSlot(p);
            while (!mc.isDone()){
                try{
                    wait(100);
                }catch (Exception e){

                }
            }
            devSlotInput = mc.getDevSlotInput();

        }

        for (int n : devSlotInput) {
            if (n == 0) { //base
                i = i + 2;
                o = o + 1;
            }
            if (n == 4 || n == 5)
                o = o + 1;

            if(n!=6 && n!=-1)
            {
                slot.add(n);
            }
        }

        //anyIn
        if (i > 0) {
            if(cli) {
                c.chooseAny(i, false);
                anyInput = c.getAnyInput();
            } else {
                mc.chooseAny(i, false);
                while (!mc.isDone()){
                    try{
                        wait(100);
                    }catch (Exception e){

                    }
                }
                anyInput = mc.getAnyInput();
            }
        } else {
            anyInput = null;
        }

        for (int h = 0; h < i; h++) {
            switch (anyInput[h]) {
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

        if (o > 0) {
            if(cli) {
                c.chooseAny(o, true);
                anyInput = c.getAnyInput();
            } else {
                mc.chooseAny(o, true);
                anyInput = mc.getAnyInput();
            }
        }

        for (int h = 0; h < o; h++) {
            switch (anyInput[h]) {
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


        Message message = new Message();
        message.setPlayerNick(p.getNick());
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.PRODUCTION);
        // information sent
        message.setProductionSlots(slot);
        message.setPaymentDepot(taking);
        message.setPaymentLeader(specialTaking);
        message.setExtraInput(anyIn);
        message.setExtraOutput(anyOut);
        clientConnection.write(message);
    }


    /**
     * Auxiliary method of askSlot.
     * @param anyInput Array of AnyResource.
     */
    public void productionExpense(ArrayList<Resource> anyInput) {
        price.put(Resource.COIN, 0); // resets the price of each resource to 0
        price.put(Resource.SERVANT, 0);
        price.put(Resource.SHIELD, 0);
        price.put(Resource.STONE, 0);
        for (Resource r : anyInput) // add the anyInput resources to the price
        {
            price.put(r, price.get(r)+1);
        }

        if(cli) {
            for (int i : c.getDevSlotInput()) // for each devSlot, the resources of inputResources are added
            {
                if(i!=6 && i!=-1) {
                    ArrayList<Resource> ar = new ArrayList<>(p.getDevSlots().get(i).getInputResource()); //copy of inputResource
                    for (Resource r : ar) {
                        price.put(r, price.get(r) + 1);
                    }
                }
            }
            c.printExpense(price);
        } else {
            for (int i : mc.getDevSlotInput()) // for each devSlot, the resources of inputResources are added
            {
                if(i!=6 && i!=-1) {
                    ArrayList<Resource> ar = new ArrayList<>(p.getDevSlots().get(i).getInputResource()); //copy of inputResource
                    for (Resource r : ar) {
                        price.put(r, price.get(r) + 1);
                    }
                }
            }
            mc.printExpense(price);
        }

    }

    /**
     * Corresponds to PURCHASE.
     */
    public void askDev()
    {
        discountRes = new ArrayList<>();

        int chooseInput;
        int leaderInput;
        int positionInput;

        if(cli) {
            c.chooseDev(visibleDevGrid);
            chooseInput = c.getChooseInput();
        } else {
            mc.chooseDev(visibleDevGrid);
            while (!mc.isDone()){
                try{
                    wait(100);
                }catch (Exception e){

                }
            }

            chooseInput = mc.getChooseInput();
        }

        if (chooseInput == 0) {
            askAction();
            return;

        } else if (chooseInput > 0 && chooseInput < 13) {
            if (visibleDevGrid.get(chooseInput - 1) == null) {
                System.out.println("Deck is void");
                askAction();
                return;
            }

            if(cli) {
                c.position();
            } else {
                mc.position();
                while (!mc.isDone()){
                    try{
                        wait(100);
                    }catch (Exception e){

                    }
                }
            }

            //use carta leader
            if (p.getLeaderDiscount().size() > 0) {

                if(cli) {
                    c.discountLeader(p);
                    leaderInput = c.getLeaderInput();
                } else {
                    mc.discountLeader(p);
                    while (!mc.isDone()){
                        try{
                            wait(100);
                        }catch (Exception e){

                        }
                    }
                    leaderInput = mc.getLeaderInput();
                }


                switch (leaderInput) {
                    case 1:
                        break;
                    case 2:
                        discountRes.add(p.getLeaderDiscount().get(0));
                        break;
                    case 3:
                        discountRes.add(p.getLeaderDiscount().get(1));
                        break;
                    case 4:
                        discountRes.add(p.getLeaderDiscount().get(0));
                        discountRes.add(p.getLeaderDiscount().get(1));
                        break;
                    default:
                        break;
                }

            }
            purchaseExpense();
            askPayment();

        } else {
            askAction();
            return;
        }


        if(cli) {
            chooseInput = c.getChooseInput();
            positionInput = c.getPositionInput();
        } else {
            chooseInput = mc.getChooseInput();
            positionInput = mc.getPositionInput();
        }

        Message message = new Message();
        message.setPlayerNick(p.getNick());
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.PURCHASE);
        //information sent
        message.setDevCardId(visibleDevGrid.get(chooseInput - 1).getId());
        message.setSlotToInsert(positionInput); //from 1 to 3
        message.setPaymentDepot(taking);
        message.setPaymentLeader(specialTaking);
        message.setResources(discountRes);
        clientConnection.write(message);

    }


    /**
     * Auxiliary method to obtain prices.
     */
    private void purchaseExpense() {

        int chooseInput;

        if(cli) {
            chooseInput = c.getChooseInput();
        } else {
            chooseInput = mc.getChooseInput();
        }

        ArrayList<Resource> ar = visibleDevGrid.get(chooseInput-1).getCostList(); // takes the card price
        price.put(Resource.COIN, 0); //resets the price to 0 for each resource
        price.put(Resource.SERVANT, 0);
        price.put(Resource.SHIELD, 0);
        price.put(Resource.STONE, 0);

        for (Resource r : ar) {
            price.put(r, price.get(r) + 1);
        }
        for (Resource r : discountRes)
        {
            price.put(r, price.get(r)-1);
            if (price.get(r)<0)
                price.put(r,0);
        }

        if(cli) {
            c.printExpense(price);
        } else {
            mc.printExpense(price);
        }

    }

    /**
     * Auxiliary method to take resources from depots.
     */
    public void askPayment()
    {
        taking=createEmptyPaymentHash();
        specialTaking=createEmptyPaymentHash();

        if (cli) {
            c.printDepot(p);
            c.printStrongBox(p); //tested
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
        } else {
            mc.printDepot(p);
            mc.printStrongBox(p);
            for (Resource r : price.keySet()) {
                if(price.get(r)>0) {
                    mc.depotTaking(r, "");
                    while (!mc.isDone()){
                        try{
                            wait(100);
                        }catch (Exception e){

                        }
                    }
                    taking.put(r, mc.getResourcesInput());
                    for (SpecialDepot sd : p.getLeaderDepots()) {
                        if (r == sd.getRes()) {
                            mc.depotTaking(r, "special");
                            while (!mc.isDone()){
                                try{
                                    wait(100);
                                }catch (Exception e){

                                }
                            }
                            specialTaking.put(r,mc.getResourcesInput());
                        }
                    }
                }
            }
        }
        CardSlot a=(CardSlot)getPlayersInfo().get(getNickClient()).getDevSlots().get(1);
        if(!a.getDevCards().empty()){
            System.out.println(a.getDevCards().peek().getId());
        }
        else {
            System.out.println("empty");
        }

    }

    private HashMap<Resource,Integer> createEmptyPaymentHash()
    {
        HashMap<Resource,Integer> payment=new HashMap<>();
        payment.put(Resource.COIN,0);
        payment.put(Resource.SERVANT,0);
        payment.put(Resource.SHIELD,0);
        payment.put(Resource.STONE,0);
        return payment;
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

    public HashMap<String, PlayerInformation> getPlayersInfo() {
        return playersInfo;
    }

    public PlayerInformation getP() {
        return p;
    }

    /**
     * Prints errors through Cli or Gui.
     * @param text Error message to print.
     */
    public void showError(String text)
    {
        //fai stampare errore da cli o gui.

        printText("Error: " + text);

    }

    /**
     * Prints Lorenzo messages through Cli or Gui.
     * @param text Lorenzo message to print.
     */
    public void showLorenzoMessage(String text)
    {

        printText("Lorenzo: " + text);

    }

    /*
    public void showServerText()//prints server text and ask for a input.
    {

    }*/

    /**
     * Handles the messages received from the server in different situations.
     * {@inheritDoc}
     * @param message Message received from the server.
     */
    @Override
    public void update(Message message) {
        switch (message.getType()) {
            case SERVER:
            {
                if(message.isNeedReply())
                {
                    printAndReplyServer(message.getText());
                }
                else
                {
                    printText(message.getText());
                }

                break;
            }
            case UPDATE: {
                switch (message.getPlayerAction()) {
                    case LEADER_READY: {
                        p.setLeaderCards(message.getLeaderDeck());
                        //print leader,ask player which leader he want to keep then choose initial resources
                        askInitially();
                        break;
                    }
                    //this is initialization of game
                    case INIT:
                    {
                        setPlayerView(nickClient, message.getPlayerNickList());
                        setMarket(message.getMarket());
                        setVisibleDevGrid(message.getDevDeck());
                        setFreeMarble(message.getFreeMarble());
                        break;
                    }
                    case CHOOSE_LEADER:
                    {
                        playersInfo.get(message.getPlayerNick()).setLeaderCards(message.getLeaderDeck());
                        playersInfo.get(message.getPlayerNick()).setDepot(message.getDepot());
                        break;
                    }
                    case MARKET1: {
                        System.out.println("MARKET 1 UPDATE RECEIVED");
                        market = message.getMarket();
                        freeMarble = message.getFreeMarble();
                        gain = message.getResources();
                        setPopeFavor(message.getPopeFavor());
                        setPopeSpace(message.getPopeSpace());
                        setFaith(message.getFaithTrack());
                        askDepot();

                        break;
                    }
                    case MARKET2: {
                        System.out.println("MARKET 2 UPDATE RECEIVED");
                        playersInfo.get(message.getPlayerNick()).setDepot(message.getDepot());
                        playersInfo.get(message.getPlayerNick()).setLeaderDepots(message.getSpecialDepots());
                        setPopeFavor(message.getPopeFavor());
                        setPopeSpace(message.getPopeSpace());
                        setFaith(message.getFaithTrack());
                        break;
                    }
                    case PRODUCTION: {
                        System.out.println("PROD UPDATE RECEIVED");
                        playersInfo.get(message.getPlayerNick()).setDepot(message.getDepot());
                        playersInfo.get(message.getPlayerNick()).setLeaderDepots(message.getSpecialDepots());
                        playersInfo.get(message.getPlayerNick()).setStrongBox(message.getStrongBox());
                        setPopeFavor(message.getPopeFavor());
                        setPopeSpace(message.getPopeSpace());
                        setFaith(message.getFaithTrack());

                        break;
                    }
                    case PURCHASE: {
                        System.out.println("PURC UPDATE RECEIVED");
                        playersInfo.get(message.getPlayerNick()).setDepot(message.getDepot());
                        playersInfo.get(message.getPlayerNick()).setLeaderDepots(message.getSpecialDepots());
                        playersInfo.get(message.getPlayerNick()).setStrongBox(message.getStrongBox());
                        playersInfo.get(message.getPlayerNick()).setDevSlots(message.getDevSlots());
                        setVisibleDevGrid(message.getDevDeck());
                        break;
                    }
                    case USE_LEADER: {
                        System.out.println("USE UPDATE RECEIVED");
                        playersInfo.get(message.getPlayerNick()).setLeaderCards(message.getLeaderDeck());
                        playersInfo.get(message.getPlayerNick()).setLeaderDepots(message.getSpecialDepots());
                        playersInfo.get(message.getPlayerNick()).setDevSlots(message.getDevSlots());
                        playersInfo.get(message.getPlayerNick()).setLeaderMarket(message.getMarketDiscount());
                        playersInfo.get(message.getPlayerNick()).setLeaderDiscount(message.getDevDiscount());
                        break;
                    }
                    case DISCARD_LEADER: {
                        System.out.println("DISCARD UPDATE RECEIVED");
                        playersInfo.get(message.getPlayerNick()).setLeaderCards(message.getLeaderDeck());
                        setFaith(message.getFaithTrack());
                        setPopeSpace(popeSpace);
                        setPopeFavor(message.getPopeFavor());
                        break;
                    }
                    case END_TURN: {
                        System.out.println("ENDTURN UPDATE RECEIVED");
                        setCurrentPlayer(message.getCurrentPlayer());
                        askAction();
                        break;
                    }
                    case END_ACTION: {
                        //System.out.println("ENDACTION UPDATE RECEIVED");
                        askAction();
                        break;
                    }
                }
                break;
            }
            case LORENZO:
            {
                showLorenzoMessage(message.getText());
                break;
            }

            case WIN: {
                if(nickList.size()!=1)
                {
                    printText(message.getText());
                }
                else {
                    if(cli) {
                        c.printRanking(message.getRanking(), nickList);
                    } else {
                        mc.printRanking(message.getRanking(), nickList);
                    }

                }

                break;
            }
            case ERROR: {
                setMarket(message.getMarket());
                setFreeMarble(freeMarble);
                playersInfo.get(message.getPlayerNick()).setDepot(message.getDepot());
                setFaith(message.getFaithTrack());
                setPopeSpace(popeSpace);
                setPopeFavor(message.getPopeFavor());
                if (this.nickClient.equals(message.getPlayerNick())) {
                    showError(message.getText());
                    askAction();
                }
                break;
            }
        }
    }

    private void setFaith(ArrayList<Integer> positions)
    {
        for(int i=0;i<nickList.size();i++)
        {
            playersInfo.get(nickList.get(i)).setPosition(positions.get(i));
        }
    }


    /**
     * Initializes the client-side connection.
     * @param ip Server IP address.
     * @param port Server port.
     */
    public void start(String ip, String port)
    {

        boolean ok=false;

        if(cli) {
            while(!ok) {


                ip= c.askIp();
                port=c.askPort();

                try {

                    clientConnection = new Client(ip, Integer.parseInt(port));
                    clientConnection.addObserver(this);
                    clientConnection.start();
                    ok = true;

                } catch (Exception e) {
                    printText("Invalid input");
                }
            }
        } else {

            try {
                boolean done = false;

                clientConnection = new Client(ip, Integer.parseInt(port));
                clientConnection.addObserver(this);


                new Thread(() -> {
                    try {
                        clientConnection.start();

                    } catch (IOException e) {
                        g.printText("Invalid input");
                    }
                }).start();

            } catch (Exception e) {
                printText("Invalid input");
            }
        }

    }
    private void printAndReplyServer(String text)
    {
        String answer="";
        if(cli)
        {

            answer=c.printAndReply(text);
            if(text.equals("Welcome! What's your name?"))
            {
                nickClient=answer;
            }

            clientConnection.write(createServerTextMessage(answer));
        } else {
            g.printText(text);
        }

    }

    /**
     * Creates a Message object with type=SERVER.
     * @param text string to convert into a Message.
     * @return the Message created.
     */
    public Message createServerTextMessage(String text)
    {
        Message message=new Message();
        message.setText(text);
        message.setType(MessageType.SERVER);
        return message;
    }
    private void printText(String text)
    {
        if(cli)
        {
            c.printText(text);
        } else {
            g.printText(text);
        }
    }

    private void setPopeFavor(boolean[][] popeFavor)
    {
        for (int i=0;i<nickList.size();i++)
        {
            playersInfo.get(nickList.get(i)).setPopeFavor(popeFavor[i]);
        }
    }

    public void sendMessage(Message m) {
        clientConnection.write(m);
    }

    public void setNickname(String name) {
        nickClient = name;
    }



}
