package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

public class MultiPlayerTurnController {

    Game game;
    GameController gameController;
    Player playerInTurn;

    /**
     *
     *
     *
     *
     */
    /**
     * verifica end game e nel caso sia arrivato modifica game
     * e modifica PlayerinTurn
     *  quando è finito l'ultimo round chiama gameController.prepareResult
     */
    public void nextTurn()
    {

    }
    public boolean isGameOver()
    {

        return false;

    }
    public MultiPlayerTurnController(GameController gameController)
    {
        this.gameController = gameController;
        game = gameController.getGame();
        //playerInTurn = game.getPlayer(0);
    }
}
