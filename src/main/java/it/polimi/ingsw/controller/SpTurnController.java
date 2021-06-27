package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.DevCard;
import it.polimi.ingsw.model.Game;

import java.util.Stack;

public class SpTurnController implements TurnController{
    Game game;


    public SpTurnController(Game game)
    {
        this.game=game;
    }


    public void nextTurn()
    {
        if (isPlayerWin())
        {
            game.sendWin("You WIN!!!");
        }else if(isLorenzoWin())
        {
            game.sendWin("You Lost...");
        }
        else
        {

        }
    }

    private boolean isPlayerWin()
    {
        if(game.getCurrentP().getFaithLocation()==24|| game.getCurrentP().getDevCardCount()==7)
            return true;
        return false;
    }

    private boolean isLorenzoWin()
    {
        if(game.getLorenzoLocation()==24)
            return true;
        Stack<DevCard>[] devGrid=game.getDevGrid();

        for (int i=0;i<4;i++)
        {
            if(devGrid[i].size()+devGrid[i+4].size()+devGrid[i+8].size()==0)
            {
                return true;
            }
        }
        return false;
    }
}
