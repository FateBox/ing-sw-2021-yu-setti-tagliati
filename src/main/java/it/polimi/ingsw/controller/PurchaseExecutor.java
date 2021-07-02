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
     * informazioni richieste
     * scelta della carta comprata (riga+colonna)
     * dove vuole mettere la carta (nei 3 slot)
     * vuole usare il/i leader per avere lo sconto?
     * come effettuare il pagamennto dal deposito (due hashmap composto di 4 righe, [risorsa, quantità], uno per depot uno per special depot)
     *
     *
     *
     *
     *
     *  operazioni da eseguire
     *  verificare che in quello slot è possibile inserire la carta
     *  verificare che il pagamento sia corretto
     *  {
     *      applica lo sconto
     *      verifica che il pagamento sia corrispondente al costo d'acquisto
     *      verifica che sia possibile prelevare risorse in ogni magazino scelto
     *  }
     *  pescare la carta dal game e inserirla nel devslot
     *
     *
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

    // verify
    // idDevCard: see if it's on a peek.
    // verify isCardInsertable
    // verify payment information.
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

    private ArrayList<Resource> getDiscountedCost(ArrayList<Resource> list, ArrayList<Resource> discount){
        ArrayList<Resource> temp= new ArrayList<>(list);
        for (Resource r: discount)
        {
            list.remove(list.lastIndexOf(r));
        }
        return temp;
    }

}
