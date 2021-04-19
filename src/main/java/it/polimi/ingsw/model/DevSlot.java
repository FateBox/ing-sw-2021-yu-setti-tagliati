package it.polimi.ingsw.model;

import it.polimi.ingsw.model.DevCard;
import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public abstract class DevSlot implements CardSlot{
    ArrayList<DevCard> devcards;

    //Returns top card costlist
    public ArrayList<Resource> getCostlist() {

                 DevCard lastcard = getLastCard();
                 return lastcard.getProductInputList();
    }
    //Returns top card production cost list
    public ArrayList<Resource> getProductlist() {

        DevCard lastcard = getLastCard();
        return lastcard.getProductOutputList();
    }
    public DevCard getLastCard()
    {

        return devcards.get((devcards.size()-1));
    }

    //Try to insert card respecting game VINCOLI otherwise launch exception
    public void insertCard(DevCard card) throws Exception
    {
        if(getLastCard().getLevel() == card.getNextLevel())
        devcards.add(card);
        else
            throw new Exception();
    }

    @Override
    // sums all stacked card victory points
    public int getVictoryPoint() {
        int v=0;
        for (DevCard dev : devcards)
        {
            v += dev.getVictoryPoint();
        }
        return v;
    }
}
