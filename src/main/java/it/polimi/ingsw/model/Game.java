package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

import java.util.*;

public class Game {


    //state
    private ArrayList<Player> playerList;
    private Stack<DevCard>[] devGrid;
    private ArrayList<ActionLorenzo> lorenzoDeck;
    private Stack<LeaderCard> leaderDeck;
    private Resource[][] market;
    private Resource freeMarble;
    private Player currentPlayer; //oggetto player che punta il player corrente a partire dal primo nella lista playerList
    private boolean[] popeSpace = {true, true, true}; // true indica che il favore papale è attivabile
    private int[] playerLocation; //Il percorso Fede ha 25 caselle: la posizione di un giocatore può essere compresa tra 0 e 24

    //costruttore
    public Game(ArrayList<Player> p)
    {
        this.playerList = new ArrayList<Player>(p); //la classe che crea i giocatori passa poi la lista (senza Lorenzo) all'oggetto game
        currentPlayer = playerList.get(0);
        marketGenerator();
        leaderDeckGenerator();
        faithTrackGenerator();
        devCardGenerator();
        //lorenzoDeckGenerator();
    }

    public void nextPlayer ()
    {
        int i = playerList.indexOf(currentPlayer);
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

    public Player getCurrentP ()
    {
        return currentPlayer;
    }

    //metodi gestione carte sviluppo

    private void devCardGenerator() //genera tutte le carte sviluppo con i valori di default e le inserisce in un array di 12 stack da 4 DevCard ciascuno
    {
        devGrid = new Stack[12];
        //lv 1

        //Green
        devGrid[0] = new Stack<DevCard>();

        ArrayList<DevCard> carte = new ArrayList<DevCard>();
        devGrid[0].push(new DevCard(0, Level.LV1, Color.GREEN, 2, Resource.STONE, Resource.SHIELD, Resource.SERVANT));
        devGrid[0].peek().setProductInputList(Resource.STONE);
        devGrid[0].peek().setProductOutputList(Resource.SERVANT);

        devGrid[0].push(new DevCard(1, Level.LV1, Color.GREEN, 1, Resource.SHIELD, Resource.SHIELD));
        devGrid[0].peek().setProductInputList(Resource.COIN);
        devGrid[0].peek().setProductOutputList(Resource.FAITH);

        devGrid[0].push(new DevCard(2,Level.LV1, Color.GREEN, 4,Resource.SHIELD,Resource.SHIELD,Resource.COIN,Resource.COIN ));
        devGrid[0].peek().setProductInputList(Resource.STONE, Resource.SERVANT);
        devGrid[0].peek().setProductOutputList(Resource.COIN,Resource.COIN, Resource.FAITH);

        devGrid[0].push(new DevCard(3,Level.LV1, Color.GREEN, 3, Resource.SHIELD,Resource.SHIELD, Resource.SHIELD));
        devGrid[0].peek().setProductInputList(Resource.SERVANT, Resource.SERVANT);
        devGrid[0].peek().setProductOutputList(Resource.COIN, Resource.SHIELD, Resource.STONE);
        //lv 1

        //Blue
        devGrid[1] = new Stack<DevCard>();

        devGrid[1].push(new DevCard(4, Level.LV1, Color.BLUE, 3, Resource.COIN, Resource.COIN,Resource.COIN));
        devGrid[1].peek().setProductInputList(Resource.STONE, Resource.STONE);
        devGrid[1].peek().setProductOutputList(Resource.COIN, Resource.SERVANT,Resource.SHIELD);

        devGrid[1].push(new DevCard(5, Level.LV1, Color.BLUE, 1, Resource.COIN,Resource.COIN));
        devGrid[1].peek().setProductInputList(Resource.SHIELD);
        devGrid[1].peek().setProductOutputList(Resource.FAITH);

        devGrid[1].push(new DevCard(6,Level.LV1, Color.BLUE, 4, Resource.COIN, Resource.COIN, Resource.SERVANT, Resource.SERVANT));
        devGrid[1].peek().setProductInputList(Resource.SHIELD, Resource.STONE);
        devGrid[1].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.FAITH);

        devGrid[1].push(new DevCard(7,Level.LV1, Color.BLUE,2, Resource.COIN, Resource.SERVANT, Resource.STONE));
        devGrid[1].peek().setProductInputList(Resource.SERVANT);
        devGrid[1].peek().setProductOutputList(Resource.STONE);
        //lv 1

        //Yellow
        devGrid[2] = new Stack<DevCard>();

        devGrid[2].push(new DevCard(8, Level.LV1, Color.YELLOW, 3, Resource.STONE, Resource.STONE, Resource.STONE));
        devGrid[2].peek().setProductInputList(Resource.SHIELD, Resource.SHIELD);
        devGrid[2].peek().setProductOutputList(Resource.COIN, Resource.SERVANT,Resource.STONE);

        devGrid[2].push(new DevCard(9, Level.LV1, Color.YELLOW, 1, Resource.STONE,Resource.STONE));
        devGrid[2].peek().setProductInputList(Resource.SERVANT);
        devGrid[2].peek().setProductOutputList(Resource.FAITH);

        devGrid[2].push(new DevCard(10,Level.LV1, Color.YELLOW, 4, Resource.STONE,Resource.STONE,Resource.SHIELD,Resource.SHIELD ));
        devGrid[2].peek().setProductInputList(Resource.COIN, Resource.SERVANT);
        devGrid[2].peek().setProductOutputList(Resource.SHIELD, Resource.SHIELD,Resource.FAITH);

        devGrid[2].push(new DevCard(11,Level.LV1, Color.YELLOW, 2, Resource.SHIELD,Resource.STONE,Resource.COIN));
        devGrid[2].peek().setProductInputList(Resource.SHIELD);
        devGrid[2].peek().setProductOutputList(Resource.COIN);

        //purple
        devGrid[3] = new Stack<DevCard>();

        devGrid[3].push(new DevCard(12, Level.LV1, Color.PURPLE, 3, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[3].peek().setProductInputList(Resource.COIN, Resource.COIN);
        devGrid[3].peek().setProductOutputList(Resource.SERVANT, Resource.SHIELD, Resource.STONE);

        devGrid[3].push(new DevCard(13, Level.LV1, Color.PURPLE, 4, Resource.SERVANT, Resource.SERVANT, Resource.STONE, Resource.STONE));
        devGrid[3].peek().setProductInputList(Resource.COIN,Resource.SHIELD);
        devGrid[3].peek().setProductOutputList(Resource.STONE, Resource.STONE, Resource.FAITH);

        devGrid[3].push(new DevCard(14,Level.LV1, Color.PURPLE, 2, Resource.SHIELD,Resource.SERVANT,Resource.COIN));
        devGrid[3].peek().setProductInputList(Resource.COIN);
        devGrid[3].peek().setProductOutputList(Resource.SHIELD);

        devGrid[3].push(new DevCard(15,Level.LV1, Color.PURPLE, 1, Resource.SERVANT, Resource.SERVANT));
        devGrid[3].peek().setProductInputList(Resource.STONE);
        devGrid[3].peek().setProductOutputList(Resource.FAITH);

        //LV2
        //green

        devGrid[4] = new Stack<DevCard>();

        devGrid[4].push(new DevCard(16, Level.LV2,Color.GREEN, 5, Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD));
        devGrid[4].peek().setProductInputList(Resource.STONE);
        devGrid[4].peek().setProductOutputList(Resource.FAITH,Resource.FAITH);

        devGrid[4].push(new DevCard(17, Level.LV2,Color.GREEN, 6, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SERVANT, Resource.SERVANT));
        devGrid[4].peek().setProductInputList(Resource.SHIELD, Resource.SERVANT);
        devGrid[4].peek().setProductOutputList(Resource.STONE, Resource.STONE, Resource.STONE);

        devGrid[4].push(new DevCard(18,Level.LV2,Color.GREEN, 7, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD));
        devGrid[4].peek().setProductInputList(Resource.COIN, Resource.COIN);
        devGrid[4].peek().setProductOutputList(Resource.STONE, Resource.STONE, Resource.FAITH, Resource.FAITH);

        devGrid[4].push(new DevCard(19,Level.LV2,Color.GREEN,8, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.COIN, Resource.COIN, Resource.COIN));
        devGrid[4].peek().setProductInputList(Resource.COIN);
        devGrid[4].peek().setProductOutputList(Resource.SHIELD, Resource.SHIELD, Resource.FAITH);

        //blue
        devGrid[5] = new Stack<DevCard>();

        devGrid[5].push(new DevCard(20, Level.LV2,Color.BLUE,5, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN ));
        devGrid[5].peek().setProductInputList(Resource.SERVANT);
        devGrid[5].peek().setProductOutputList(Resource.FAITH, Resource.FAITH);

        devGrid[5].push(new DevCard(21, Level.LV2,Color.BLUE, 6, Resource.COIN, Resource.COIN, Resource.COIN, Resource.STONE, Resource.STONE));
        devGrid[5].peek().setProductInputList(Resource.COIN, Resource.STONE);
        devGrid[5].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.SERVANT);

        devGrid[5].push(new DevCard(22,Level.LV2,Color.BLUE, 7, Resource.COIN,Resource.COIN,Resource.COIN,Resource.COIN,Resource.COIN));
        devGrid[5].peek().setProductInputList(Resource.SERVANT, Resource.SERVANT);
        devGrid[5].peek().setProductOutputList(Resource.SHIELD,Resource.SHIELD,Resource.FAITH,Resource.FAITH);

        devGrid[5].push(new DevCard(23,Level.LV2,Color.BLUE, 8, Resource.COIN, Resource.COIN, Resource.COIN, Resource.STONE, Resource.STONE, Resource.STONE));
        devGrid[5].peek().setProductInputList(Resource.SERVANT);
        devGrid[5].peek().setProductOutputList(Resource.STONE, Resource.STONE, Resource.FAITH);

        //yellow
        devGrid[6] = new Stack<DevCard>();

        devGrid[6].push(new DevCard(24, Level.LV2,Color.YELLOW, 5, Resource.STONE,Resource.STONE,Resource.STONE,Resource.STONE));
        devGrid[6].peek().setProductInputList(Resource.SHIELD);
        devGrid[6].peek().setProductOutputList(Resource.FAITH, Resource.FAITH);

        devGrid[6].push(new DevCard(25, Level.LV2,Color.YELLOW, 6, Resource.STONE,Resource.STONE,Resource.STONE,Resource.SHIELD,Resource.SHIELD));
        devGrid[6].peek().setProductInputList(Resource.STONE, Resource.SHIELD);
        devGrid[6].peek().setProductOutputList(Resource.COIN,Resource.COIN,Resource.COIN);

        devGrid[6].push(new DevCard(26,Level.LV2,Color.YELLOW, 7, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE));
        devGrid[6].peek().setProductInputList(Resource.SHIELD, Resource.SHIELD);
        devGrid[6].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.FAITH, Resource.FAITH);

        devGrid[6].push(new DevCard(27,Level.LV2,Color.YELLOW, 8, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[6].peek().setProductInputList(Resource.SHIELD);
        devGrid[6].peek().setProductOutputList(Resource.COIN, Resource.COIN, Resource.FAITH);

        //purple
        devGrid[7] = new Stack<DevCard>();

        devGrid[7].push(new DevCard(28, Level.LV2,Color.PURPLE, 8, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD));
        devGrid[7].peek().setProductInputList(Resource.STONE);
        devGrid[7].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.FAITH);

        devGrid[7].push(new DevCard(29, Level.LV2,Color.PURPLE, 6, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.COIN, Resource.COIN));
        devGrid[7].peek().setProductInputList(Resource.SERVANT, Resource.COIN);
        devGrid[7].peek().setProductOutputList(Resource.SHIELD, Resource.SHIELD, Resource.SHIELD);

        devGrid[7].push(new DevCard(30,Level.LV2,Color.PURPLE, 7, Resource.SERVANT,Resource.SERVANT,Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[7].peek().setProductInputList(Resource.STONE, Resource.STONE);
        devGrid[7].peek().setProductOutputList(Resource.COIN, Resource.COIN, Resource.FAITH, Resource.FAITH);

        devGrid[7].push(new DevCard(31,Level.LV2,Color.PURPLE, 5, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[7].peek().setProductInputList(Resource.COIN);
        devGrid[7].peek().setProductOutputList(Resource.FAITH, Resource.FAITH);

        //lv3
        //green
        devGrid[8] = new Stack<DevCard>();

        devGrid[8].push(new DevCard(32, Level.LV3,Color.GREEN, 9, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD));
        devGrid[8].peek().setProductInputList(Resource.COIN, Resource.COIN);
        devGrid[8].peek().setProductOutputList(Resource.STONE, Resource.STONE, Resource.STONE, Resource.FAITH, Resource.FAITH);

        devGrid[8].push(new DevCard(33, Level.LV3,Color.GREEN, 10, Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD, Resource.SERVANT, Resource.SERVANT));
        devGrid[8].peek().setProductInputList(Resource.COIN, Resource.SERVANT);
        devGrid[8].peek().setProductOutputList(Resource.SHIELD, Resource.SHIELD, Resource.STONE, Resource.STONE, Resource.FAITH);

        devGrid[8].push(new DevCard(34, Level.LV3,Color.GREEN, 11, Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD));
        devGrid[8].peek().setProductInputList(Resource.SERVANT);
        devGrid[8].peek().setProductOutputList(Resource.COIN, Resource.FAITH, Resource.FAITH, Resource.FAITH);

        devGrid[8].push(new DevCard(35, Level.LV3,Color.GREEN, 12, Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN));
        devGrid[8].peek().setProductInputList(Resource.STONE);
        devGrid[8].peek().setProductOutputList(Resource.COIN, Resource.COIN, Resource.COIN, Resource.SHIELD);

        //blue
        devGrid[9] = new Stack<DevCard>();

        devGrid[9].push(new DevCard(36, Level.LV3,Color.BLUE, 9, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN));
        devGrid[9].peek().setProductInputList(Resource.SERVANT, Resource.SERVANT);
        devGrid[9].peek().setProductOutputList(Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.FAITH, Resource.FAITH);

        devGrid[9].push(new DevCard(37, Level.LV3,Color.BLUE, 10, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN,Resource.STONE, Resource.STONE));
        devGrid[9].peek().setProductInputList( Resource.COIN, Resource.SHIELD);
        devGrid[9].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.STONE, Resource.STONE, Resource.FAITH);

        devGrid[9].push(new DevCard(38, Level.LV3,Color.BLUE, 11, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN));
        devGrid[9].peek().setProductInputList(Resource.STONE);
        devGrid[9].peek().setProductOutputList(Resource.SHIELD, Resource.FAITH, Resource.FAITH, Resource.FAITH);

        devGrid[9].push(new DevCard(39, Level.LV3,Color.BLUE, 12, Resource.COIN, Resource.COIN, Resource.COIN, Resource.COIN, Resource.STONE,Resource.STONE,Resource.STONE,Resource.STONE));
        devGrid[9].peek().setProductInputList(Resource.SERVANT);
        devGrid[9].peek().setProductOutputList(Resource.COIN, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD);

        //yellow
        devGrid[10] = new Stack<DevCard>();

        devGrid[10].push(new DevCard(40, Level.LV3,Color.YELLOW, 9, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE));
        devGrid[10].peek().setProductInputList(Resource.SHIELD, Resource.SHIELD);
        devGrid[10].peek().setProductOutputList(Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.FAITH, Resource.FAITH);

        devGrid[10].push(new DevCard(41, Level.LV3,Color.YELLOW, 10, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT, Resource.SERVANT));
        devGrid[10].peek().setProductInputList(Resource.STONE, Resource.SERVANT);
        devGrid[10].peek().setProductOutputList(Resource.COIN, Resource.COIN, Resource.SHIELD, Resource.SHIELD, Resource.FAITH);

        devGrid[10].push(new DevCard(42, Level.LV3,Color.YELLOW, 11, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE,  Resource.STONE));
        devGrid[10].peek().setProductInputList(Resource.SHIELD);
        devGrid[10].peek().setProductOutputList(Resource.SERVANT, Resource.FAITH, Resource.FAITH, Resource.FAITH);

        devGrid[10].push(new DevCard(43, Level.LV3,Color.YELLOW, 12, Resource.STONE, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT,Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[10].peek().setProductInputList(Resource.SHIELD);
        devGrid[10].peek().setProductOutputList(Resource.STONE, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT);

        //purple
        devGrid[11] = new Stack<DevCard>();

        devGrid[11].push(new DevCard(44, Level.LV3,Color.PURPLE, 9, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[11].peek().setProductInputList(Resource.STONE,Resource.STONE);
        devGrid[11].peek().setProductOutputList(Resource.COIN, Resource.COIN, Resource.COIN, Resource.FAITH, Resource.FAITH);

        devGrid[11].push(new DevCard(45, Level.LV3,Color.PURPLE, 10,  Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.COIN, Resource.COIN));
        devGrid[11].peek().setProductInputList(Resource.STONE,Resource.SHIELD);
        devGrid[11].peek().setProductOutputList( Resource.SERVANT, Resource.SERVANT,Resource.COIN, Resource.COIN, Resource.FAITH);

        devGrid[11].push(new DevCard(46, Level.LV3,Color.PURPLE, 11,  Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT));
        devGrid[11].peek().setProductInputList(Resource.COIN);
        devGrid[11].peek().setProductOutputList(Resource.STONE, Resource.FAITH, Resource.FAITH, Resource.FAITH);

        devGrid[11].push(new DevCard(47, Level.LV3,Color.PURPLE, 12,  Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD));
        devGrid[11].peek().setProductInputList(Resource.COIN);
        devGrid[11].peek().setProductOutputList( Resource.SERVANT,Resource.STONE,Resource.STONE,Resource.STONE);


        for(int i = 0; i<12; i++)
        {
            Collections.shuffle(devGrid[i]);
        }
    }

    public DevCard getDevCard(int idmazzo)
    {
        return devGrid[idmazzo].peek();
    }

    public DevCard drawDevCard(int idmazzo)
    {
        if (devGrid[idmazzo].isEmpty())
            return null;
        else
            return devGrid[idmazzo].pop();
    }

    //metodi gestione mercato

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

    public ArrayList<Resource> getResources (Resource r, ArrayList<Resource> gain) //restituisce le risorse della striscia scelta o senza il bianco o potenziate con la carta leader
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
        return gain;
    }

    //Viene chiamato al posto di getResources quando il giocatore decide di usare entrambe le carte leader per potenziare i bianchi
    //il giocatore deve per ogni biglia bianca definire la risorsa che vuole
    //il metodo si limita a prendere la lista contenete le risorse che vanno a sostituire i bianchi
    public ArrayList<Resource> get2LeaderResources (ArrayList<Resource> r, ArrayList<Resource> gain)
    {
        int j = 0;
        for (int i = 0; i< gain.size(); i++) {
            if (gain.get(i).equals(Resource.WHITE)) {
                gain.set(i, r.get(j));
                j++;
            }
        }
        return gain;
    }

    //metodi gestione percorso fede

    private void faithTrackGenerator() {

        int s = this.playerList.size();
        int i;
        if (s>1) //multiplayer
        { //posizioni iniziali primo e secondo giocatore: 0; terzo e quarto giocatore: 1
        this.playerLocation = new int[s];
        for (i=0; i<s; i++) {
            if (i < 2) {
                this.playerLocation[i] = 0;
                }
            else{
                this.playerLocation[i] = 1;
                }
            }
        }
        else{ //singleplayer
            this.playerLocation = new int[2];
            playerLocation[0] = 0;
            playerLocation[1] = 0; //posizione di Lorenzo
        }
    }

    public void forwardPlayer(Player p, int box) //i parametri indicano il giocatore che avanza(da 0 a 3) e il numero di caselle da percorrere
    {
        int id = playerList.indexOf(p);
        this.playerLocation[id] += box;
        setPope(id);
    }

    public void forwardOtherPlayers(Player p, int box) //usato solo in multiplayer
    {
        int id = playerList.indexOf(p);
        for (int i = 0; i<playerList.size(); i++) {
            if (i != id) {
                this.playerLocation[i] += box;
                setPope(i);
            }
        }
    }

    public void forwardLorenzo(int box) //usato solo in singleplayer
    {
        this.playerLocation[1] += box;
        setPope(1);
    }

    public int getPositionPlayer(Player p)
    {
        int id = playerList.indexOf(p);
        return  playerLocation[id];
    }

    private void setPope (int id)//metodo ausiliario utilizzato dai metodi forward del tracciato fede
    {
        if (this.playerLocation[id]>18)
        {
            this.popeSpace[2] = false;
        }
        else if (this.playerLocation[id]>11)
        {
            this.popeSpace[1] = false;
        }
        else if(this.playerLocation[id]>4)
        {
            this.popeSpace[0] = false;
        }
    }

    public boolean getPope (int n)
    {
        return popeSpace[n];
    }

    public Resource getFreeMarble ()
    {
        return freeMarble;
    }


    //genera e mescola il mazzo leader
    private void leaderDeckGenerator() {
        this.leaderDeck = new Stack<LeaderCard>();
            //Leader Sconto
            this.leaderDeck.push(new LeaderCard(0, 2, AbilityType.DISCOUNT, Resource.SHIELD));
            leaderDeck.peek().setDisResLeader(Color.BLUE, Color.PURPLE);
            this.leaderDeck.push(new LeaderCard(1, 2, AbilityType.DISCOUNT, Resource.STONE));
            leaderDeck.peek().setDisResLeader(Color.GREEN, Color.BLUE);
            this.leaderDeck.push(new LeaderCard(2, 2, AbilityType.DISCOUNT, Resource.SERVANT));
            leaderDeck.peek().setDisResLeader(Color.YELLOW, Color.GREEN);
            this.leaderDeck.push(new LeaderCard(3, 2, AbilityType.DISCOUNT, Resource.COIN));
            leaderDeck.peek().setDisResLeader(Color.YELLOW, Color.PURPLE);
            //Leader Market
            this.leaderDeck.push(new LeaderCard(4, 5, AbilityType.DEPOT, Resource.SHIELD ));
            leaderDeck.peek().setDisResLeader(Color.GREEN, Color.PURPLE);
            this.leaderDeck.push(new LeaderCard(5, 5, AbilityType.DEPOT, Resource.STONE));
            leaderDeck.peek().setDisResLeader(Color.BLUE, Color.YELLOW);
            this.leaderDeck.push(new LeaderCard(6, 5, AbilityType.DEPOT, Resource.SERVANT));
            leaderDeck.peek().setDisResLeader(Color.YELLOW, Color.BLUE);
            this.leaderDeck.push(new LeaderCard(7, 5, AbilityType.DEPOT, Resource.COIN));
            leaderDeck.peek().setDisResLeader(Color.PURPLE, Color.GREEN);
            //Leader Sviluppo
            this.leaderDeck.push(new LeaderCard(8, 4, AbilityType.PRODUCTION, Resource.SHIELD));
            leaderDeck.peek().setDevLeader(Color.YELLOW);
            this.leaderDeck.push(new LeaderCard(9, 4, AbilityType.PRODUCTION,Resource.STONE));
            leaderDeck.peek().setDevLeader(Color.PURPLE);
            this.leaderDeck.push(new LeaderCard(10,4, AbilityType.PRODUCTION,Resource.SERVANT));
            leaderDeck.peek().setDevLeader(Color.BLUE);
            this.leaderDeck.push(new LeaderCard(11,4, AbilityType.PRODUCTION, Resource.COIN));
            leaderDeck.peek().setDevLeader(Color.GREEN);
            //Leader Deposito
            this.leaderDeck.push(new LeaderCard(12, 3, AbilityType.DEPOT, Resource.SHIELD));
            leaderDeck.peek().setDepLeader(Resource.SERVANT);
            this.leaderDeck.push(new LeaderCard(13, 3, AbilityType.DEPOT, Resource.STONE));
            leaderDeck.peek().setDepLeader(Resource.COIN);
            this.leaderDeck.push(new LeaderCard(14, 3, AbilityType.DEPOT, Resource.SERVANT));
            leaderDeck.peek().setDepLeader(Resource.STONE);
            this.leaderDeck.push(new LeaderCard(15, 3, AbilityType.DEPOT, Resource.COIN));
            leaderDeck.peek().setDepLeader(Resource.SHIELD);

        Collections.shuffle(this.leaderDeck);
    }

    public LeaderCard drawLeaderCard()
    {
        if (leaderDeck.isEmpty())
            return  null;
        else
        return leaderDeck.pop();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    public Player getLastPlayer()
    {
        return playerList.get(playerList.size()-1);
    }
}
