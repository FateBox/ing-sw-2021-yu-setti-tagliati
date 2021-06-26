package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
public interface TurnController {
    //turn controllers does 2 things, when player choose to end his turn, calls nextTurn, when it happens, it check the condition for *endGame*, if true, changes endGame attribute in model.
    void nextTurn();//this is called when player decide to end his turn, in MP changes Current player in the model, in SP make lorenzo action;
}
