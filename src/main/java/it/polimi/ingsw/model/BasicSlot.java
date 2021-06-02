package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;

import java.util.ArrayList;

public class BasicSlot implements DevSlot {
    private final int numOfAnyInput;
    private final int numOfAnyOutput;
    private ArrayList<Resource> inputResource;
    private ArrayList<Resource> outputResource;
    private SlotType type;

    public BasicSlot()
    {
        type=SlotType.BASIC;
        numOfAnyInput=2;
        numOfAnyOutput=1;
        inputResource=new ArrayList<>();
        outputResource=new ArrayList<>();
    }

    public int getNumOfAnyInput() {
        return numOfAnyInput;
    }

    public int getNumOfAnyOutput() {
        return numOfAnyOutput;
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
