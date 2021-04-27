package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import sun.jvm.hotspot.utilities.Observable;
import sun.jvm.hotspot.utilities.Observer;

import java.util.ArrayList;

public class GameController implements Observer {
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
    boolean isGameOver(){
        return false;
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
