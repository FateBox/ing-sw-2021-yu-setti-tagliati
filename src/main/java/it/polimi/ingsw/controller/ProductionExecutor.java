package it.polimi.ingsw.controller;
import it.polimi.ingsw.Util;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.DevSlot;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * It handles the operations regarding the production.
 * Required information:
 * - Choose in which slots the array of integers should be activated
 * - Choose how to pay the required resources
 * { if base slot is selected, it should choose the resources to pay from the depot}
 *
 * Operations to do:
 * - Checks the selections
 * - Perform the actions
 * { removes the paid resources, adds the acquired ones and moves forward the faith track}
 */
public class ProductionExecutor implements ActionExecutor {


    private Game game;
    private ArrayList<DevSlot> devSlots;
    private String price,resourceGain;
    private HashMap<Resource,Integer> payment;
    public ProductionExecutor(Game game) {
        this.game=game;
        devSlots=new ArrayList<>();
        price="";
        resourceGain="";
        payment=new HashMap<>();
    }

    /**
     * Verify if the payment could be correct.
     * @param slotsId ID of the slots.
     * @param paymentDepot Payment depot.
     * @param paymentLeader Payment leader depot.
     * @param extraInput Extra inputs.
     * @return
     */
    public boolean verifyData(ArrayList<Integer> slotsId, HashMap<Resource,Integer> paymentDepot, HashMap<Resource,Integer> paymentLeader, ArrayList<Resource> extraInput)
    {
        System.out.println("veryfing data");
        devSlots=game.getCurrentP().getDevSlots();
        payment=createPayment(slotsId,extraInput);
        //does player has enough resource from his depot and spDepot?
        if(!verifyPayment(paymentDepot,paymentLeader))
        {
            return false;
        }
        return true;
    }

    /**
     * Executes the operation.
     * @param slotsId ID of the slots.
     * @param paymentDepot Payment depot.
     * @param paymentLeader Payment leader depot.
     * @param extraOutput Extra outputs.
     */
    public void execute(ArrayList<Integer> slotsId, HashMap<Resource,Integer> paymentDepot, HashMap<Resource,Integer> paymentLeader,ArrayList<Resource> extraOutput)
    {
        //draw resource from player storage
        game.getCurrentP().drawResourceHash(payment,paymentDepot,paymentLeader);
        //add resource into player's strongbox and in case of faith forward the player.
        ArrayList<Resource> output=createOutput(slotsId,extraOutput);

        int faith=Collections.frequency(output,Resource.FAITH);
        game.getCurrentP().forwardFaithLocation(faith);
        output.removeIf(r -> r.equals(Resource.FAITH));

        for (Resource r: output)
        {
            game.getCurrentP().insertStrongBox(r);
        }

        String temp="";
        for(Integer i:slotsId)
        {
            temp= temp+" "+i;
        }
        game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+ " initiated production on slots:" + temp);
        game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+
                " gained Coin x"+Collections.frequency(output,Resource.COIN)+
                "; Servant x"+Collections.frequency(output,Resource.SERVANT)+
                "; Shield x"+Collections.frequency(output,Resource.SHIELD)+
                "; Stone x"+ Collections.frequency(output,Resource.STONE)+";");
    }

    private HashMap<Resource, Integer> createPayment(ArrayList<Integer> slotID, ArrayList<Resource> extraInput)//create payment hashmap in order to verify whether player has enough resource or not
    {
        price="";
        HashMap<Resource,Integer> toBePaid =Util.createEmptyPaymentHash();
        for(Integer i:slotID)
        {
            if(i!=0)
            {
                for (Resource r: devSlots.get(i).getInputResource())
                {
                    toBePaid.put(r,toBePaid.get(r)+1);
                }
            }
        }
        for (Resource r: extraInput)
        {
            toBePaid.put(r,toBePaid.get(r)+1);
        }
        return toBePaid;
    }

    private ArrayList<Resource> createOutput(ArrayList<Integer> slotID, ArrayList<Resource> extraOutput)
    {
        ArrayList<Resource> output=new ArrayList<>();
        for(Integer i:slotID)
        {
            if(i!=0)
            {
                output.addAll(devSlots.get(i).getOutputResource());
            }
        }
        output.addAll(extraOutput);
        return output;
    }

    private boolean verifyPayment(HashMap<Resource,Integer> paymentDepot, HashMap<Resource,Integer> paymentLeader)
    {
        //payment is price generated server side using slot ids
        //compare it to players payment
        for (Resource r: payment.keySet())
        {
            if(payment.get(r)>game.getCurrentP().getNumResource(r))
                return false;
            if (paymentDepot.get(r)>game.getCurrentP().getNumResourceDepot(r))
                return false;
            if(paymentLeader.get(r)>game.getCurrentP().getNumResourceSp(r))
                return false;
        }
        return true;
    }

}
