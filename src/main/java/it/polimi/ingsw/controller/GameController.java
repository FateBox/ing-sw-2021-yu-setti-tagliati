package it.polimi.ingsw.controller;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.enumeration.MessageType;
import it.polimi.ingsw.enumeration.PlayerAction;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * It represents the main controller of the game and it handles the minor controllers.
 * It invokes specific methods of different executors in performing operations.
 */
public class GameController implements Observer<Message> {

    private final Game game;
    final ArrayList<Player> players;


    //TurnController turnController;
    private final MarketExecutor marketExecutor;
    private final DiscardLeaderExecutor discardLeaderExecutor;
    private final ProductionExecutor productionExecutor;
    private final PurchaseExecutor purchaseExecutor;
    private final UseLeaderExecutor useLeaderExecutor;
    private final TurnController turnController;

    /**
     * It initializes the gameController with the players' nicknames list.
     * @param nicknames List containing all the players' nicknames in this match.
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

    /**
     * It receives messages from connection and handles the operations the player wants to do.
     * @param message Message sent by the player.
     */
    @Override
    public synchronized void update(Message message) {

        System.out.println("In GC: from " + message.getPlayerNick() + ": " + message.getType());

        if(game.isReadyLeader())
        {
            if(game.getCurrentP().getNickname().equals(message.getPlayerNick()))//message is taken into consideration only if the sender is the player on turn.
            {
                switch (message.getPlayerAction()) {
                //with chosen row/col, modify market and gained resource (forward in case of red marble)
                    case MARKET1 -> {
                        System.out.println("received market1 message");
                        if (game.getCurrentP().isDidAction()) {
                            game.sendErrorToCurrentPlayer("You already played an action!");
                            break;
                        }
                        if (message.getRowCol() < 0 || message.getRowCol() > 6) {
                            game.sendErrorToCurrentPlayer("Wrong row or column");
                            break;
                        }

                        if (!marketExecutor.checkResArray(message.getResources())) {
                            System.out.println(message.getResources());
                            game.sendErrorToCurrentPlayer("Wrong Market discount");
                            break;
                        }
                        marketExecutor.choiceResource(message.getRowCol());
                        marketExecutor.manualChange(message.getResources());
                        game.sendUpdateMarket1();
                        break;
                    }
                    //insert into depot and forward in case of remaining
                    case MARKET2 -> {
                        System.out.println("received market2 message");
                        if (game.getCurrentP().isDidAction()) {
                            game.sendErrorToCurrentPlayer("You already played an action!");
                            break;
                        }
                        if (!marketExecutor.checkDepot(message.getDepot(), message.getSpecialDepots())) {
                            game.sendErrorToCurrentPlayer("Depot is not correct");
                            break;
                        }
                        game.getCurrentP().setDepots(message.getDepot());
                        game.getCurrentP().setSpecialDepots(message.getSpecialDepots());
                        game.sendLorenzoAnnouncement(game.getCurrentP().getNickname() + " modified his depots");
                        game.forwardOtherPlayers(game.getIndexPlayer(game.getCurrentP()), message.getResources().size());
                        game.getCurrentP().setDidAction(true);
                        game.sendUpdateMarket2();
                        game.sendEndAction();
                        break;
                    }
                    case PURCHASE -> {
                        System.out.println("received purchase message");
                        if (game.getCurrentP().isDidAction()) {
                            game.sendErrorToCurrentPlayer("You already played an action!");
                            break;
                        }
                        if (purchaseExecutor.verifyData(message.getDevCardId(), message.getSlotToInsert(), message.getPaymentDepot(), message.getPaymentLeader(), message.getResources())) {
                            System.out.println("into purchase execute, verify ok");
                            purchaseExecutor.execute(message.getSlotToInsert(), message.getPaymentDepot(), message.getPaymentLeader(), message.getResources());
                            game.getCurrentP().setDidAction(true);
                            game.sendUpdatePurchase();
                            game.sendEndAction();
                        } else {
                            game.sendErrorToCurrentPlayer("Payment error!");
                        }
                        break;
                    }
                    case PRODUCTION -> {
                        System.out.println("received production message");
                        if (game.getCurrentP().isDidAction()) {
                            System.out.println("turn check passed");
                            game.sendErrorToCurrentPlayer("You already played an action!");
                            break;
                        }
                        if (productionExecutor.verifyData(message.getProductionSlots(), message.getPaymentDepot(), message.getPaymentLeader(), message.getExtraInput())) {
                            System.out.println("verify passed");
                            productionExecutor.execute(message.getProductionSlots(), message.getPaymentDepot(), message.getPaymentLeader(), message.getExtraOutput());
                            game.getCurrentP().setDidAction(true);
                            game.sendUpdateProduction();
                            game.sendEndAction();
                        } else {
                            game.sendErrorToCurrentPlayer("Payment error!");
                        }
                        break;
                    }
                    case USE_LEADER -> {
                        System.out.println("received useleader message");
                        if (useLeaderExecutor.verifyData(message.getIdLeader1())) {
                            useLeaderExecutor.execute(message.getIdLeader1());
                            game.sendUpdateUseLeader();
                            game.sendEndAction();
                        } else {
                            game.sendErrorToCurrentPlayer("Leader Card is not present or it's already played");
                        }

                        break;
                    }
                    case DISCARD_LEADER -> {
                        System.out.println("received discardleader message");
                        if (discardLeaderExecutor.verifyData(message.getIdLeader1())) {
                            discardLeaderExecutor.execute(message.getIdLeader1());
                            game.sendUpdateDiscardLeader();
                            game.sendEndAction();
                        } else {
                            game.sendErrorToCurrentPlayer("This leader cannot be discarded!");
                        }
                        break;
                    }
                    case END_TURN -> {
                        System.out.println("received endturn message");
                        if (!(game.getCurrentP().isDidAction())) {
                            game.sendErrorToCurrentPlayer("You have to play an action before ending this turn!");
                            System.out.println("sent play action error");
                            break;
                        }
                        game.getCurrentP().setDidAction(false);
                        turnController.nextTurn();
                        break;
                    }
                    default -> {
                        game.sendErrorToCurrentPlayer("wrong action type");
                    }
                }

            }

        }
        else
        {
            if(message.getPlayerAction().equals(PlayerAction.CHOOSE_LEADER))
            {
                System.out.println("received choose leader message");
                boolean allReady=true;
                setupLeader(message.getPlayerNick(), message.getIdLeader1(), message.getIdLeader2());
                setupResource(message.getPlayerNick(), message.getResources());
                game.sendUpdateChooseLeader(message.getPlayerNick());
                game.getPlayerByNick(message.getPlayerNick()).setLeaderPicked(true);
                for (Player p: game.getPlayerList())
                {
                    System.out.println(p.getLeader().size());
                    if(p.getLeader().size()>2)
                    {
                        allReady=false;
                    }
                }
                if(allReady)
                {
                    System.out.println("all ready");
                    game.setReadyLeader(true);

                    game.sendUpdateNextTurn();
                }
                System.out.println();
            }
            else{
                game.sendErrorToCurrentPlayer("Error in leader choosing stage");
            }
        }
    }
    //insert 4 leaders to choose and call turnController setupFirstRound
    private void prepare4Leader()
    {
        for(Player p : players) {
            p.addLeader(game.drawLeaderCard());
            p.addLeader(game.drawLeaderCard());
            p.addLeader(game.drawLeaderCard());
            p.addLeader(game.drawLeaderCard());


            game.sendUnicast(p.getNickname(),sendUpdate4Leader(p));
        }
    }

