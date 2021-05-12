package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

public class MultiPlayerTurnController extends TurnController{

    Game game;
    GameController gameController;
    boolean gameOver;

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
        if(!gameOver && isGameOver()) {
            gameOver = true;
            //notify gameover
        }
        if(gameOver && game.getCurrentPlayer() == game.getLastPlayer())
        {
            gameController.prepareResults();
        }
        game.nextPlayer();
        //notify nextPlayer
    }

    public boolean isGameOver()
    {
        boolean result = false;
        for(Player p : game.getPlayerList()) {
            if (game.getPositionPlayer(p) == 20 || p.has7DevCards())
                result = true;
        }
        return result;

    }
    public MultiPlayerTurnController(GameController gameController)
    {
        this.gameController = gameController;
        game = gameController.getGame();
        gameOver = false;
        //playerInTurn = game.getPlayer(0);
    }
}
