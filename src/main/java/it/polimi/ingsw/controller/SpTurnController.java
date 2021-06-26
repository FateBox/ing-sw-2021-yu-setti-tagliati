package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
public class SpTurnController implements TurnController{
    Game game;


    public SpTurnController(Game game)
    {
        this.game=game;
    }


    public void nextTurn()
    {

    }
}
