package it.polimi.ingsw.controller;
import it.polimi.ingsw.Util;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;
import it.polimi.ingsw.model.CardSlot;
import it.polimi.ingsw.model.DevCard;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PurchaseExecutor implements ActionExecutor{

    /**
     * It handles the operations regarding the purchase.
     * Required information:
     * - Choose the bought card (row+column)
     * - Choose where to put the card (in the 3 slots)
     * - Choose to use the leader card for the discount or not.
     * - Choose how to pay from the depot
     * (2 hashmaps composed by 4 rows, [resources, quantity], one for depot and another for special depot)
     * Operations to do:
     * - Check that it's possible to insert the card in that slot
     * - Check that the payment is correct
     * {apply the discount, verify if the payment is equal to the purchase price,
     * verify if it's possible to take resources in each selected depot}
     * - Pick the card from game and insert it in devSlot.
     */
    private Game game;
    private int deckId;
    private HashMap<Resource,Integer> toBePaid;
    private String price;
    public PurchaseExecutor(Game game)
    {
        this.game=game;
        this.deckId=-1;
        this.toBePaid=new HashMap<>();
        this.price="";
    }


    /**
     * Checks if the card is on a peek, verifies if it's insertable and checks the payment information.
     * @param devCardId ID of the development card.
     * @param slotId ID of the slot.
     * @param paymentDepot Payment depot.
     * @param paymentLeader Payment leader depot.
     * @param discount Possible discount to apply.
     * @return true if the operation can be completed, false otherwise.
     */
    public boolean verifyData(int devCardId, int slotId, HashMap<Resource,Integer> paymentDepot, HashMap<Resource,Integer> paymentLeader, ArrayList<Resource> discount)
    {
        deckId=findDeck(devCardId);
        if (deckId==-1)
            return false;
        toBePaid=createPayment(findDeck(devCardId),discount);

        if (slotId<1||slotId>3)
            return false;
        CardSlot temp = null;
        if(!(game.getCurrentP().getDevSlots().get(slotId).getType().equals(SlotType.CARD)))
            return false;

        //does player has all required resources?
        if(!verifyPayment(toBePaid,paymentDepot,paymentLeader))
        {
            return false;
        }

        //is insertable?
        temp=(CardSlot)game.getCurrentP().getDevSlots().get(slotId);
        if(!(temp.getDevCards().isEmpty()))
        {
            if(!(temp.getDevCards().peek().getNextLevel().equals(game.getDevCard(deckId).getLevel())))
            {
                return false;
            }
        }


        return true;
    }


    /**
     * Executes the purchase operation.
     * @param slotId ID of the slot.
     * @param paymentDepot Payment depot.
     * @param paymentLeader Payment leader depot.
     * @param discount Possible discount to apply.
     */
    public void execute(int slotId, HashMap<Resource,Integer> paymentDepot, HashMap<Resource,Integer> paymentLeader,ArrayList<Resource> discount)
    {
        //give the card to the player and insert it into indicated Card slot.
        CardSlot temp=(CardSlot) game.getCurrentP().getDevSlots().get(slotId);
        temp.addDevCard(game.drawDevCard(deckId));
        game.getCurrentP().addDevCard();
        //draw resource from player's storage
        game.getCurrentP().drawResourceHash(toBePaid,paymentDepot,paymentLeader);

        game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+" obtained a new Development Card.");
        if(discount.size()!=0)
        {
            game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+ " used leader ability.");

        }
        game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+" paid "+ price);
    }

    private int findDeck(int id)
    {
        for(int i=0;i<12;i++)
        {
            if(game.getDevCard(i).getId()==id)
            {
                return i;
            }
        }
        return -1;
    }

    private HashMap<Resource, Integer> createPayment(int deckId,ArrayList<Resource> discount)//given deck id and discount, return a payment table with discounts already applied
    {
        price="";
        HashMap<Resource,Integer> toBePaid = Util.createEmptyPaymentHash();
        DevCard card= game.getDevCard(deckId);
        //create announcement string
        for(Resource r:toBePaid.keySet())
        {
            if(discount.contains(r))
            {
                toBePaid.put(r, Collections.frequency(card.getCostList(),r)-1);
                price=price+" " + r.toString()+" x"+(Collections.frequency(card.getCostList(),r)-1+ "; ");
            }
            else {
                toBePaid.put(r, Collections.frequency(card.getCostList(),r));
                price=price+" " + r.toString()+" x"+(Collections.frequency(card.getCostList(),r) + "; ");
            }

        }
        return toBePaid;
    }

    private boolean verifyPayment(HashMap<Resource,Integer> toBePaid, HashMap<Resource,Integer> paymentDepot, HashMap<Resource,Integer> paymentLeader)
    {
        for (Resource r: toBePaid.keySet())
        {
            if(game.getCurrentP().getNumResource(r)<toBePaid.get(r))//player must have required resources in his storages
                return false;
            if(game.getCurrentP().getNumResourceDepot(r)<paymentDepot.get(r))// player must have what he is trying to pay from depot
                return false;
            if(game.getCurrentP().getNumResourceSp(r)<paymentLeader.get(r))// player must have what he is trying to pay from SpDepot
                return false;
        }
        return true;
    }

}
