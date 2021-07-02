package it.polimi.ingsw.controller;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Util;
import it.polimi.ingsw.client.Cli;
import it.polimi.ingsw.connection.Client;
import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.enumeration.*;
import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    ArrayList<String> nickList;
    ArrayList<Client> clientList;
    GameController gameController;
    Game game;
    Observable<Message> ob1=new Observable<>();
    Observable<Message> ob2=new Observable<>();
    Resource freeMarble;
    Resource[][] market;
    @Before
    private void createCustomSpGame()
    {

        int i= 0;
        nickList=new ArrayList<>();
        nickList.add("player1");
        clientList=new ArrayList<>();
        gameController=new GameController(nickList);
        ob1.addObserver(gameController);
        game=gameController.getGame();
    }

    @Before
    private void createCustomMpGame()
    {

        int i= 0;
        nickList=new ArrayList<>();
        nickList.add("player1");
        nickList.add("player2");
        clientList=new ArrayList<>();
        gameController=new GameController(nickList);
        ob1.addObserver(gameController);
        ob2.addObserver(gameController);
        game=gameController.getGame();
    }

    @Test
    public void testSpLorenzo()
    {
        createCustomSpGame();
        testStart();
        ArrayList<LorenzoCard> lorenzoDeck=new ArrayList<>();
        lorenzoDeck.add(new LorenzoCard(LorenzoType.MOVE));
        lorenzoDeck.add(new LorenzoCard(LorenzoType.DISCARD,Color.GREEN));
        lorenzoDeck.add(new LorenzoCard(LorenzoType.SHUFFLE));
        game.setLorenzoDeck(lorenzoDeck);
        for(int i=0;i<lorenzoDeck.size();i++)
        {
            game.getCurrentP().setDidAction(true);
            Message message = new Message();
            message.setPlayerNick(game.getCurrentP().getNickname());
            message.setType(MessageType.ACTION);
            message.setPlayerAction(PlayerAction.END_TURN);
            ob1.notify(message);
            switch (lorenzoDeck.get(i).getType())
            {
                case MOVE:
                {
                    assertTrue(game.getLorenzoLocation()==2||game.getLorenzoLocation()==3);
                    break;
                }

                case DISCARD:
                {
                    assertEquals(2,game.getDevGrid()[0].size());
                    break;
                }

                case SHUFFLE:
                {
                    assertEquals(3,game.getLorenzoLocation());
                    break;
                }
            }
        }
    }

    @Test
    public void testStart()
    {
        createCustomSpGame();
        assertEquals(game.getLeaderDeck().size(),16);
        gameController.start();
        assertEquals(game.getLeaderDeck().size(),12);
        int id1=game.getPlayerByNick("player1").getLeader().get(0).getID();
        int id2=game.getPlayerByNick("player1").getLeader().get(1).getID();
        assertEquals(game.getPlayerByNick("player1").getLeader().size(),4);
        Message message=new Message();
        message.setPlayerNick("player1");
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.CHOOSE_LEADER);
        message.setIdLeader1(id1);
        message.setIdLeader2(id2);
        message.setResources(new ArrayList<>());
        ob1.notify(message);
        assertEquals(game.getPlayerByNick("player1").getLeader().size(),2);
    }


    @Test
    public void testStartMp()
    {
        createCustomMpGame();
        assertEquals(game.getLeaderDeck().size(),16);
        gameController.start();
        assertEquals(game.getLeaderDeck().size(),8);
        int id1=game.getPlayerByNick("player1").getLeader().get(0).getID();
        int id2=game.getPlayerByNick("player1").getLeader().get(1).getID();
        assertEquals(game.getPlayerByNick("player1").getLeader().size(),4);

        Message message=new Message();
        message.setPlayerNick("player1");
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.CHOOSE_LEADER);
        message.setIdLeader1(id1);
        message.setIdLeader2(id2);
        message.setResources(new ArrayList<>());
        ob1.notify(message);
        assertEquals(game.getPlayerByNick("player1").getLeader().size(),2);
        id1=game.getPlayerByNick("player2").getLeader().get(0).getID();
        id2=game.getPlayerByNick("player2").getLeader().get(1).getID();
        assertEquals(game.getPlayerByNick("player2").getLeader().size(),4);
        message.setPlayerNick("player2");
        message.setIdLeader1(id1);
        message.setIdLeader2(id2);
        ob2.notify(message);
        assertEquals(game.getPlayerByNick("player2").getLeader().size(),2);
    }

    @Test
    public void testDiscardLeader()
    {
        createCustomSpGame();
        testStart();
        assertEquals(game.getCurrentP().getLeader().size(),2);
        Message message=new Message();
        message.setPlayerNick("player1");
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.DISCARD_LEADER);
        message.setIdLeader1(game.getCurrentP().getLeader().get(0).getID());
        ob1.notify(message);
        assertEquals(game.getCurrentP().getLeader().size(),1);

    }

    /**
     * Adding one of each type of leader card then use all of then to see the effect.
     */
    @Test
    public void testUseLeader()
    {
        createCustomSpGame();
        testStart();
        //add 5 servant for card 6
        for(int i=0;i<5;i++)
        {
            game.getCurrentP().insertStrongBox(Resource.SERVANT);
        }
        assertEquals(game.getCurrentP().getNumResourceStrongbox(Resource.SERVANT),5);
        CardSlot cs=(CardSlot) game.getCurrentP().getDevSlots().get(1);
        cs.addDevCard(new DevCard(19, Level.LV2,Color.YELLOW, 5, Resource.STONE,Resource.STONE,Resource.STONE,Resource.STONE));
        cs.addDevCard(new DevCard(26,Level.LV2,Color.BLUE, 7, Resource.COIN,Resource.COIN,Resource.COIN,Resource.COIN,Resource.COIN));
        cs.addDevCard(new DevCard(22, Level.LV2,Color.BLUE, 6, Resource.COIN, Resource.COIN, Resource.COIN, Resource.STONE, Resource.STONE));
        cs.addDevCard(new DevCard(16, Level.LV2,Color.GREEN, 5, Resource.SHIELD,Resource.SHIELD,Resource.SHIELD,Resource.SHIELD));
        cs.addDevCard(new DevCard(20, Level.LV2,Color.GREEN, 6, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD, Resource.SERVANT, Resource.SERVANT));
        cs.addDevCard(new DevCard(29, Level.LV2,Color.PURPLE, 8, Resource.SERVANT, Resource.SERVANT, Resource.SERVANT, Resource.SHIELD, Resource.SHIELD, Resource.SHIELD));
        //add one of each type of leader card
        LeaderCard lc;
        lc=new LeaderCard(1, 2, AbilityType.DISCOUNT, Resource.SHIELD);
        lc.setDisResLeader(Color.BLUE, Color.PURPLE);
        assertTrue(lc.isPlayable(game.getCurrentP()));
        game.getCurrentP().getLeader().add(lc);
        lc=new LeaderCard(9, 5, AbilityType.RESOURCE, Resource.SHIELD );
        lc.setDisResLeader(Color.GREEN, Color.PURPLE);
        assertTrue(lc.isPlayable(game.getCurrentP()));
        game.getCurrentP().getLeader().add(lc);
        lc=new LeaderCard(12, 4, AbilityType.PRODUCTION, Resource.SHIELD);
        lc.setDevLeader(Color.YELLOW);
        assertTrue(lc.isPlayable(game.getCurrentP()));
        game.getCurrentP().getLeader().add(lc);
        lc=new LeaderCard(6, 3, AbilityType.DEPOT, Resource.SHIELD);
        lc.setDepLeader(Resource.SERVANT);
        assertTrue(lc.isPlayable(game.getCurrentP()));
        game.getCurrentP().getLeader().add(lc);
        assertEquals(game.getCurrentP().getLeader().size(),6);
        for(int i=2;i<6;i++)
        {
            Message message=new Message();
            message.setPlayerNick("player1");
            message.setType(MessageType.ACTION);
            message.setPlayerAction(PlayerAction.USE_LEADER);
            message.setIdLeader1(game.getCurrentP().getLeader().get(i).getID());
            ob1.notify(message);
        }
        assertEquals(game.getCurrentP().getDevSlots().size(),5);
        assertEquals(game.getCurrentP().getSpecialDepots().size(),1);
        assertEquals(game.getCurrentP().getMarketDiscounts().size(),1);
        assertEquals(game.getCurrentP().getDevelopmentDiscounts().size(),1);
    }

    @Test
    public void testMarket()
    {
        createCustomSpGame();
        testStart();
        marketGenerator();
        game.setMarket(market);
        game.setFreeMarble(freeMarble);
        printMarket(game.getMarket(),game.getFreeMarble());
        Message message=new Message();
        message.setPlayerNick("player1");
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.MARKET1);
        message.setRowCol(1);
        message.setResources(new ArrayList<>());
        assertEquals(game.getFreeMarble(),Resource.COIN);
        ob1.notify(message);

        assertEquals(game.getFreeMarble(),Resource.SHIELD);
        assertEquals(game.getRow(1).size(),4);
        assertEquals(game.getCol(1).size(),3);
        printMarket(game.getMarket(),game.getFreeMarble());
        message.setRowCol(6);
    }

    private void marketGenerator() //genera e riempie casualmente la griglia mercato di un oggetto gioco
    {
        int i;
        market = new Resource[3][4];
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
        i = 0;
        freeMarble = origin.get(i);
        for (int j = 0; j<3; j++)
        {
            for (int k = 0; k<4; k++)
            {
                i++;
                this.market[j][k] = origin.get(i);
            }
        }
    }

    private void printMarket (Resource[][] market, Resource freeMarble){
        for (int i = 0; i < 5; i++) {
            if(i<3) {
                for (int j = 0; j < 4; j++) {
                    System.out.format("%1$-10s",market[i][j]);
                }
                System.out.println("< " + (i + 1) + "\n");
            } else if(i==4){
                for(int j=0;j<4;j++) {
                    System.out.format("%1$-10s","  ^  ");
                }
                System.out.println();
            } else {
                for(int j=4;j<8;j++) {
                    System.out.format("%1$-10s","  " + j);
                }
                System.out.println();
            }

        }
        System.out.println("Free: " + freeMarble + "\n");
    }

    @Test
    public void testMp()
    {
        createCustomMpGame();
        testStartMp();
        game.getCurrentP().setDidAction(true);
        Message message = new Message();
        message.setPlayerNick(game.getCurrentP().getNickname());
        String currentP=game.getCurrentP().getNickname();
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.END_TURN);
        ob1.notify(message);
        assertNotEquals(currentP,game.getCurrentP().getNickname());
        game.getCurrentP().insertStrongBox(Resource.SERVANT);
        game.getCurrentP().insertStrongBox(Resource.SERVANT);
        game.getCurrentP().insertStrongBox(Resource.SERVANT);
        game.getCurrentP().insertStrongBox(Resource.SERVANT);
        game.getCurrentP().insertStrongBox(Resource.SERVANT);
        game.getCurrentP().insertStrongBox(Resource.SERVANT);
        game.getCurrentP().setDidAction(true);
        game.setLastRound(true);
        ob2.notify(message);
    }

    @Test
    public void testDepot()
    {
        createCustomSpGame();
        testStart();
        marketGenerator();
        game.setMarket(market);
        game.setFreeMarble(freeMarble);
        printMarket(game.getMarket(),game.getFreeMarble());
        Message message=new Message();
        message.setPlayerNick("player1");
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.MARKET2);
        message.setRowCol(1);
        message.setResources(new ArrayList<>());
        assertEquals(game.getFreeMarble(),Resource.COIN);
        ArrayList<ArrayList<Resource>> depot=new ArrayList<>();
        depot.add(new ArrayList<>());
        depot.add(new ArrayList<>());
        depot.add(new ArrayList<>());
        depot.get(0).add(Resource.SHIELD);
        depot.get(1).add(Resource.STONE);
        depot.get(1).add(Resource.STONE);
        message.setDepot(depot);
        message.setResources(new ArrayList<>());
        message.setSpecialDepots(new ArrayList<>());
        ob1.notify(message);
        assertEquals(depot,game.getCurrentP().getDepots());
        ArrayList<ArrayList<Resource>> depot2=new ArrayList<>();
        depot2.add(new ArrayList<>());
        depot2.add(new ArrayList<>());
        depot2.add(new ArrayList<>());
        depot2.get(0).add(Resource.SHIELD);
        depot2.get(1).add(Resource.STONE);
        depot2.get(1).add(Resource.STONE);
        depot2.get(2).add(Resource.STONE);
        message.setDepot(depot2);
        ob1.notify(message);
        assertEquals(depot,game.getCurrentP().getDepots());

    }

    @Test
    public void testProduction()
    {
        createCustomSpGame();
        testStart();
        HashMap<Resource,Integer> paymentDepot;
        HashMap<Resource,Integer> paymentLeader;
        paymentDepot= Util.createEmptyPaymentHash();
        paymentLeader=Util.createEmptyPaymentHash();
        CardSlot cs= (CardSlot)game.getCurrentP().getDevSlots().get(1);
        DevCard dc= new DevCard(3, Level.LV1, Color.YELLOW, 1, Resource.STONE,Resource.STONE);
        dc.setProductInputList(Resource.SERVANT);
        dc.setProductOutputList(Resource.FAITH);
        cs.addDevCard(dc);
        Message message=new Message();
        message.setPlayerNick("player1");
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.PRODUCTION);
        ArrayList<Integer> temp=new ArrayList<>();
        temp.add(1);
        message.setProductionSlots(temp);
        message.setPaymentDepot(paymentDepot);
        message.setPaymentLeader(paymentLeader);
        message.setExtraInput(new ArrayList<>());
        message.setExtraOutput(new ArrayList<>());
        game.getCurrentP().insertStrongBox(Resource.SERVANT);
        assertEquals(0,game.getCurrentP().getFaithLocation());
        assertEquals(1,game.getCurrentP().getNumResourceStrongbox(Resource.SERVANT));
        ob1.notify(message);
        assertEquals(1,game.getCurrentP().getFaithLocation());
        assertEquals(0,game.getCurrentP().getNumResourceStrongbox(Resource.SERVANT));
        }
    @Test
    public void testPurchase()
    {
        createCustomSpGame();
        testStart();
        HashMap<Resource,Integer> paymentDepot;
        HashMap<Resource,Integer> paymentLeader;
        paymentDepot= Util.createEmptyPaymentHash();
        paymentLeader=Util.createEmptyPaymentHash();
        DevCard dc=game.getDevCard(0);
        for(Resource r: dc.getCostList())
        {
            game.getCurrentP().insertStrongBox(r);
        }
        Message message=new Message();
        message.setPlayerNick("player1");
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.PURCHASE);
        message.setDevCardId(dc.getId());
        message.setSlotToInsert(1);
        message.setPaymentDepot(paymentDepot);
        message.setPaymentLeader(paymentLeader);
        message.setResources(new ArrayList<>());

        ob1.notify(message);
        CardSlot cs=(CardSlot) game.getCurrentP().getDevSlots().get(1);
        assertEquals(dc,cs.getDevCards().peek());
    }

    @Test
    public void testVP()
    {
        createCustomSpGame();
        testStart();
        for (int i =0;i<30;i++)
        {
            game.getCurrentP().insertStrongBox(Resource.COIN);
        }
        assertEquals(6,game.getCurrentP().vp());
    }

    @Test
    public void testWinningCondition()
    {
        createCustomMpGame();
        testStartMp();
        String nick1=game.getPlayerList().get(0).getNickname();
        String nick2=game.getPlayerList().get(1).getNickname();
        game.getCurrentP().setDidAction(true);
        Message message=new Message();
        message.setPlayerNick(nick1);
        message.setType(MessageType.ACTION);
        message.setPlayerAction(PlayerAction.END_TURN);
        ob1.notify(message);
        game.getCurrentP().forwardFaithLocation(24);
        message.setPlayerNick(nick2);
        game.getCurrentP().setDidAction(true);
        ob2.notify(message);
    }

}