    private Message sendUpdate4Leader(Player p)
    {
        Message m=new Message();
        m.setType(MessageType.UPDATE);
        m.setBroadCast(false);
        m.setPlayerAction(PlayerAction.LEADER_READY);
        m.setLeaderDeck(p.getLeader());
        m.setPlayerNick(p.getNickname());
        return m;
    }
    private void setupLeader(String nickname, int id1, int id2)
    {
        Player player= game.getPlayerByNick(nickname);
        player.getLeader().removeIf(lc -> (lc.getID()!=id1 && lc.getID()!=id2));
    }


    /**
     * Initializes the match once the lobby is complete.
     */
    public void start()
    {
        System.out.println("Game started");
        game.sendInitGame();
        System.out.println("Sent init");
        prepare4Leader();
        System.out.println("Leader prepared");
    }
    private void setupResource(String playerNick,ArrayList<Resource> resources)// given playerNickname, add resource to the depot
    {
        if(resources.size()!=0)
        {
            ArrayList<ArrayList<Resource>> depot=game.getPlayerByNick(playerNick).getDepots();
            if(resources.size()==1)//
            {
                depot.get(0).add(resources.get(0));
            }
            else if(resources.size()==2 && resources.get(0)==resources.get(1))// if both resource are the same, add them to the second row
            {
                depot.get(1).add(resources.get(0));
                depot.get(1).add(resources.get(1));
            }
            else// if resource 1 and 2 are different, add one to first row and one to second row.
            {
                depot.get(0).add(resources.get(0));
                depot.get(1).add(resources.get(1));
            }
        }

    }



    public Game getGame()
    {
        return game;
    }


    //Executor getters
    public MarketExecutor getMarketExecutor() {
        return marketExecutor;
    }

    public DiscardLeaderExecutor getDiscardLeaderExecutor() {
        return discardLeaderExecutor;
    }

    public ProductionExecutor getProductionExecutor() {
        return productionExecutor;
    }

    public PurchaseExecutor getPurchaseExecutor() {
        return purchaseExecutor;
    }

    public UseLeaderExecutor getUseLeaderExecutor() {
        return useLeaderExecutor;
    }

    public TurnController getTurnController() {
        return turnController;
    }
}
