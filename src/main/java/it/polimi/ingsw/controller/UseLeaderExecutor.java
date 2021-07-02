package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.LeaderSlot;
import it.polimi.ingsw.model.Player;

/**
 * Executor for USE LEADER action
 */
public class UseLeaderExecutor {



    /**
     * Information
     * Which leader is being used
     *
     * Operation to be executed
     * Verify whether this leader is present or not
     * Verify whether this leader is played or not
     * Verify all requirement (leader.isPlayable)
     * Use leader (leader.use())
     */


    private Game game;

    /**
     * Constructor
     * @param game game
     */
    public UseLeaderExecutor(Game game)
    {
        this.game=game;
    }

    /**
     * Given leader id, verify whether player has this leader and whether player activated this leader or not
     * @param leaderID id of leader
     * @return a boolean
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
     * Given leader id, use it and changes players model based on leader type
     * @param leaderID leader to be discarded
     */
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
