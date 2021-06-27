package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LeaderSlotTest {

    @Test
    void test() {
        LeaderSlot ls=new LeaderSlot(Resource.COIN);
        ArrayList<Resource> temp1=new ArrayList<>();
        ArrayList<Resource> temp2=new ArrayList<>();
        temp1.add(Resource.COIN);
        temp2.add(Resource.FAITH);
        assertEquals(ls.getType(), SlotType.LEADER);
        assertEquals(ls.getInputResource(),temp1);
        assertEquals(ls.getOutputResource(),temp2);
    }

}