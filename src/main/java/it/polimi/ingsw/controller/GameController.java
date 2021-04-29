package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class GameController {
    Game game;
    ArrayList<Player> players;
    /*
     */
    GameController(int playersnum, ArrayList<String> nicknames)
    {
    }
    //insert 4 leaders to chose and call turnController setupFirstRound
    void prepareFirstAction()
    {

    }
    //calculate scores and tell players (using a model attribute forwarded)
    void prepareResults()
    {

    }
    //check if game over, called by turn controller every last turn
    public boolean isGameOver(){
        return false;
    }


}
