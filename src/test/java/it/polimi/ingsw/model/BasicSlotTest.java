package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicSlotTest {


    @Test
    void addInputResource() {
        Resource res=Resource.COIN;
        BasicSlot bs=new BasicSlot();
        bs.addInputResource(0,res);
        assertEquals(bs.getInputResource().get(0),res);
    }

    @Test
    void addOutputResource() {
        Resource res=Resource.COIN;
        BasicSlot bs=new BasicSlot();
        bs.addOutputResource(res);
        assertEquals(bs.getOutputResource().get(0),res);
    }
}