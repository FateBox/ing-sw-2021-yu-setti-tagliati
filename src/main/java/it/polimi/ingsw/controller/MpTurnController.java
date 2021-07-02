package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public class MpTurnController implements TurnController{
    Game game;

    public MpTurnController(Game game)
    {
        this.game=game;
    }

    public void nextTurn()//calls nextPlayer in game, if current player is last player and it's lastRound, end game.
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
