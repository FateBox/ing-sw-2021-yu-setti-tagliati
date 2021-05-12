package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public abstract class TurnController {

    Game game;

    public void setupFirstRoundTurns()
    {
    }

    public Player getPlayerInTurn()
    {
        return game.getCurrentP();
    }



}
