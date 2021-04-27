package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;

public class SinglePlayerTurnController {
    Game game;
    Player inTurnPlayer;


    //called after receiving end turn message by view
    //update players possible actions
    void setupNextTurn(){

    }
    //update players possible actions
    //called by gamecontroller & after executeFirstAction to permit next player to chose leader to discard
    void setupFirstRoundTurns(){

    }
    //FIXME: needs rename
    //save choice for each player and if all player have chosen,
    void executeFirstAction(Player player, LeaderCard firstDiscord, LeaderCard secondDiscard){

    }

}
