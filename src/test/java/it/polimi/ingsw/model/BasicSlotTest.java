package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicSlotTest {

    @Test
    void testType()
    {
        BasicSlot bs=new BasicSlot();
        assertEquals(bs.getType(), SlotType.BASIC);
        assertEquals(bs.getInputResource().size(),0);
        assertEquals(bs.getOutputResource().size(),0);
    }
}