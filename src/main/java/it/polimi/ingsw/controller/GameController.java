package it.polimi.ingsw.controller;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.enumeration.PlayerAction;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GameController extends Observable<Message> implements Observer<Message> {

    private Game game;
    final ArrayList<Player> players;
    //TurnController turnController;
    private MarketExecutor marketExecutor;
    private DiscardLeaderExecutor discardLeaderExecutor;
    private ProductionExecutor productionExecutor;
    private PurchaseExecutor purchaseExecutor;
    private UseLeaderExecutor useLeaderExecutor;
    private TurnController turnController;
    /*
     */

    public GameController(ArrayList<String> nicknames) {
        players = new ArrayList<>();
        Collections.shuffle(nicknames);
        for (String nick : nicknames)
        {
            players.add(new Player(nick));
        }
        game = new Game(players);
        if (nicknames.size() > 1) {
            turnController = new MpTurnController(game);
        }
        else{
            turnController = new SpTurnController(game);
        }
        marketExecutor = new MarketExecutor(game);
        purchaseExecutor=new PurchaseExecutor(game);
        productionExecutor = new ProductionExecutor(game);
        useLeaderExecutor= new UseLeaderExecutor(game);
        discardLeaderExecutor = new DiscardLeaderExecutor(game);

    }

    @Override
    public void update(Message message) {
        if(game.isReadyLeader())
        {
            if(game.getCurrentP().getNickname().equals(message.getPlayerNick()))//message is taken into consideration only if the sender is the player on turn.
            {
                switch (message.getPlayerAction())
                {
                    case MARKET1://with chosen row/col, modify market and gained resource (forward in case of red marble)
                    {
                        if(game.getCurrentP().isDidAction())
                        {
                            game.sendErrorToCurrentPlayer("You already played an action!");
                            break;
                        }
                        if (message.getRowCol()<0 || message.getRowCol()>6)
                        {
                            game.sendErrorToCurrentPlayer("Wrong row or column");
                            break;
                        }

                        if(!marketExecutor.checkResArray(message.getResources()))
                        {
                            game.sendErrorToCurrentPlayer("Wrong Market discount");
                            break;
                        }
                        marketExecutor.choiceResource(message.getRowCol());
                        marketExecutor.manualChange(message.getResources());
                        break;
                    }
                    case MARKET2://insert into depot and forward in case of remaining
                    {
                        if(game.getCurrentP().isDidAction())
                        {
                            game.sendErrorToCurrentPlayer("You already played an action!");
                            break;
                        }
                        if(!marketExecutor.checkNumResource(createClientCount(message.getDepot(),message.getSpecialDepots())))
                        {
                            game.sendErrorToCurrentPlayer("Resources that you have do not match to what you had before");
                            break;
                        }
                        if(!marketExecutor.checkDepot(message.getDepot(), message.getSpecialDepots()))
                        {
                            game.sendErrorToCurrentPlayer("Depot is not correct");
                            break;
                        }
                        game.getCurrentP().setDepots(message.getDepot());
                        game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+" ");
                        game.getCurrentP().setSpecialDepots(message.getSpecialDepots());
                        game.forwardOtherPlayers(game.getIndexPlayer(game.getCurrentP()), message.getResources().size());
                        game.getCurrentP().setDidAction(true);
                        break;
                    }
                    case PURCHASE:
                    {
                        if(game.getCurrentP().isDidAction())
                        {
                            game.sendErrorToCurrentPlayer("You already played an action!");
                            break;
                        }
                        if(purchaseExecutor.verifyData(message.getDevCardId(), message.getSlotToInsert(), message.getPaymentDepot(), message.getPaymentLeader(), message.getResources()))
                        {
                            purchaseExecutor.execute(message.getSlotToInsert(), message.getPaymentDepot(), message.getPaymentLeader(), message.getResources());
                            game.getCurrentP().setDidAction(true);
                        }
                        break;
                    }
                    case PRODUCTION:
                    {
                        if(game.getCurrentP().isDidAction())
                        {
                            game.sendErrorToCurrentPlayer("You already played an action!");
                            break;
                        }
                        if(productionExecutor.verifyData())
                        {
                            productionExecutor.execute(message.getProductionSlots(), message.getPaymentDepot(), message.getPaymentLeader(), message.getExtraOutput());
                            game.getCurrentP().setDidAction(true);
                        }
                        break;
                    }
                    case USE_LEADER:
                    {
                        if(useLeaderExecutor.verifyData(message.getIdLeader1()))
                        {
                            useLeaderExecutor.execute(message.getIdLeader1());
                        }
                        else
                        {
                            game.sendErrorToCurrentPlayer("Leader Card is not present or it's already played");
                        }
                        break;
                    }
                    case DISCARD_LEADER:
                    {
                        if (discardLeaderExecutor.verifyData(message.getIdLeader1()))
                        {
                            discardLeaderExecutor.execute(message.getIdLeader1());
                        }
                        else {
                            game.sendErrorToCurrentPlayer("This leader cannot be discarded!");
                        }
                        break;
                    }
                    case END_TURN:
                    {
                        if(!(game.getCurrentP().isDidAction()))
                        {
                            game.sendErrorToCurrentPlayer("You have to play an action before ending this turn!");
                            break;
                        }
                        game.getCurrentP().setDidAction(false);
                        turnController.nextTurn();
                        break;
                    }
                    default:
                    {
                        game.sendErrorToCurrentPlayer("wrong action type");
                    }
                }

            }

        }
        else
        {
            if(message.getPlayerAction().equals(PlayerAction.CHOOSE_LEADER))
            {

            }
            else{
                game.sendErrorToCurrentPlayer("Error in leader choosing stage");
            }
        }
    }

    //Creates a hash table with Resource as key and quantity as value.
    private HashMap<Resource,Integer> createClientCount(ArrayList<ArrayList<Resource>> depot, ArrayList<SpecialDepot> specialDepots)
    {
        HashMap<Resource,Integer> clientCount=new HashMap<>();
        for (ArrayList<Resource> row: depot)
        {
            for(Resource r:row)
            {
                clientCount.put(r,clientCount.get(r)+1);
            }
        }
        for(SpecialDepot s:specialDepots)
        {
            clientCount.put(s.getRes(),clientCount.get(s.getRes())+s.getQuantity());
        }

        return clientCount;
    }

    //insert 4 leaders to choose and call turnController setupFirstRound
    public void prepare4Leader()
    {
        for(Player p : players) {
            p.addLeader(game.drawLeaderCard());
            p.addLeader(game.drawLeaderCard());
            p.addLeader(game.drawLeaderCard());
            p.addLeader(game.drawLeaderCard());
        }
        //notify

    }

    public void setupLeader(String nickname, int id1, int id2)
    {
        Player player= game.getPlayerByNick(nickname);
        player.getLeader().removeIf(lc -> (lc.getID()!=id1 || lc.getID()!=id2));
    }

    private boolean checkChosenLeader(String nickname, int id1,int id2)
    {
        Player player = game.getPlayerByNick(nickname);
        if(!(player.getLeader().contains(player.getLeaderById(id1)) && player.getLeader().contains(player.getLeaderById(id2))))
            return false;
        return true;
    }

    private boolean checkAllIsLeaderPicked()//condition to start the game.
    {

        return true;
    }


}
