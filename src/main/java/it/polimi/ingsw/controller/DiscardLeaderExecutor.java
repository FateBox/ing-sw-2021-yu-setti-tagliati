package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

public class DiscardLeaderExecutor implements ActionExecutor {
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

    private Game game;
    public DiscardLeaderExecutor(Game game)
    {
        this.game=game;
    }

    public boolean verifyData(int leaderID)
    {
        if (!game.getCurrentP().hasLeader(leaderID))
            return false;
        if(game.getCurrentP().isLeaderActive(leaderID))
            return false;

        return true;
    }

    public void execute(int leaderID)
    {
        game.getCurrentP().getLeader().removeIf(lc -> lc.getID() == leaderID);
        game.forwardPlayer(game.getIndexPlayer(game.getCurrentP()),1 );
    }

}




