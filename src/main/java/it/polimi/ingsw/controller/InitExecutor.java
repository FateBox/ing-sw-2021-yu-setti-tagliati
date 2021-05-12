package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;

public class InitExecutor extends ActionController{


    public InitExecutor(GameController gameController) {
        super(gameController);
    }


    public void ExecuteLeaderChoice(Player player, LeaderCard firstDiscard, LeaderCard secondDiscard)
    {
        if(player.getLeader().contains(firstDiscard) && player.getLeader().contains(secondDiscard))
        {
            player.removeLeader(firstDiscard);
            player.removeLeader(secondDiscard);
        }
        //else
        //    errorMSG
    }

}


