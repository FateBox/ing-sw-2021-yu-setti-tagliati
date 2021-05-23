package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Level;
import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;
import java.util.Stack;

public class CardSlot extends DevSlot {
    private Stack<DevCard> devCards;
    public Level showSlotMaxLevel()
    {
       return devCards.peek().getLevel();
    }
    public void insertCard(DevCard card)
    {
        devCards.push(card);
    }


    public int getAllVictoryPoint()//return sum of VP of all cards in this slot
    {
        int sum=0;
        for (DevCard d:devCards) {
            sum+=d.getVictoryPoint();
        }
        return sum;
    }

    public void addDevCard(DevCard d)
    {
        devCards.push(d);
        this.setInputResource(d.getProductInputList());
    }
}
