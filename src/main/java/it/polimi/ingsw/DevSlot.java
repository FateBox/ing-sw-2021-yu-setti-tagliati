package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public abstract class DevSlot implements CardSlot{
    ArrayList<DevCard> devcards;


    public ArrayList<Resource> getCostlist() {

                 DevCard lastcard = getLastCard();
                 return lastcard.getPurchaseCostList();
    }
    public ArrayList<Resource> getProductlist() {

        DevCard lastcard = getLastCard();
        return lastcard.getProductionList();
    }
    public DevCard getLastCard()
    {
        return devcards.get((devcards.size()-1));
    }
    //
    public void insertCard(DevCard card) throws Exception
    {
        if(getLastCard().getLevel() == card.getLevel() +1)
        devcards.add(card);
        else
            throw new Exception();
    }

    @Override
    public int getVictoryPoint() {
        return 0;
    }
}
