package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

public class MpTurnController implements TurnController{
    Game game;

    public MpTurnController(Game game)
    {
        this.game=game;
    }
    public boolean isGameOver()
    {

    }

    public void nextTurn()//calls nextPlayer in game, if current player is last player and it's lastRound, end game.
    {

    }
}
