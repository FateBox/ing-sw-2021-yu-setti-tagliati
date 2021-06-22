package it.polimi.ingsw.controller;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;
import it.polimi.ingsw.model.CardSlot;
import it.polimi.ingsw.model.DevCard;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

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
    public PurchaseExecutor(Game game)
    {
        this.game=game;
        this.deckId=-1;
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
        CardSlot temp;
        if(!(game.getCurrentP().getDevSlots().get(slotId).getType().equals(SlotType.CARD)))
        {
            return false;
        }
        //is insertable?
        temp=(CardSlot)game.getCurrentP().getDevSlots().get(slotId);
        if(!(temp.getDevCards().peek().getNextLevel().equals(game.getDevCard(deckId).getLevel())))
        {
            return false;
        }
        ///////
        {
            if(!payment.get(r).equals(game.getCurrentP().getQuantityDepot(r)))
                return false;
        }
        for(Resource r: payment.keySet())
        {
            if(!payment.get(r).equals(game.getCurrentP().getQuantityDepot(r)))
            return false;
        }
        return true;
    }

    public void execute(int devCardId, int slotId, HashMap<Resource,Integer> payment)
    {
        HashMap<Resource,Integer> toBePaid;
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
}
