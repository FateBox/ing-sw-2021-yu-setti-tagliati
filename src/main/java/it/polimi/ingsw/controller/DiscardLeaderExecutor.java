package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

/**
 * It works on the leaders that the player wants to discard.
 * Operations to do:
 * - Checks if the chosen leader card is present or is already used.
 * - Removes the leader card from the player.
 * - Adds a faith point to the player (game.forward(player))
 * - If it's not present, it calls a Util method to send an Error message to the player.
 */
public class DiscardLeaderExecutor implements ActionExecutor {

    private Game game;
    public DiscardLeaderExecutor(Game game)
    {
        this.game=game;
    }

    /**
     * Checks if the player has the chosen leader card, or if the card is already activated.
     * @param leaderID ID of the leader card.
     * @return True if it's possible to remove it, false otherwise.
     */
    public boolean verifyData(int leaderID)
    {
        if (!game.getCurrentP().hasLeader(leaderID))
            return false;
        if(game.getCurrentP().isLeaderActive(leaderID))
            return false;

        return true;
    }

    /**
     * Executes the leader card removal.
     * @param leaderID ID of the chosen leader card.
     */
    public void execute(int leaderID)
    {
        game.getCurrentP().getLeader().removeIf(lc -> lc.getID() == leaderID);
        game.forwardPlayer(game.getIndexPlayer(game.getCurrentP()),1 );
        game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+" Discarded one of his leader card and moved forward by 1 box. "+ game.getCurrentP().getFaithLocation()+"/24");

    }
}




