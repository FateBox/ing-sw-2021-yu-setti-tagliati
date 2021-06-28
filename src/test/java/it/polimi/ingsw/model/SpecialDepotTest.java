package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class SpecialDepotTest {

    @Test
    public void getResTest() {
        Resource res = Resource.COIN;
        SpecialDepot sd = new SpecialDepot(res);
        assertEquals(res, sd.getRes());
    }

    @Test
    public void rowTest() {
        Resource res = Resource.COIN;
        SpecialDepot sd = new SpecialDepot(res);
        assertEquals(0, sd.getQuantity());
        ArrayList<Resource> row1 = new ArrayList<>();
        row1.add(res);
        row1.add(res);
        assertTrue(sd.isCorrect());
        sd.setRow(row1);
        assertEquals(row1, sd.getRow());
        sd.removeResource(0);
        assertEquals(row1.size() - 1, sd.getQuantity());
        row1 = new ArrayList<Resource>(sd.getRow());
        row1.add(res);
        assertEquals(row1.size() - 1, sd.getQuantity());
        ArrayList<Resource> row2 = new ArrayList<>();
        row2.add(Resource.COIN);
        row2.add(Resource.SERVANT);
        sd.setRow(row2);
        assertFalse(sd.isCorrect());
        row2.add(Resource.COIN);
        sd.setRow(row2);
        assertFalse(sd.isCorrect());
    }




}
