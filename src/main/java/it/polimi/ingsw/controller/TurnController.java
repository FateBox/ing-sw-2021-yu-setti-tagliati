package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public abstract class TurnController {

    Game game;

    public void setupFirstRoundTurns()
    {   }

    public void executeFirstAction(Player player, LeaderCard firstDiscard, LeaderCard secondDiscard){

        if(player.getLeader().contains(firstDiscard) && player.getLeader().contains(secondDiscard))
        {
            player.removeLeader(firstDiscard);
            player.removeLeader(secondDiscard);
        }
        setupFirstRoundTurns();
        //else
        //    errorMSG
    }

    public Player getPlayerInTurn()
    {
        return game.getCurrentP();
    }



}
