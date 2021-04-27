package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public class DevSlot implements CardSlot{
    ArrayList<DevCard> devCards;

    DevSlot()
    {
        devCards = new ArrayList<>();
    }
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

        return devCards.get((devCards.size()-1));
    }

    //Try to insert card respecting game VINCOLI otherwise launch exception
    public void insertCard(DevCard card) throws Exception
    {
        if(getLastCard().getLevel() == card.getNextLevel())
        devCards.add(card);
        else
            throw new Exception();
    }


    @Override
    // sums all stacked card victory points
    public int getVictoryPoint() {
        int v=0;
        for (DevCard dev : devCards)
        {
            v += dev.getVictoryPoint();
        }
        return v;
    }
}
