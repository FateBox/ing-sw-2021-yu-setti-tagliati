package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.DevCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LorenzoCard;

import java.util.Collections;
import java.util.Stack;

public class SpTurnController implements TurnController{
    Game game;
    int lorenzoCounter;

    public SpTurnController(Game game)
    {
        this.game=game;
        this.lorenzoCounter=0;
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
            LorenzoCard lorenzoCard=game.getLorenzoDeck().get(lorenzoCounter);
            switch (lorenzoCard.getType())
            {
                case SHUFFLE:
                {
                    game.forwardLorenzo(1);
                    game.shuffleLorenzo();
                    game.sendLorenzoAnnouncement("My action deck is shuffled");
                    game.sendLorenzoAnnouncement("I'm forwarding by 1, now I'm at "+ game.getLorenzoLocation()+"/24.");
                    lorenzoCounter=0;
                    break;
                }
                case MOVE:
                {
                    game.forwardLorenzo(2);
                    game.sendLorenzoAnnouncement("I'm forwarding by 2, now I'm at "+ game.getLorenzoLocation()+"/24.");
                    lorenzoCounter++;
                    break;
                }
                case DISCARD: {
                    game.lorenzoDiscard(lorenzoCard.getColor());
                    game.sendLorenzoAnnouncement("I took 2 "+ lorenzoCard.getColor().toString()+ " card");
                    lorenzoCounter++;
                }
            }
            //game.sendUpdateNextTurn();
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
