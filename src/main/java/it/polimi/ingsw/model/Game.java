package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.Level;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Game {

    //state
    private ArrayList<Player> playerLobby;
    private Stack<DevCard>[] devGrid;
    private ArrayList<ActionLorenzo> lorenzoDeck;
    private ArrayList<LeaderCard> leaderDeck;
    private Resource[][] market;
    private Resource freeMarble;
    private boolean[] popeSpace = {true, true, true}; // true indica che il favore papale è attivabile
    private int[] playerLocation; //Il percorso Fede ha 25 caselle: la posizione di un giocatore può essere compresa tra 0 e 24

    //costruttore
    public Game()
    {
        //PlayerListGenerator();
        marketGenerator();
        //leaderDeckGenerator();
        //lorenzoDeckGenerator();
        //faithTrackGenerator();
        devCardGenerator();
    }

    //metodi gestione carte sviluppo

    private void devCardGenerator() //genera tutte le carte sviluppo con i valori di default e le inserisce in un array di 12 stack da 4 DevCard ciascuno
    {
        devGrid = new Stack[12];
        //lv 1 //Le carte sono momentaneamente uguali per tutti i mazzi

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

        devGrid[1].push(new DevCard(4, Level.LV1, Color.BLUE, 2, Resource.STONE, Resource.SHIELD, Resource.SERVANT));
        devGrid[1].peek().setProductInputList(Resource.STONE);
        devGrid[1].peek().setProductOutputList(Resource.SERVANT);

        devGrid[1].push(new DevCard(5, Level.LV1, Color.BLUE, 1, Resource.SHIELD, Resource.SHIELD));
        devGrid[1].peek().setProductInputList(Resource.COIN);
        devGrid[1].peek().setProductOutputList(Resource.FAITH);

        devGrid[1].push(new DevCard(6,Level.LV1, Color.BLUE, 4,Resource.SHIELD,Resource.SHIELD,Resource.COIN,Resource.COIN ));
        devGrid[1].peek().setProductInputList(Resource.STONE, Resource.SERVANT);
        devGrid[1].peek().setProductOutputList(Resource.COIN,Resource.COIN, Resource.FAITH);

        devGrid[1].push(new DevCard(7,Level.LV1, Color.BLUE, 3, Resource.SHIELD,Resource.SHIELD, Resource.SHIELD));
        devGrid[1].peek().setProductInputList(Resource.SERVANT, Resource.SERVANT);
        devGrid[1].peek().setProductOutputList(Resource.COIN, Resource.SHIELD, Resource.STONE);
        //lv 1

        //Yellow
        devGrid[2] = new Stack<DevCard>();

        devGrid[2].push(new DevCard(8, Level.LV1, Color.YELLOW, 2, Resource.STONE, Resource.SHIELD, Resource.SERVANT));
        devGrid[2].peek().setProductInputList(Resource.STONE);
        devGrid[2].peek().setProductOutputList(Resource.SERVANT);

        devGrid[2].push(new DevCard(9, Level.LV1, Color.YELLOW, 1, Resource.SHIELD, Resource.SHIELD));
        devGrid[2].peek().setProductInputList(Resource.COIN);
        devGrid[2].peek().setProductOutputList(Resource.FAITH);

        devGrid[2].push(new DevCard(10,Level.LV1, Color.YELLOW, 4,Resource.SHIELD,Resource.SHIELD,Resource.COIN,Resource.COIN ));
        devGrid[2].peek().setProductInputList(Resource.STONE, Resource.SERVANT);
        devGrid[2].peek().setProductOutputList(Resource.COIN,Resource.COIN, Resource.FAITH);

        devGrid[2].push(new DevCard(11,Level.LV1, Color.YELLOW, 3, Resource.SHIELD,Resource.SHIELD, Resource.SHIELD));
        devGrid[2].peek().setProductInputList(Resource.SERVANT, Resource.SERVANT);
        devGrid[2].peek().setProductOutputList(Resource.COIN, Resource.SHIELD, Resource.STONE);

        //purple
        devGrid[3] = new Stack<DevCard>();

        devGrid[3].push(new DevCard(12, Level.LV1, Color.PURPLE, 2, Resource.STONE, Resource.SHIELD, Resource.SERVANT));
        devGrid[3].peek().setProductInputList(Resource.STONE);
        devGrid[3].peek().setProductOutputList(Resource.SERVANT);

        devGrid[3].push(new DevCard(13, Level.LV1, Color.PURPLE, 1, Resource.SHIELD, Resource.SHIELD));
        devGrid[3].peek().setProductInputList(Resource.COIN);
        devGrid[3].peek().setProductOutputList(Resource.FAITH);

        devGrid[3].push(new DevCard(14,Level.LV1, Color.PURPLE, 4,Resource.SHIELD,Resource.SHIELD,Resource.COIN,Resource.COIN ));
        devGrid[3].peek().setProductInputList(Resource.STONE, Resource.SERVANT);
        devGrid[3].peek().setProductOutputList(Resource.COIN,Resource.COIN, Resource.FAITH);

        devGrid[3].push(new DevCard(15,Level.LV1, Color.PURPLE, 3, Resource.SHIELD,Resource.SHIELD, Resource.SHIELD));
        devGrid[3].peek().setProductInputList(Resource.SERVANT, Resource.SERVANT);
        devGrid[3].peek().setProductOutputList(Resource.COIN, Resource.SHIELD, Resource.STONE);

        //LV 2 e LV3  (da implementare)

        for(int i = 0; i<12; i++)
        {
            Collections.shuffle(devGrid[i]);
        }
    }
    public Stack<DevCard> getDeck(int idmazzo) //tramite questa funzione è possibile accedere ad un mazzo specifico. Una volta restituito in un'altra classe, è possibile invocare i metodi peek e pop per visualizzare e pescare la carta
    {
        return devGrid[idmazzo];
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
    public Resource[][] insertRow(int j) //j indica la riga scelta: deve essere compreso tra 0 e 2
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
    public Resource[][] insertCol(int k) //k indica la colonna scelta: deve essere compreso tra 0 e 3
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

    public ArrayList<Resource> getEnhancedResources (Resource r, ArrayList<Resource> gain) //restituisce le risorse del mercato potenziate con la carta leader
    {
        for (int i = 0; i< gain.size(); i++)
        {
            if (gain.get(i).equals(Resource.WHITE))
            {
                gain.set(i,r);
            }
        }
        return gain;
    }

    //metodi gestione percorso fede

    /*private void faithTrackGenerator() {

        int s = this.playerLobby.size();
        int i;
        playerLocation = new int[s];
        //posizione iniziale primo e secondo giocatore: 0; terzo e quarto giocatore: 1
        for (i=0; i<s; i++) {
            if(i<2) {
                this.playerLocation[i] = 0;
            }
            else{
                this.playerLocation[i]= 1;
            }
        }
    }

    public void forward(int id, int box) //i parametri indicano il giocatore e il numero di caselle percorse
    {
        this.playerLocation[id] += box;
        //questo controllo viene fatto dopo che un personaggio si è mosso per sapere se attivare il popeSpace
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

    private void PlayerListGenerator() {

    }


    //metodi pubblici Game
    public void shuffleLorenzoDeck()
    {
        Collections.shuffle(this.leaderDeck);
    }


    public void discardDev()
    {

    }

    public void useActionLorenzo()
    {

    }

    public void endGame()
    {

    }

    public void forwardLorenzo()
    {

    }

    //metodi privati ausiliari (momentaneamente pubblici)

    //genera e mescola il mazzo azioni di Lorenzo
    private ArrayList<ActionLorenzo> lorenzoDeckGenerator() {
        this.lorenzoDeck= new ArrayList<ActionLorenzo>(7);

        for (int i = 0; i<7; i++) {
            this.lorenzoDeck.add(i, new ActionLorenzo());
        }
        this.shuffleLorenzoDeck();
        return this.lorenzoDeck;
    }

    //genera e mescola il mazzo leader
    private ArrayList<LeaderCard> leaderDeckGenerator() {
        this.leaderDeck = new ArrayList<LeaderCard>(16);
        for (int i = 0; i<16; i++) {
            this.leaderDeck.add(i, new LeaderCard());
        }
        Collections.shuffle(this.leaderDeck);
        return this.leaderDeck;
    }*/
}
