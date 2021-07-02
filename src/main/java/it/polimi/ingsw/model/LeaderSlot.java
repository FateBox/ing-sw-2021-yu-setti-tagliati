package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.enumeration.SlotType;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Leader slots are slots 4 and 5, their presence vary based on whether player used a leader card with such ability or not.
 */
public class LeaderSlot implements DevSlot, Serializable {
    private final SlotType type;
    private final ArrayList<Resource> inputResource;
    private final ArrayList<Resource> outputResource;

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
    }}
