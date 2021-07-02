package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Basic slot is first slot of player, where player can choose 2 any resource as input and obtain 1 any resource as output
 */
public class BasicSlot implements DevSlot, Serializable {
    private ArrayList<Resource> inputResource;
    private ArrayList<Resource> outputResource;
    private SlotType type;

    public BasicSlot()
    {
        type=SlotType.BASIC;
        inputResource=new ArrayList<>(2);
        outputResource=new ArrayList<>(1);
    }

    @Override
    public ArrayList<Resource> getInputResource() {
        return inputResource;
    }

    @Override
    public ArrayList<Resource> getOutputResource() {
        return outputResource;
    }

    @Override
    public SlotType getType() {
        return type;
    }
}
