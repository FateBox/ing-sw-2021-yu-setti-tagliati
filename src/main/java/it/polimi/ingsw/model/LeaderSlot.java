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
        inputResource=new ArrayList<>(1);
        outputResource=new ArrayList<>(2);
        inputResource.add(0, resource);
        outputResource.add(0, Resource.FAITH);
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

    @Override
    public ArrayList<Resource> getOutputResource()
    {
        return  this.outputResource;
    }

    public void addOutputResource(Resource r)
    {
        this.outputResource.add(1,r);
    }
}
