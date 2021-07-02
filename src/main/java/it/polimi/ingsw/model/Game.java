package it.polimi.ingsw.model;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.enumeration.*;

import java.util.*;

/**
 * Class that contains all information of a game
 */
public class Game extends Observable<Message> {

    //state
    private ArrayList<Player> playerList;
    private Stack<DevCard>[] devGrid;
    private ArrayList<LorenzoCard> lorenzoDeck;
    private Stack<LeaderCard> leaderDeck;
    private Resource[][] market;
    private Resource freeMarble;
    private Player currentPlayer; //oggetto player che punta il player corrente a partire dal primo nella lista playerList
    private boolean[] popeSpace = {true, true, true}; // true indica che il favore papale è attivabile
    private int lorenzoLocation; //indica la posizione di Lorenzo sul percorso Fede
    private boolean readyLeader;
    private boolean lastRound;

    //constructor

    /**
     * Creates a Game with given name list, also generating information such as market and all cards
     * @param p name list of all player who's playing this game
     */
    public Game(ArrayList<Player> p)
    {
        this.playerList = new ArrayList<Player>(p); //la classe che crea i giocatori passa poi la lista (senza Lorenzo) all'oggetto game
        currentPlayer = playerList.get(0);
        lastRound=false;
        marketGenerator();
        leaderDeckGenerator();
        faithTrackGenerator();
        devCardGenerator();
        lorenzoDeckGenerator();
    }

