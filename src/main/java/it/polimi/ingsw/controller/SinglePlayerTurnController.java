package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;

public class SinglePlayerTurnController extends TurnController{
    Game game;
    GameController Controller;
    Player inTurnPlayer;


    //called after receiving end turn message by view
    //update players possible actions
    void setupNextTurn(){
        game.nextPlayer();
        //update stuff
    }
    //update players possible actions
    //called by gamecontroller & after executeFirstAction to permit next player to chose leader to discard
    public void setupFirstRoundTurns(){
        game.nextPlayer();
    }
    //FIXME: needs rename
    //save choice for each player and if all player have chosen,


}
