package it.polimi.ingsw.controller;

public interface TurnController {
    //turn controllers does 2 things, when player choose to end his turn, calls nextTurn, when it happens, it check the condition for *endGame*, if true, changes endGame attribute in model.
    boolean isGameOver();//check if game has reached "end_Game"
    void nextTurn();//this is called when player decide to end his turn, changes Current player in the model;
}