    //getter and setter
    public Player getPlayer (int id)
    {
        return playerList.get(id);

    }
    public int getIndexPlayer (Player p)
    {
        return playerList.indexOf(p);
    }
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    public Player getCurrentP ()
    {
        return currentPlayer;
    }
    public Stack<DevCard>[] getDevGrid()
    {
        return devGrid;
    }
    public int getLorenzoLocation()
    {
        return lorenzoLocation;
    }
    public DevCard getDevCard(int idmazzo)
    {
        return devGrid[idmazzo].peek();
    }
    public ArrayList<Resource> getRow (int j) //restituisce la riga scelta
    {
        ArrayList<Resource> chosenRow = new ArrayList<Resource>(4);
        for (int i = 0; i<4; i++)
        {
            chosenRow.add(i,this.market[j][i]);
        }
        return  chosenRow;
    }
    public ArrayList<Resource> getCol (int k) //restituisce la colonna scelta
    {
        ArrayList<Resource> chosenCol = new ArrayList<Resource>(3);
        for (int i = 0; i<3; i++)
        {
            chosenCol.add(i,this.market[i][k]);
        }
        return  chosenCol;
    }
    public int getLocationPlayer(int id)
    {
        return  playerList.get(id).getFaithLocation();
    }
    public boolean getPope (int n)
    {
        return popeSpace[n];
    }
    public Resource getFreeMarble ()
    {
        return freeMarble;
    }
    public Player getPlayerByNick(String nickname)
    {
        for(Player p : playerList)
        {
            if (p.getNickname().equals(nickname))
            {
                return p;
            }
        }
        return null;
    }
    public boolean isReadyLeader() {
        return readyLeader;
    }
    public void setReadyLeader(boolean readyLeader) {
        this.readyLeader = readyLeader;
    }
    public boolean isLastRound() {
        return lastRound;
    }
    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }
    public ArrayList<LorenzoCard> getLorenzoDeck() {
        return lorenzoDeck;
    }
    public void setLorenzoDeck(ArrayList<LorenzoCard> lorenzoDeck) {
        this.lorenzoDeck = lorenzoDeck;
    }
    public boolean[] getPopeSpace() {
        return popeSpace;
    }
    public Resource[][] getMarket() {
        return market;
    }
    public void setMarket(Resource[][] market)
    {
        this.market=market;
    }
    public void setFreeMarble(Resource freemarble)
    {
        this.freeMarble=freemarble;
    }
    private ArrayList<String> getPlayerNickList()
    {
        ArrayList<String> temp=new ArrayList<>();
        for(Player p: playerList)
        {
            temp.add(p.getNickname());
        }
        return temp;
    }
    public Stack<LeaderCard> getLeaderDeck()
    {
        return leaderDeck;
    }
    //generators

    /**
     * Gnerates all Development card with default values then insert them into an Array made out of Stacks of cards
     */
    private void devCardGenerator() //genera tutte le carte sviluppo con i valori di default e le inserisce in un array di 12 stack da 4 DevCard ciascuno
    {
        devGrid = new Stack[12];
        //lv 1

        //Green
        devGrid[0] = new Stack<DevCard>();

        ArrayList<DevCard> carte = new ArrayList<DevCard>();
        devGrid[0].push(new DevCard(4, Level.LV1, Color.GREEN, 2, Resource.STONE, Resource.SHIELD, Resource.SERVANT));
        devGrid[0].peek().setProductInputList(Resource.STONE);
        devGrid[0].peek().setProductOutputList(Resource.SERVANT);

        devGrid[0].push(new DevCard(0, Level.LV1, Color.GREEN, 1, Resource.SHIELD, Resource.SHIELD));
        devGrid[0].peek().setProductInputList(Resource.COIN);
        devGrid[0].peek().setProductOutputList(Resource.FAITH);

        devGrid[0].push(new DevCard(12,Level.LV1, Color.GREEN, 4,Resource.SHIELD,Resource.SHIELD,Resource.COIN,Resource.COIN ));
        devGrid[0].peek().setProductInputList(Resource.STONE, Resource.SERVANT);
        devGrid[0].peek().setProductOutputList(Resource.COIN,Resource.COIN, Resource.FAITH);

        devGrid[0].push(new DevCard(8,Level.LV1, Color.GREEN, 3, Resource.SHIELD,Resource.SHIELD, Resource.SHIELD));
        devGrid[0].peek().setProductInputList(Resource.SERVANT, Resource.SERVANT);
        devGrid[0].peek().setProductOutputList(Resource.COIN, Resource.SHIELD, Resource.STONE);
        //lv 1

        //Blue
        devGrid[1] = new Stack<DevCard>();

        devGrid[1].push(new DevCard(10, Level.LV1, Color.BLUE, 3, Resource.COIN, Resource.COIN,Resource.COIN));
        devGrid[1].peek().setProductInputList(Resource.STONE, Resource.STONE);
        devGrid[1].peek().setProductOutputList(Resource.COIN, Resource.SERVANT,Resource.SHIELD);

        devGrid[1].push(new DevCard(2, Level.LV1, Color.BLUE, 1, Resource.COIN,Resource.COIN));
        devGrid[1].peek().setProductInputList(Resource.SHIELD);
        devGrid[1].peek().setProductOutputList(Resource.FAITH);

        devGrid[1].push(new DevCard(14,Level.LV1, Color.BLUE, 4, Resource.COIN, Resource.COIN, Resource.SERVANT, Resource.SERVANT));
        devGrid[1].peek().setProductInputList(Resource.SHIELD, Resource.STONE);
        devGrid[1].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.FAITH);

        devGrid[1].push(new DevCard(6,Level.LV1, Color.BLUE,2, Resource.COIN, Resource.SERVANT, Resource.STONE));
        devGrid[1].peek().setProductInputList(Resource.SERVANT);
        devGrid[1].peek().setProductOutputList(Resource.STONE);
        //lv 1

        //Yellow
        devGrid[2] = new Stack<DevCard>();

        devGrid[2].push(new DevCard(11, Level.LV1, Color.YELLOW, 3, Resource.STONE, Resource.STONE, Resource.STONE));
        devGrid[2].peek().setProductInputList(Resource.SHIELD, Resource.SHIELD);
        devGrid[2].peek().setProductOutputList(Resource.COIN, Resource.SERVANT,Resource.STONE);

        devGrid[2].push(new DevCard(3, Level.LV1, Color.YELLOW, 1, Resource.STONE,Resource.STONE));
        devGrid[2].peek().setProductInputList(Resource.SERVANT);
        devGrid[2].peek().setProductOutputList(Resource.FAITH);

        devGrid[2].push(new DevCard(15,Level.LV1, Color.YELLOW, 4, Resource.STONE,Resource.STONE,Resource.SHIELD,Resource.SHIELD ));
        devGrid[2].peek().setProductInputList(Resource.COIN, Resource.SERVANT);
        devGrid[2].peek().setProductOutputList(Resource.SHIELD, Resource.SHIELD,Resource.FAITH);

        devGrid[2].push(new DevCard(7,Level.LV1, Color.YELLOW, 2, Resource.SHIELD,Resource.STONE,Resource.COIN));
        devGrid[2].peek().setProductInputList(Resource.SHIELD);
        devGrid[2].peek().setProductOutputList(Resource.COIN);

        //purple
        devGrid[3] = new Stack<DevCard>();

        devGrid[3].push(new DevCard(9, Level.LV1, Color.PURPLE, 3, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[3].peek().setProductInputList(Resource.COIN, Resource.COIN);
        devGrid[3].peek().setProductOutputList(Resource.SERVANT, Resource.SHIELD, Resource.STONE);

        devGrid[3].push(new DevCard(13, Level.LV1, Color.PURPLE, 4, Resource.SERVANT, Resource.SERVANT, Resource.STONE, Resource.STONE));
        devGrid[3].peek().setProductInputList(Resource.COIN,Resource.SHIELD);
        devGrid[3].peek().setProductOutputList(Resource.STONE, Resource.STONE, Resource.FAITH);

        devGrid[3].push(new DevCard(5,Level.LV1, Color.PURPLE, 2, Resource.SHIELD,Resource.SERVANT,Resource.COIN));
        devGrid[3].peek().setProductInputList(Resource.COIN);
        devGrid[3].peek().setProductOutputList(Resource.SHIELD);

        devGrid[3].push(new DevCard(1,Level.LV1, Color.PURPLE, 1, Resource.SERVANT, Resource.SERVANT));
        devGrid[3].peek().setProductInputList(Resource.STONE);
        devGrid[3].peek().setProductOutputList(Resource.FAITH);

        //LV2
        //green

        devGrid[4] = new Stack<DevCard>();

        devGrid[4].push(new DevCard(16, Level.LV2,Color.GREEN, 5, Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD));
        devGrid[4].peek().setProductInputList(Resource.STONE);
        devGrid[4].peek().setProductOutputList(Resource.FAITH,Resource.FAITH);

        devGrid[4].push(new DevCard(20, Level.LV2,Color.GREEN, 6, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SERVANT, Resource.SERVANT));
        devGrid[4].peek().setProductInputList(Resource.SHIELD, Resource.SERVANT);
        devGrid[4].peek().setProductOutputList(Resource.STONE, Resource.STONE, Resource.STONE);

        devGrid[4].push(new DevCard(24,Level.LV2,Color.GREEN, 7, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD));
        devGrid[4].peek().setProductInputList(Resource.COIN, Resource.COIN);
        devGrid[4].peek().setProductOutputList(Resource.STONE, Resource.STONE, Resource.FAITH, Resource.FAITH);

        devGrid[4].push(new DevCard(28,Level.LV2,Color.GREEN,8, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.COIN, Resource.COIN, Resource.COIN));
        devGrid[4].peek().setProductInputList(Resource.COIN);
        devGrid[4].peek().setProductOutputList(Resource.SHIELD, Resource.SHIELD, Resource.FAITH);

        //blue
        devGrid[5] = new Stack<DevCard>();

        devGrid[5].push(new DevCard(18, Level.LV2,Color.BLUE,5, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN ));
        devGrid[5].peek().setProductInputList(Resource.SERVANT);
        devGrid[5].peek().setProductOutputList(Resource.FAITH, Resource.FAITH);

        devGrid[5].push(new DevCard(22, Level.LV2,Color.BLUE, 6, Resource.COIN, Resource.COIN, Resource.COIN, Resource.STONE, Resource.STONE));
        devGrid[5].peek().setProductInputList(Resource.COIN, Resource.STONE);
        devGrid[5].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.SERVANT);

        devGrid[5].push(new DevCard(26,Level.LV2,Color.BLUE, 7, Resource.COIN,Resource.COIN,Resource.COIN,Resource.COIN,Resource.COIN));
        devGrid[5].peek().setProductInputList(Resource.SERVANT, Resource.SERVANT);
        devGrid[5].peek().setProductOutputList(Resource.SHIELD,Resource.SHIELD,Resource.FAITH,Resource.FAITH);

        devGrid[5].push(new DevCard(30,Level.LV2,Color.BLUE, 8, Resource.COIN, Resource.COIN, Resource.COIN, Resource.STONE, Resource.STONE, Resource.STONE));
        devGrid[5].peek().setProductInputList(Resource.SERVANT);
        devGrid[5].peek().setProductOutputList(Resource.STONE, Resource.STONE, Resource.FAITH);

        //yellow
        devGrid[6] = new Stack<DevCard>();

        devGrid[6].push(new DevCard(19, Level.LV2,Color.YELLOW, 5, Resource.STONE,Resource.STONE,Resource.STONE,Resource.STONE));
        devGrid[6].peek().setProductInputList(Resource.SHIELD);
        devGrid[6].peek().setProductOutputList(Resource.FAITH, Resource.FAITH);

        devGrid[6].push(new DevCard(23, Level.LV2,Color.YELLOW, 6, Resource.STONE,Resource.STONE,Resource.STONE,Resource.SHIELD,Resource.SHIELD));
        devGrid[6].peek().setProductInputList(Resource.STONE, Resource.SHIELD);
        devGrid[6].peek().setProductOutputList(Resource.COIN,Resource.COIN,Resource.COIN);

        devGrid[6].push(new DevCard(27,Level.LV2,Color.YELLOW, 7, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE));
        devGrid[6].peek().setProductInputList(Resource.SHIELD, Resource.SHIELD);
        devGrid[6].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.FAITH, Resource.FAITH);

        devGrid[6].push(new DevCard(31,Level.LV2,Color.YELLOW, 8, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[6].peek().setProductInputList(Resource.SHIELD);
        devGrid[6].peek().setProductOutputList(Resource.COIN, Resource.COIN, Resource.FAITH);

        //purple
        devGrid[7] = new Stack<DevCard>();

        devGrid[7].push(new DevCard(29, Level.LV2,Color.PURPLE, 8, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD));
        devGrid[7].peek().setProductInputList(Resource.STONE);
        devGrid[7].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.FAITH);

        devGrid[7].push(new DevCard(21, Level.LV2,Color.PURPLE, 6, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.COIN, Resource.COIN));
        devGrid[7].peek().setProductInputList(Resource.SERVANT, Resource.COIN);
        devGrid[7].peek().setProductOutputList(Resource.SHIELD, Resource.SHIELD, Resource.SHIELD);

        devGrid[7].push(new DevCard(25,Level.LV2,Color.PURPLE, 7, Resource.SERVANT,Resource.SERVANT,Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[7].peek().setProductInputList(Resource.STONE, Resource.STONE);
        devGrid[7].peek().setProductOutputList(Resource.COIN, Resource.COIN, Resource.FAITH, Resource.FAITH);

        devGrid[7].push(new DevCard(17,Level.LV2,Color.PURPLE, 5, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[7].peek().setProductInputList(Resource.COIN);
        devGrid[7].peek().setProductOutputList(Resource.FAITH, Resource.FAITH);

        //lv3
        //green
        devGrid[8] = new Stack<DevCard>();

        devGrid[8].push(new DevCard(32, Level.LV3,Color.GREEN, 9, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD));
        devGrid[8].peek().setProductInputList(Resource.COIN, Resource.COIN);
        devGrid[8].peek().setProductOutputList(Resource.STONE, Resource.STONE, Resource.STONE, Resource.FAITH, Resource.FAITH);

        devGrid[8].push(new DevCard(36, Level.LV3,Color.GREEN, 10, Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD, Resource.SERVANT, Resource.SERVANT));
        devGrid[8].peek().setProductInputList(Resource.COIN, Resource.SERVANT);
        devGrid[8].peek().setProductOutputList(Resource.SHIELD, Resource.SHIELD, Resource.STONE, Resource.STONE, Resource.FAITH);

        devGrid[8].push(new DevCard(40, Level.LV3,Color.GREEN, 11, Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD));
        devGrid[8].peek().setProductInputList(Resource.SERVANT);
        devGrid[8].peek().setProductOutputList(Resource.COIN, Resource.FAITH, Resource.FAITH, Resource.FAITH);

        devGrid[8].push(new DevCard(44, Level.LV3,Color.GREEN, 12, Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN));
        devGrid[8].peek().setProductInputList(Resource.STONE);
        devGrid[8].peek().setProductOutputList(Resource.COIN, Resource.COIN, Resource.COIN, Resource.SHIELD);

        //blue
        devGrid[9] = new Stack<DevCard>();

        devGrid[9].push(new DevCard(34, Level.LV3,Color.BLUE, 9, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN));
        devGrid[9].peek().setProductInputList(Resource.SERVANT, Resource.SERVANT);
        devGrid[9].peek().setProductOutputList(Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.FAITH, Resource.FAITH);

        devGrid[9].push(new DevCard(38, Level.LV3,Color.BLUE, 10, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN,Resource.STONE, Resource.STONE));
        devGrid[9].peek().setProductInputList( Resource.COIN, Resource.SHIELD);
        devGrid[9].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.STONE, Resource.STONE, Resource.FAITH);

        devGrid[9].push(new DevCard(42, Level.LV3,Color.BLUE, 11, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN));
        devGrid[9].peek().setProductInputList(Resource.STONE);
        devGrid[9].peek().setProductOutputList(Resource.SHIELD, Resource.FAITH, Resource.FAITH, Resource.FAITH);

        devGrid[9].push(new DevCard(46, Level.LV3,Color.BLUE, 12, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.STONE,Resource.STONE,Resource.STONE,Resource.STONE));
        devGrid[9].peek().setProductInputList(Resource.SERVANT);
        devGrid[9].peek().setProductOutputList(Resource.COIN, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD);

        //yellow
        devGrid[10] = new Stack<DevCard>();

        devGrid[10].push(new DevCard(35, Level.LV3,Color.YELLOW, 9, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE));
        devGrid[10].peek().setProductInputList(Resource.SHIELD, Resource.SHIELD);
        devGrid[10].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.FAITH, Resource.FAITH);

        devGrid[10].push(new DevCard(39, Level.LV3,Color.YELLOW, 10, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT, Resource.SERVANT));
        devGrid[10].peek().setProductInputList(Resource.STONE, Resource.SERVANT);
        devGrid[10].peek().setProductOutputList(Resource.COIN, Resource.COIN, Resource.SHIELD, Resource.SHIELD, Resource.FAITH);

        devGrid[10].push(new DevCard(43, Level.LV3,Color.YELLOW, 11, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE,  Resource.STONE));
        devGrid[10].peek().setProductInputList(Resource.SHIELD);
        devGrid[10].peek().setProductOutputList(Resource.SERVANT, Resource.FAITH, Resource.FAITH, Resource.FAITH);

        devGrid[10].push(new DevCard(47, Level.LV3,Color.YELLOW, 12, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT,Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[10].peek().setProductInputList(Resource.SHIELD);
        devGrid[10].peek().setProductOutputList(Resource.STONE, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT);

        //purple
        devGrid[11] = new Stack<DevCard>();

        devGrid[11].push(new DevCard(33, Level.LV3,Color.PURPLE, 9, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[11].peek().setProductInputList(Resource.STONE,Resource.STONE);
        devGrid[11].peek().setProductOutputList(Resource.COIN, Resource.COIN, Resource.COIN, Resource.FAITH, Resource.FAITH);

        devGrid[11].push(new DevCard(37, Level.LV3,Color.PURPLE, 10,  Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.COIN, Resource.COIN));
        devGrid[11].peek().setProductInputList(Resource.STONE,Resource.SHIELD);
        devGrid[11].peek().setProductOutputList( Resource.SERVANT, Resource.SERVANT,Resource.COIN, Resource.COIN, Resource.FAITH);

        devGrid[11].push(new DevCard(41, Level.LV3,Color.PURPLE, 11,  Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[11].peek().setProductInputList(Resource.COIN);
        devGrid[11].peek().setProductOutputList(Resource.STONE, Resource.FAITH, Resource.FAITH, Resource.FAITH);

        devGrid[11].push(new DevCard(45, Level.LV3,Color.PURPLE, 12,  Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD));
        devGrid[11].peek().setProductInputList(Resource.COIN);
        devGrid[11].peek().setProductOutputList( Resource.SERVANT,Resource.STONE,Resource.STONE,Resource.STONE);


        for(int i = 0; i<12; i++)
        {
            Collections.shuffle(devGrid[i]);
        }
    }

    /**
     * Generates by inserting randomly all marbles into market
     */
    private void marketGenerator() //genera e riempie casualmente la griglia mercato di un oggetto gioco
    {
        int i;
        this.market = new Resource[3][4];
        ArrayList<Resource> origin = new ArrayList<Resource>(13);
        for (i=0; i<13; i++ )
        {
            if (i<2)
            {
                origin.add(i,Resource.COIN);
            }
            else if (i<4)
            {
                origin.add(i,Resource.SERVANT);
            }
            else if (i<6)
            {
                origin.add(i,Resource.SHIELD);
            }
            else if (i<8)
            {
                origin.add(i,Resource.STONE);
            }
            else if (i==8)
            {
                origin.add(i,Resource.FAITH);
            }
            else
            {
                origin.add(i,Resource.WHITE);
            }
        }
        Collections.shuffle(origin);
        i = 0;
        this.freeMarble = origin.get(i);
        for (int j = 0; j<3; j++)
        {
            for (int k = 0; k<4; k++)
            {
                i++;
                this.market[j][k] = origin.get(i);
            }
        }
    }

    /**
     * Generates faith track for all player where some player starts 1 or 2 steps ahead
     */
    private void faithTrackGenerator() {

        int s = this.playerList.size(); //numero di giocatori
        if (s>1) //multiplayer
        { //posizioni iniziali primo e secondo giocatore: 0; terzo e quarto giocatore: 1
            for (int i=0; i<s; i++) {
                if (i < 2) {
                    playerList.get(i).forwardFaithLocation(0);
                }
                else{
                    playerList.get(i).forwardFaithLocation(1);
                }
            }
        }
        else{ //singleplayer
            playerList.get(0).forwardFaithLocation(0);
            lorenzoLocation = 0; //posizione di Lorenzo
        }
    }

    /**
     * Generates starting leader deck
     */
    private void leaderDeckGenerator() {
        this.leaderDeck = new Stack<LeaderCard>();
        //Leader Sconto
        this.leaderDeck.push(new LeaderCard(1, 2, AbilityType.DISCOUNT, Resource.SHIELD));
        leaderDeck.peek().setDisResLeader(Color.BLUE, Color.PURPLE);
        this.leaderDeck.push(new LeaderCard(2, 2, AbilityType.DISCOUNT, Resource.STONE));
        leaderDeck.peek().setDisResLeader(Color.GREEN, Color.BLUE);
        this.leaderDeck.push(new LeaderCard(0, 2, AbilityType.DISCOUNT, Resource.SERVANT));
        leaderDeck.peek().setDisResLeader(Color.YELLOW, Color.GREEN);
        this.leaderDeck.push(new LeaderCard(3, 2, AbilityType.DISCOUNT, Resource.COIN));
        leaderDeck.peek().setDisResLeader(Color.YELLOW, Color.PURPLE);
        //Leader Market
        this.leaderDeck.push(new LeaderCard(9, 5, AbilityType.RESOURCE, Resource.SHIELD ));
        leaderDeck.peek().setDisResLeader(Color.GREEN, Color.PURPLE);
        this.leaderDeck.push(new LeaderCard(10, 5, AbilityType.RESOURCE, Resource.STONE));
        leaderDeck.peek().setDisResLeader(Color.BLUE, Color.YELLOW);
        this.leaderDeck.push(new LeaderCard(8, 5, AbilityType.RESOURCE, Resource.SERVANT));
        leaderDeck.peek().setDisResLeader(Color.YELLOW, Color.BLUE);
        this.leaderDeck.push(new LeaderCard(11, 5, AbilityType.RESOURCE, Resource.COIN));
        leaderDeck.peek().setDisResLeader(Color.PURPLE, Color.GREEN);
        //Leader Sviluppo
        this.leaderDeck.push(new LeaderCard(12, 4, AbilityType.PRODUCTION, Resource.SHIELD));
        leaderDeck.peek().setDevLeader(Color.YELLOW);
        this.leaderDeck.push(new LeaderCard(14, 4, AbilityType.PRODUCTION,Resource.STONE));
        leaderDeck.peek().setDevLeader(Color.PURPLE);
        this.leaderDeck.push(new LeaderCard(13,4, AbilityType.PRODUCTION,Resource.SERVANT));
        leaderDeck.peek().setDevLeader(Color.BLUE);
        this.leaderDeck.push(new LeaderCard(15,4, AbilityType.PRODUCTION, Resource.COIN));
        leaderDeck.peek().setDevLeader(Color.GREEN);
        //Leader Deposito
        this.leaderDeck.push(new LeaderCard(6, 3, AbilityType.DEPOT, Resource.SHIELD));
        leaderDeck.peek().setDepLeader(Resource.SERVANT);
        this.leaderDeck.push(new LeaderCard(4, 3, AbilityType.DEPOT, Resource.STONE));
        leaderDeck.peek().setDepLeader(Resource.COIN);
        this.leaderDeck.push(new LeaderCard(5, 3, AbilityType.DEPOT, Resource.SERVANT));
        leaderDeck.peek().setDepLeader(Resource.STONE);
        this.leaderDeck.push(new LeaderCard(7, 3, AbilityType.DEPOT, Resource.COIN));
        leaderDeck.peek().setDepLeader(Resource.SHIELD);

        Collections.shuffle(this.leaderDeck);
    }

    /**
     * generates Lorenzo's action deck
     */
    private void lorenzoDeckGenerator()
    {
        lorenzoDeck=new ArrayList<>();
        lorenzoDeck.add(new LorenzoCard(LorenzoType.SHUFFLE));
        lorenzoDeck.add(new LorenzoCard(LorenzoType.MOVE));
        lorenzoDeck.add(new LorenzoCard(LorenzoType.MOVE));
        lorenzoDeck.add(new LorenzoCard(LorenzoType.DISCARD,Color.GREEN));
        lorenzoDeck.add(new LorenzoCard(LorenzoType.DISCARD,Color.BLUE));
        lorenzoDeck.add(new LorenzoCard(LorenzoType.DISCARD,Color.PURPLE));
        lorenzoDeck.add(new LorenzoCard(LorenzoType.DISCARD,Color.YELLOW));
        shuffleLorenzo();
    }

    //metodi gestione carte sviluppo

    /**
     * Draw a dev card from a deck
     * @param idmazzo id of the deck
     * @return the card on top of the deck
     */
    public DevCard drawDevCard(int idmazzo)
    {
        if (devGrid[idmazzo].isEmpty())
            return null;
        else
            return devGrid[idmazzo].pop();
    }

    //metodi gestione mercato

    /**
     * Insert the free marble into the market row
     * @param j the row which the free marble is being inserted
     * @return the modified market
     */
    public Resource[][] insertRow(int j) //j indica la riga scelta: deve essere compreso tra 0 e 2. Restituisce tutte le risorse presenti nella riga
    {
        int i;
        Resource container = this.freeMarble;
        this.freeMarble = this.market[j][0];
        for (i=0; i<3; i++)
        {
            this.market[j][i] = this.market[j][i+1];
        }
        this.market[j][i] = container;
        return this.market;
    }

    /**
     * Insert the free marble into the market column
     * @param k the column which the free marble is being inserted
     * @return the modified market
     */
    public Resource[][] insertCol(int k) //k indica la colonna scelta: deve essere compreso tra 0 e 3. //restituisce tutte risorse presenti nella colonna
    {
        int i;
        Resource container = this.freeMarble;
        this.freeMarble = this.market[0][k];
        for (i=0; i<2; i++)
        {
            this.market[i][k] = this.market[i+1][k];
        }
        this.market[i][k] = container;
        return this.market;
    }

    /**
     * Modify given arraylist, if the element is white then remove it,
     * @param r Resource for reference
     * @param gain given Array
     */
    public void modifyResources (Resource r, ArrayList<Resource> gain) //restituisce le risorse della striscia scelta o senza il bianco o potenziate con la carta leader
    {
        if (r.equals(Resource.WHITE))
        {
            for (int i = 0; i< gain.size(); i++)
            {
                if (gain.get(i).equals(Resource.WHITE))
                {
                    gain.remove(i);
                    i--;
                }
            }
        }
        else {
            for (int i = 0; i< gain.size(); i++)
            {
                if (gain.get(i).equals(Resource.WHITE))
                {gain.set(i, r);}
            }
        }
    }

    //metodi gestione percorso fede

    /**
     * Forward the specific player
     * @param id the id of player who's forwarding
     * @param box number of steps
     */
    public void forwardPlayer(int id, int box) //i parametri indicano il giocatore che avanza(da 0 a 3) e il numero di caselle da percorrere
    {
        playerList.get(id).forwardFaithLocation(box);
        setPope(id);
    }
    /**
     *Forward everyone but given player
     * @param id the id of player who's NOT forwarding
     * @param box number of steps
     */
    public void forwardOtherPlayers(int id, int box) //usato solo in multiplayer, scarta risorse
    {
        int i;
        for (i = 0; i<playerList.size(); i++) {
            if (i != id) {
                playerList.get(i).forwardFaithLocation(box);
                //setPope(i);
            }
        }
        for (i = 0; i<playerList.size(); i++) {
            if (i != id) {
                setPope(i);
            }
        }
        if(playerList.size()==1)
        {
            forwardLorenzo(box);
        }
    }

    /**
     * Forward lorenzo
     * @param box Number of steps
     */
    public void forwardLorenzo(int box) //usato solo in singleplayer
    {
        this.lorenzoLocation += box;
        if (lorenzoLocation>23)
        {
            assignmentPopeFavor(2);
            this.popeSpace[2] = false;
        }
        else if (lorenzoLocation>15)
        {
            assignmentPopeFavor(1);
            this.popeSpace[1] = false;
        }
        else if(lorenzoLocation>7)
        {
            assignmentPopeFavor(0);
            this.popeSpace[0] = false;
        }
    }

    /**
     * Based on given player's position, assign his Pope favor based on Pope space status
     * @param id
     */
    private void setPope (int id)//metodo ausiliario utilizzato dai metodi forward del tracciato fede
    {
        if (popeSpace[2] && getLocationPlayer(id)>23)
        {
            assignmentPopeFavor(2);
            this.popeSpace[2] = false;
        }
        else if (popeSpace[1] && getLocationPlayer(id)>15)
        {
            assignmentPopeFavor(1);
            this.popeSpace[1] = false;
        }
        else if(popeSpace[0] && getLocationPlayer(id)>7)
        {
            assignmentPopeFavor(0);
            this.popeSpace[0] = false;
        }
    }
    private void assignmentPopeFavor(int n)
    {
        int c;
        if (n==2)
            c = 18;
        else if (n==1)
            c =11;
        else
            c = 4;
        for(Player p : playerList)
        {
            if (p.getFaithLocation() > c)
            {
                p.setPopeFavor(n);
            }
        }
    }


    /**
     * Return an array of all players' victory points at end game
     * @return the
     */
    public int[] ranking(){
        int[] r = new int[playerList.size()];
        for (int i = 0; i<playerList.size(); i++)
        {
            r[i] = playerList.get(i).vp();
        }
        return r;
    }

    /**
     * Changes the currentPlayer to the next player in list, if it's end of the list, go back to the first
     */
    public void nextPlayer ()
    {
        int i = getIndexPlayer(currentPlayer);
        i++;
        if (i == playerList.size())
        {
            currentPlayer = playerList.get(0);
        }
        else
        {
            currentPlayer = playerList.get(i);
        }

    }

    /**
     * Draw a Leader card from deck
     * @return the drawn leader card
     */
    public LeaderCard drawLeaderCard()
    {
        if (leaderDeck.isEmpty())
            return  null;
        else
            return leaderDeck.pop();
    }

    /**
     * Verify whether game is entering "last round"
     * @return true or false
     */
    public boolean isGameOverMP()//return true if player has more than 7 devCard or he reached end of faith track
    {
        for (Player p:playerList)
        {
            if(p.getFaithLocation()==24 || p.getDevCardCount()>=7)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Shuffles lorenzo action deck
     */
    public void shuffleLorenzo()
    {
        Collections.shuffle(lorenzoDeck);
    }

    /**
     * One of Lorenzo's action, discard 2 card of selected color, starting from low level
     * @param color the color of discarding cards
     */
    public void lorenzoDiscard(Color color) //draw 2 card of specified color from devGrid.
    {
        int i=0;
        switch(color)
        {
            case GREEN:
            {
                i=0;
                break;
            }
            case BLUE:
            {
                i=1;
                break;
            }
            case YELLOW:
            {
                i=2;
                break;
            }
            case PURPLE:
            {
                i=3;
                break;
            }
        }
        for(int j=0;j<2;j++)
        {
            if(devGrid[i].size()!=0)
            {
                devGrid[i].pop();
            }else if(devGrid[i+4].size()!=0)
            {
                devGrid[i+4].pop();
            }
            else
            {
                devGrid[i+8].pop();
            }
        }
    }

    /**
     * Generates a Arraylist made of fist Development card of every single deck
     * @return the Array
     */
    private ArrayList<DevCard> createVisibleGrid()
    {
        ArrayList<DevCard> visGrid=new ArrayList<>();
        for(Stack<DevCard> sd: devGrid)
        {
            if(sd.size()!=0)
            {
                visGrid.add(sd.peek());
            }
            else
            {
                visGrid.add(null);
            }

        }
        return visGrid;
    }


    //Message section

    /**
     * Sends a message for initialization
     */
    public void sendInitGame()
    {
        Message m=new Message();
        m.setType(MessageType.UPDATE);
        m.setPlayerAction(PlayerAction.INIT);
        m.setBroadCast(true);
        m.setMarket(market);
        m.setFreeMarble(freeMarble);
        m.setDevDeck(createVisibleGrid());
        m.setPlayerNickList(getPlayerNickList());
        sendBroadcast(m);
    }

    /**
     * Sends a Error message to current player
     * @param text the error text
     */
    public void sendErrorToCurrentPlayer(String text)
    {
        Message m=new Message();
        m.setType(MessageType.ERROR);
        m.setText(text);
        m.setBroadCast(false);
        m.setPlayerNick(getCurrentP().getNickname());
        m.setMarket(market);
        m.setFreeMarble(freeMarble);
        m.setDepot(currentPlayer.getDepots());
        m.setPopeSpace(popeSpace);
        m.setPopeFavor(createPopeFavor());
        m.setFaithTrack(createFaithTrack());
        sendBroadcast(m);
    }

    /**
     * Sends a Lorenzo announcement to all player
     * @param text Lorenzo announcement
     */
    public void sendLorenzoAnnouncement(String text)
    {
        Message m=new Message();
        m.setType(MessageType.LORENZO);
        m.setText(text);
        m.setBroadCast(true);
        sendBroadcast(m);
    }

    /**
     * Sends a message to notify client that the Action performed by player ended without error
     */
    public void sendEndAction()
    {
        Message m=new Message();
        m.setType(MessageType.UPDATE);
        m.setPlayerAction(PlayerAction.END_ACTION);
        m.setBroadCast(true);
        sendBroadcast(m);
    }

    /**
     * Sends a message containing ranking information at end game
     * @param ranking the ranking, array of int
     */
    public void sendRanking(ArrayList<Integer> ranking)
    {
        Message m=new Message();
        m.setRanking(ranking);
        m.setType(MessageType.WIN);
        m.setBroadCast(true);
        sendBroadcast(m);
    }

    /**
     * Sends a "win" message with text that indicates who won.
     * @param text
     */
    public void sendWin(String text)
    {
        Message m=new Message();
        m.setText(text);
        m.setType(MessageType.WIN);
        m.setBroadCast(true);
        sendBroadcast(m);
    }

    /**
     * Sends an update message to update client's information after a  action.
     * @param p nick of player
     */
    public void sendUpdateChooseLeader(String p)
    {
        Message m=new Message();
        m.setBroadCast(true);
        m.setType(MessageType.UPDATE);
        m.setPlayerAction(PlayerAction.CHOOSE_LEADER);
        m.setPlayerNick(p);
        m.setLeaderDeck(getPlayerByNick(p).getLeader());
        m.setDepot(getPlayerByNick(p).getDepots());
        sendBroadcast(m);
    }

    /**
     * Sends an update message to update client's information after a Use leader action.
     */
    public void sendUpdateUseLeader()
    {
        Message m=new Message();
        m.setBroadCast(true);
        m.setType(MessageType.UPDATE);
        m.setPlayerAction(PlayerAction.USE_LEADER);
        m.setPlayerNick(getCurrentP().getNickname());
        m.setLeaderDeck(currentPlayer.getLeader());
        m.setSpecialDepots(currentPlayer.getSpecialDepots());
        m.setDevSlots(currentPlayer.getDevSlots());
        m.setMarketDiscount(currentPlayer.getMarketDiscounts());
        m.setDevDiscount(currentPlayer.getDevelopmentDiscounts());
        sendBroadcast(m);
    }
    /**
     * Sends an update message to update client's information after a Discard leader action.
     */
    public void sendUpdateDiscardLeader()
    {
        Message m=new Message();
        m.setBroadCast(true);
        m.setType(MessageType.UPDATE);
        m.setPlayerAction(PlayerAction.DISCARD_LEADER);
        m.setLeaderDeck(currentPlayer.getLeader());
        m.setPlayerNick(getCurrentP().getNickname());
        m.setFaithTrack(createFaithTrack());
        m.setPopeSpace(popeSpace);
        m.setPopeFavor(createPopeFavor());
        sendBroadcast(m);
    }
    /**
     * Sends an update message to update client's information after a Market1 action.
     */
    public void sendUpdateMarket1()
    {
        Message m=new Message();
        m.setBroadCast(false);
        m.setType(MessageType.UPDATE);
        m.setPlayerAction(PlayerAction.MARKET1);
        m.setPlayerNick(getCurrentP().getNickname());
        m.setFaithTrack(createFaithTrack());
        m.setPopeSpace(popeSpace);
        m.setPopeFavor(createPopeFavor());
        m.setMarket(market);
        m.setFreeMarble(freeMarble);
        m.setResources(currentPlayer.getGain());
        sendUnicast(currentPlayer.getNickname(),m);
    }
    /**
     * Sends an update message to update client's information after a Market2 action.
     */
    public void sendUpdateMarket2()
    {
        Message m=new Message();
        m.setBroadCast(true);
        m.setType(MessageType.UPDATE);
        m.setPlayerAction(PlayerAction.MARKET2);
        m.setPlayerNick(getCurrentP().getNickname());
        m.setDepot(currentPlayer.getDepots());
        m.setSpecialDepots(currentPlayer.getSpecialDepots());
        m.setFaithTrack(createFaithTrack());
        m.setPopeSpace(popeSpace);
        m.setPopeFavor(createPopeFavor());
        m.setLorenzoLocation(lorenzoLocation);
        sendBroadcast(m);
    }

    /**
     * Sends an update message to update client's information after a Production action.
     */
    public void sendUpdateProduction()
    {
        Message m=new Message();
        m.setBroadCast(true);
        m.setType(MessageType.UPDATE);
        m.setPlayerAction(PlayerAction.PRODUCTION);
        m.setPlayerNick(getCurrentP().getNickname());
        m.setDepot(currentPlayer.getDepots());
        m.setSpecialDepots(currentPlayer.getSpecialDepots());
        m.setStrongBox(currentPlayer.getStrongbox());
        m.setFaithTrack(createFaithTrack());
        m.setPopeSpace(popeSpace);
        m.setPopeFavor(createPopeFavor());
        sendBroadcast(m);
    }
    /**
     * Sends an update message to update client's information after a Purchase action.
     */
    public void sendUpdatePurchase()
    {
        Message m=new Message();
        m.setBroadCast(true);
        m.setType(MessageType.UPDATE);
        m.setPlayerAction(PlayerAction.PURCHASE);
        m.setPlayerNick(getCurrentP().getNickname());
        m.setDepot(currentPlayer.getDepots());
        m.setSpecialDepots(currentPlayer.getSpecialDepots());
        m.setStrongBox(currentPlayer.getStrongbox());
        m.setDevSlots(currentPlayer.getDevSlots());
        m.setDevDeck(createVisibleGrid());
        sendBroadcast(m);
    }
    /**
     * Sends an update message to update client's information after a End turn action.
     */
    public void sendUpdateNextTurn()
    {
        Message m=new Message();
        m.setBroadCast(true);
        m.setType(MessageType.UPDATE);
        m.setPlayerAction(PlayerAction.END_TURN);
        m.setCurrentPlayer(getCurrentP().getNickname());
        sendBroadcast(m);
    }

    private ArrayList<Integer> createFaithTrack()
    {
        ArrayList<Integer> temp=new ArrayList<>();
        for(Player p: playerList)
        {
            temp.add(p.getFaithLocation());
        }
        return temp;
    }

    private boolean[][] createPopeFavor()
    {
        boolean[][] temp=new boolean[playerList.size()][3];
        for(int i=0;i<playerList.size();i++)
        {
            temp[i]=playerList.get(i).getPopeFavor();
        }
        return temp;
    }

    /**
     * BroadCast given message
     * @param m message
     */
    private void sendBroadcast(Message m)
    {
        notify(m);
    }

    /**
     * Unicast given message to given player
     * @param nickname nick of player
     * @param m message
     */
    public void sendUnicast(String nickname, Message m) {
        notify(m);
    }

}
