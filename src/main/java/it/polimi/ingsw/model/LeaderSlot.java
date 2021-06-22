package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;

import java.util.ArrayList;

public class LeaderSlot implements DevSlot{
    private final SlotType type;
    private ArrayList<Resource> inputResource;
    private ArrayList<Resource> outputResource;

    public LeaderSlot(Resource resource)
    {
        this.type=SlotType.LEADER;
        inputResource=new ArrayList<>();
        outputResource=new ArrayList<>();
        inputResource.add(resource);
        outputResource.add(Resource.FAITH);
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

}
