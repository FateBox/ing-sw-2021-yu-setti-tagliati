package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Color;
import it.polimi.ingsw.enumeration.Level;
import it.polimi.ingsw.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DevCardTest {
    DevCard devCard=new DevCard(0, Level.LV1, Color.GREEN, 2, Resource.STONE, Resource.SHIELD, Resource.SERVANT);
    Resource[] res= new Resource[1];

    @Test
    void getCostList()
    {
        ArrayList<Resource> temp=new ArrayList<>();
        temp.add(Resource.STONE);
        temp.add(Resource.SHIELD);
        temp.add(Resource.SERVANT);
        assertEquals(devCard.getCostList(),temp);
    }
    @Test
    void getId() {
        assertEquals(devCard.getId(),0);
    }

    @Test
    void getLevel() {
        assertEquals(devCard.getLevel(),Level.LV1);
    }

    @Test
    void getNextLevel() {
        assertEquals(devCard.getNextLevel(),Level.LV2);
        DevCard devCard2=new DevCard(0, Level.LV2, Color.GREEN, 2, Resource.STONE, Resource.SHIELD, Resource.SERVANT);
        assertEquals(devCard2.getNextLevel(),Level.LV3);
        DevCard devCard3=new DevCard(0, Level.LV3, Color.GREEN, 2, Resource.STONE, Resource.SHIELD, Resource.SERVANT);
        assertNull(devCard3.getNextLevel());
    }

    @Test
    void getColor() {
        assertEquals(devCard.getColor(),Color.GREEN);
    }

    @Test
    void getVictoryPoint() {
        assertEquals(devCard.getVictoryPoint(),2);
    }

    @Test
    void setProductInputList() {
        res[0]=Resource.COIN;
        devCard.setProductInputList(res);
        ArrayList<Resource> temp=new ArrayList<>();
        temp.add(Resource.COIN);
        assertEquals(devCard.getProductInputList(),temp);
    }

    @Test
    void setProductOutputList() {
        res[0]=Resource.COIN;
        devCard.setProductOutputList(res);
        ArrayList<Resource> temp=new ArrayList<>();
        temp.add(Resource.COIN);
        assertEquals(devCard.getProductOutputList(),temp);
    }

}