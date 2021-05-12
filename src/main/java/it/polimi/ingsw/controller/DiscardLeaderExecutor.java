package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.LeaderCard;

import java.awt.*;


public class DiscardLeaderExecutor extends ActionController {


    void execute(LeaderCard l) {
         Player player = turnController.getPlayerInTurn();
         try{
             player.removeLeader(l);
         } catch (Exception e)
        {
            //player.notify("string opcode");
        }
    }
    DiscardLeaderExecutor(GameController g)
    {
        super(g);
    }

    }


    /**
     * informazioni
     * quale leader l'utente vuole scartare
     *
     * operazioni da eseguire
     * verifica se tale leader sia presente o già utilizzato
     * rimuovi il leader dal player
     * aggiungi un punto fede (game.forward(player))
     * se non presente chiama un metodo di util per tornare un messaggio d'errore all'utente
     */

