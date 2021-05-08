package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumeration.PlayerAction;
import it.polimi.ingsw.model.Game;

import java.io.IOError;
import java.io.IOException;

public class MultiPlayerTurnController {

    GameController gameController;
    private int playerInTurn;
    private int playerNumber;
    private boolean endgame;
    private MarketExecutor marketExecutor;
    private PurchaseExecutor purchaseExecutor;
    private ProductionExecutor productionExecutor;
    private UseLeaderExecutor useLeaderExecutor;
    private DiscardLeaderExecutor discardLeaderExecutor;
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
    public MultiPlayerTurnController(GameController g,int playerNumber)
    {
        this.gameController=g;
        this.playerNumber=playerNumber;
        this.playerInTurn=0;
        this.endgame=false;
    }
    public void nextTurn()
    {
        if(playerInTurn==3)
        {
            playerInTurn=0;
        }
        else
        {
            playerInTurn++;
        }
        //Here it should also modify player in turn inside our model in order to let view know who can make their actions.
    }


    public void newActionExecutor(PlayerAction action) throws IOException {
        switch (action)
        {
            case MARKET: new MarketExecutor();break;
            case PRODUCTION:new ProductionExecutor();break;
            case PURCHASE:new PurchaseExecutor();break;
            case USE_LEADER:new UseLeaderExecutor();break;
            case DISCARD_LEADER:new DiscardLeaderExecutor();break;
            default:throw new IOException("Invalid Action");
        }
    }
}
