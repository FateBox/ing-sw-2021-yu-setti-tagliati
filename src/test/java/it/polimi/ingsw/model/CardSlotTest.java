package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Color;
import it.polimi.ingsw.enumeration.Level;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class CardSlotTest {

    CardSlot cs=new CardSlot();
    DevCard devCard=new DevCard(0, Level.LV1, Color.GREEN, 2,Resource.STONE, Resource.SHIELD, Resource.SERVANT);
    //devCard.setProductInputList(Resource.STONE);
    //devCard.setProductOutputList(Resource.SERVANT);
    @Test
    void getAllVictoryPoint() {
        cs.addDevCard(devCard);
        assertEquals(devCard.getVictoryPoint(),2);
    }

    @Test
    void addDevCard() {
        devCard.setProductInputList(Resource.STONE);
        devCard.setProductOutputList(Resource.SERVANT);
        cs.addDevCard(devCard);
        assertEquals(cs.getInputResource(),devCard.getProductInputList());
        assertEquals(cs.getOutputResource(),devCard.getProductOutputList());
    }


    @Test
    void testGetQuantityDevCard() {
        cs.addDevCard(devCard);
        assertEquals(cs.getQuantityDevCard(Color.GREEN),1);
        assertEquals(cs.getQuantityDevCard(Color.GREEN,Level.LV1),1);
        assertEquals(cs.getQuantityDevCard(Color.YELLOW,Level.LV1),0);
        assertEquals(cs.getQuantityDevCard(Color.YELLOW,Level.LV2),0);
        assertEquals(cs.getQuantityDevCard(Color.GREEN,Level.LV2),0);

    }

    @Test
    void getInputResource() {
    }

    @Test
    void setInputResource() {
        ArrayList<Resource> temp=new ArrayList<>();
        temp.add(Resource.STONE);
        devCard.setProductInputList(Resource.STONE);
        assertEquals(devCard.getProductInputList(),temp);
    }

    @Test
    void getOutputResource() {

    }

    @Test
    void setOutputResource() {
        ArrayList<Resource> temp=new ArrayList<>();
        temp.add(Resource.STONE);
        devCard.setProductInputList(Resource.STONE);
        assertEquals(devCard.getProductInputList(),temp);
    }

    @Test
    void getType() {
        assertEquals(cs.getType(), SlotType.CARD);
    }

    @Test
    void getDevCards() {
        Stack<DevCard> temp=new Stack<>();

        devCard.setProductInputList(Resource.STONE);
        devCard.setProductOutputList(Resource.SERVANT);
        cs.addDevCard(devCard);
        temp.push(devCard);
        assertEquals(devCard.getProductInputList(),cs.getInputResource());
        assertEquals(devCard.getProductOutputList(),cs.getOutputResource());
        assertEquals(cs.getDevCards(),temp);
    }
}