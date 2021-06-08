package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GameController extends Observable implements Observer {

    private Game game;
    final ArrayList<Player> players;
    //TurnController turnController;
    MarketExecutor marketExecutor;
    DiscardLeaderExecutor discardLeaderExecutor;
    ProductionExecutor productionExecutor;
    PurchaseExecutor purchaseExecutor;
    UseLeaderExecutor useLeaderExecutor;
    /*
     */

    public GameController(ArrayList<String> nicknames) {
        players = new ArrayList<>();
        Collections.shuffle(nicknames);
        for (String nick : nicknames)
        {
            players.add(new Player(nick));
        }
        game = new Game(players);
        /*if (nicknames.size() > 1)
            turnController = new MultiPlayerTurnController(this);
        else
            turnController = new SinglePlayerTurnController();*/
        marketExecutor = new MarketExecutor(this);
        discardLeaderExecutor = new DiscardLeaderExecutor(this);
        productionExecutor = new ProductionExecutor(this);
    }

    @Override
    public void update(Object message) {

    }

    public void decoder(Object message)
    {

    }

    //insert 4 leaders to choose and call turnController setupFirstRound
    public void prepareFirstAction()
    {
        for(Player p : players) {
            p.addLeader(game.drawLeaderCard());
            p.addLeader(game.drawLeaderCard());
            p.addLeader(game.drawLeaderCard());
            p.addLeader(game.drawLeaderCard());
        }
        //notify
    }

    public void setupLeader(Player player, int id1, int id2)
    {

    }
}
