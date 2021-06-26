package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.LeaderSlot;
import it.polimi.ingsw.model.Player;

public class UseLeaderExecutor {



    /**
     * informazioni
     * quale leader utilizzare
     *
     * operazioni da eseguire
     * verificare che il leader sia presente e non stato già giocato
     * verificare i requisiti (leader.isPlayable)
     * usa il leader (leader.use())
     */

    private Game game;
    public UseLeaderExecutor(Game game)
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
        LeaderCard leaderCard=getLeaderCard(leaderID);
        if(leaderCard.isPlayable(game.getCurrentP()))
        {
            use(leaderID);
            game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+" activated one of his leader card");
        }
        else{
            game.sendErrorToCurrentPlayer("This leader is not playable! Requirement's are not met.");
        }

    }

    private void use (int leaderID)
    {
        LeaderCard leaderCard = getLeaderCard(leaderID);

        if(leaderCard!=null)
        {
            switch (leaderCard.getType()) {
                case DISCOUNT:
                {
                    game.getCurrentP().addDevelopmentDiscounts(leaderCard.getRes());
                    break;
                }
                case PRODUCTION:
                {
                    game.getCurrentP().getDevSlots().add(new LeaderSlot(leaderCard.getRes()));
                    break;
                }
                case DEPOT:
                {
                    game.getCurrentP().addSpecialDepot(leaderCard.getRes());
                    break;
                }
                case RESOURCE:
                {
                    game.getCurrentP().addMarketDiscounts(leaderCard.getRes());
                    break;
                }
                default:

            }
            leaderCard.setActive(true);
        }
    }

    private LeaderCard getLeaderCard(int leaderID)
    {
        LeaderCard leaderCard = null;
        for(LeaderCard l: game.getCurrentP().getLeader())
        {
            if (l.getID()==leaderID)
            {
                return l;
            }
        }
        return null;
    }
}
