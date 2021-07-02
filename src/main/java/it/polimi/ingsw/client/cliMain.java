package it.polimi.ingsw.client;
//classe provvisoria per testare cli


import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class cliMain {


    public static void main(String[] args ){
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(new Player("Federico"));
        playerList.add(new Player("Mario"));

        Game g = new Game(playerList);
        ArrayList<DevCard> deck = new ArrayList<DevCard>();
        Cli c2=new Cli();
        Cli c1=new Cli();
        View v1 = new View(c1);
        View v2 = new View(c2);
        for (Stack dc : g.getDevGrid())
        {
            deck.add((DevCard) dc.peek());
        }
        ArrayList<String > nl = new ArrayList<>();
        nl.add(playerList.get(0).getNickname());
        nl.add(playerList.get(1).getNickname());
        v1.setPlayerView(nl.get(0), nl);
        v2.setPlayerView(nl.get(1), nl);
        v1.setGameView(g.getMarket(), g.getFreeMarble(), g.getPopeSpace(), deck);
        v2.setGameView(g.getMarket(), g.getFreeMarble(), g.getPopeSpace(), deck);
        v1.setCli(true);
        v2.setCli(true);
        v1.getP().getLeaderDepots().add(new SpecialDepot(Resource.COIN));
        v1.getP().getLeaderDiscount().add(Resource.COIN);
        v1.getP().getLeaderDiscount().add(Resource.SHIELD);
        v1.getP().getLeaderMarket().add(Resource.COIN); //Printare leader
        v1.getP().getLeaderMarket().add(Resource.SHIELD);
        v1.getP().getLeaderCards().add(g.getLeaderDeck().pop());
        v1.getP().getLeaderCards().add(g.getLeaderDeck().pop());
        v1.getP().getLeaderCards().add(g.getLeaderDeck().pop());
        v1.getP().getLeaderCards().add(g.getLeaderDeck().pop());
        v1.getP().getDevSlots().add(new BasicSlot());
        v1.getP().getDevSlots().add(new CardSlot());
        v1.getP().getDevSlots().add(new CardSlot());
        v1.getP().getDevSlots().add(new CardSlot());
        v1.getP().getDevSlots().add(new LeaderSlot(Resource.STONE));
        ((CardSlot)v1.getP().getDevSlots().get(1)).addDevCard(g.drawDevCard(1));
        ((CardSlot)v1.getP().getDevSlots().get(2)).addDevCard(g.drawDevCard(0));
        ((CardSlot)v1.getP().getDevSlots().get(2)).addDevCard(g.drawDevCard(5));
        v1.getPlayersInfo().get(nl.get(1)).getLeaderCards().add(g.getLeaderDeck().pop());
        v1.getPlayersInfo().get(nl.get(1)).getLeaderCards().add(g.getLeaderDeck().pop());
        ArrayList<Resource> a = new ArrayList<>();
        a.add(Resource.SHIELD);
        a.add(Resource.COIN);
        a.add(Resource.COIN);
        v1.setGain(a);
        boolean[] ps = {false, true, true};
        v1.setPopeSpace(ps);
        //inizializzazione

        //test
        System.out.println(v1.getNickClient()+" "+v2.getNickClient()+" "+ v2.getCurrentPlayer());
        System.out.println(v1.getP().getDepot().size());
        for(PlayerInformation p: v1.getPlayersInfo().values())
        {
            System.out.println(p.getNick());
        }
        System.out.println();
        v1.askAction();

    }

}
