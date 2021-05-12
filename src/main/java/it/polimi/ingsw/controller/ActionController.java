package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.Game;

public abstract class ActionController {
    Game game;
    TurnController turnController;
    public ActionController(GameController gameController)
    {
        this.game= gameController.getGame();
        this.turnController = gameController.getTurnController();
    }


    public void executeDepotInsert(Resource resource,int row)
    {
        try
        {
            game.getCurrentPlayer().insertDepots(resource, row);
        }
        catch (Exception e)
        {
            //notify error
        }

    }
    public void executeStrongBoxInsert(Resource... r)
    {
        try{
            game.getCurrentPlayer().insertStrongBox(r);
        }
        catch (Exception e)
        {
            //forward error message
        }
    }

    /*

    every action controller must have methods called after server receives right command message, that modify model.
    these methods can store action state inside the class if necessary and forward new possibleOperation through model.

    YES NOW I UNDERSTAND WHY IT IS WORTH CREATING A NEW ACTION CONTROLLER EVERY ACTION, but clearing old state does the same

    now the real problems is, can we just implement a "weird RPC" that return values through model observable.
    we do need to use a decoder class between virtualView and ALL controller classes?
    do we have to store message information in virtual view and observe that by a decoder class?

    all that solutions seems transparent to controller in my opinion.
    is any of them surely valid?
     */
}
