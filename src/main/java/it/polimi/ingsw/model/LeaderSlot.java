package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LeaderSlot implements DevSlot{
    private final SlotType type;
    private final int numOfAnyInput;
    private final int numOfAnyOutput;
    private ArrayList<Resource> inputResource;
    private ArrayList<Resource> outputResource;

    public LeaderSlot(int id, int numOfAnyOutput, int numOfAnyInput, ArrayList<Resource> inputResource, ArrayList<Resource> outputResource)
    {
        this.numOfAnyInput=numOfAnyInput;
        this.numOfAnyOutput=numOfAnyOutput;
        this.type=SlotType.LEADER;
        this.inputResource=inputResource;
        this.outputResource=outputResource;
    }
    @Override
    public SlotType getType() {
        return type;
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

    public int getNumOfAnyInput() {
        return numOfAnyInput;
    }

    public int getNumOfAnyOutput() {
        return numOfAnyOutput;
    }
}
