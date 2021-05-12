package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Color;
import it.polimi.ingsw.enumeration.Level;
import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public class DevSlot implements CardSlot {
    ArrayList<DevCard> devCards;

    DevSlot() {
        devCards = new ArrayList<>();
    }

    //Returns top card costList
    public ArrayList<Resource> getCostList() {

        if (getLastCard() == null)
            return null;
        DevCard lastcard = getLastCard();
        return lastcard.getProductInputList();
    }

    //Returns top card production cost list
    public ArrayList<Resource> getProductList() {

        if (getLastCard() == null)
            return null;
        DevCard lastcard = getLastCard();
        return lastcard.getProductOutputList();
    }

    public DevCard getLastCard() {
        if (devCards.size() == 0)
            return null;
        return devCards.get((devCards.size() - 1));
    }

    //Try to insert card respecting game VINCOLI otherwise launch exception
    public DevSlot insertCard(DevCard card) throws Exception {
        if (getLastCard() == null && card.getLevel() == Level.LV1) {
            devCards.add(card);
            return this;
        }
        if (getLastCard().getNextLevel() == card.getLevel())
            devCards.add(card);

        else
            throw new Exception();
        return this;
    }

    //returns how many devCards of that type are present in devSlot
    int getQuantityDevCard(Level l, Color c)
    {
        int result = 0;
        for (DevCard d : devCards) {
            if (d.getLevel() == l && d.getColor() == c)
                result++;

        }
        return result;
    }
    //returns how many devCards of that color are present in devSlot
    int getQuantityDevCard(Color c)
    {
        int result = 0;
        for (DevCard d : devCards) {
            if (d.getColor() == c)
                result++;
        }
        return result;

    }
    int getQuantityDevCard()
    {
        return devCards.size();
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
