package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Color;
import it.polimi.ingsw.enumeration.Level;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Card slots are slots 1,2 and 3, which player uses it as purchased Development card's storage,
 */
public class CardSlot implements DevSlot, Serializable {


    private Stack<DevCard> devCards;
    private ArrayList<Resource> inputResource;
    private ArrayList<Resource> outputResource;
    private SlotType type;

    public CardSlot()
    {
        devCards=new Stack<>();
        inputResource=new ArrayList<>();
        outputResource=new ArrayList<>();
        type=SlotType.CARD;
    }

    /**
     * Returns sum of victory points of all present Cards
     * @return a integer
     */
    public int getAllVictoryPoint()//return sum of VP of all cards in this slot
    {
        int sum=0;
        for (DevCard d:devCards) {
            sum+=d.getVictoryPoint();
        }
        return sum;
    }

    /**
     * Adds a cart to this slot then change its input and output resource to card's input and output
     * @param d Devcard
     */
    public void addDevCard(DevCard d)
    {
        devCards.push(d);
        this.setInputResource(d.getProductInputList());
        this.setOutputResource(d.getProductOutputList());
    }

    /**
     * Return number of card with specific color
     * @param color required color
     * @return number of cards
     */
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
    /**
     * Return number of card with specific color and level
     * @param color required color
     * @param level required level
     * @return number of cards
     */
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
