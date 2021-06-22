package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Color;
import it.polimi.ingsw.enumeration.Level;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;

import java.util.ArrayList;
import java.util.Stack;

public class CardSlot implements DevSlot {


    private Stack<DevCard> devCards;
    private ArrayList<Resource> inputResource;
    private ArrayList<Resource> outputResource;
    private SlotType type;

    public Level showSlotMaxLevel()
    {
       return devCards.peek().getLevel();
    }

    public CardSlot()
    {
        inputResource=new ArrayList<>();
        outputResource=new ArrayList<>();
        type=SlotType.CARD;
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
        this.setOutputResource(d.getProductOutputList());
    }

    public int getQuantityDevCard(Color color)
    {
        int sum=0;
        for(DevCard devCard: devCards)
        {
            if(devCard.getColor()==color)
                sum++;
        }
        return sum;
    }

    public int getQuantityDevCard(Color color, Level level)//return number of card with specified requirement
    {
        int sum=0;
        for(DevCard devCard: devCards)
        {
            if(devCard.getColor()==color && devCard.getLevel()==level)
                sum++;
        }
        return sum;
    }
    @Override
    public ArrayList<Resource> getInputResource()
    {
        return this.inputResource;
    }

    public void setInputResource(ArrayList<Resource> inputResource)
    {
        this.inputResource=inputResource;
    }
    @Override
    public ArrayList<Resource> getOutputResource()
    {
        return  this.outputResource;
    }

    public void setOutputResource(ArrayList<Resource> outputResource)
    {
        this.outputResource=outputResource;
    }

    @Override
    public SlotType getType() {
        return type;
    }

    public Stack<DevCard> getDevCards() {
        return devCards;
    }
}
