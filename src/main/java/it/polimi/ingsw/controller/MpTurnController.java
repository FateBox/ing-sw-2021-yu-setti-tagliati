package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

/**
 * It represents the turn controller for the multiplayer game.
 */
public class MpTurnController implements TurnController{
    Game game;

    public MpTurnController(Game game)
    {
        this.game=game;
    }


    /**
     * Handles the turns and updates them in the game.
     * It calls the next player if available.
     * If the current player is the last one and it's the last round, the game ends.
     */
    public void nextTurn()
    {
        if(game.isGameOverMP())
        {
            game.setLastRound(true);
        }
        if(game.isLastRound() && game.getCurrentP().equals(game.getPlayerList().get(game.getPlayerList().size()-1)))
        {
            //end game, count VPs and get ranking
            ArrayList<Integer> ranking=new ArrayList<>();
            for(Player p:game.getPlayerList())
            {
                ranking.add(p.vp());
            }
            game.sendRanking(ranking);
        }
        else{
            //next player and send announcement
            game.nextPlayer();
            game.sendUpdateNextTurn();
            game.sendLorenzoAnnouncement("Now it's "+ game.getCurrentP().getNickname()+"'s round.");
        }
    }
}
