package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class GameController {
    Game game;
    ArrayList<Player> players;
    TurnController turnController;
    MarketExecutor marketExecutor;
    DiscardLeaderExecutor discardLeaderExecutor;
    ProductionExecutor productionExecutor;
    PurchaseExecutor purchaseExecutor;
    UseLeaderExecutor useLeaderExecutor;
    /*
     */

    //insert 4 leaders to chose and call turnController setupFirstRound
    public void prepareFirstAction()
    {
        for(Player p : players) {
            p.addLeader(game.drawLeaderCard())
                    .addLeader(game.drawLeaderCard())
                    .addLeader(game.drawLeaderCard())
                    .addLeader(game.drawLeaderCard());
        }
        //notify
    }
    //calculate scores and tell players (using a model attribute forwarded)
    public void prepareResults()
    {


    }
    //check if game over, called by turn controller every last turn
    public boolean isGameOver(){
        return false;
    }
    public GameController(ArrayList<String> nicknames)
    {
        players= new ArrayList<>();
        if(nicknames.size()<4)
            for( int i=0; i<players.size(); i++ )
        players.add(new Player(nicknames.get(i)));
        game = new Game(players);

    }


    public Game getGame() {
        return game;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public MarketExecutor getMarketExecutor() {
        return marketExecutor;
    }

    public DiscardLeaderExecutor getDiscardLeaderExecutor() {
        return discardLeaderExecutor;
    }

    public ProductionExecutor getProductionExecutor() {
        return productionExecutor;
    }

    public PurchaseExecutor getPurchaseExecutor() {
        return purchaseExecutor;
    }

    public UseLeaderExecutor getUseLeaderExecutor() {
        return useLeaderExecutor;
    }
}
