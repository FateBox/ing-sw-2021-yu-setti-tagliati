package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;

import java.util.ArrayList;

public interface DevSlot {
    //interface of all development slot (1 base slot, 3 card slot and max 2 leader slot)

    ArrayList<Resource> getInputResource();
    ArrayList<Resource> getOutputResource();
    SlotType getType();
}
