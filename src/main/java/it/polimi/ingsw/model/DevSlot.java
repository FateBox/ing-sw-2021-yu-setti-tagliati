package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public class DevSlot {
    //superclass of all development slot (1 base slot, 3 card slot and max 2 leader slot)
    private ArrayList<Resource> inputResource;
    private ArrayList<Resource> outputResource;

    public ArrayList<Resource> getInputResource() {
        return inputResource;
    }

    public void setInputResource(ArrayList<Resource> inputResource) {
        this.inputResource = inputResource;
    }

    public ArrayList<Resource> getOutputResource() {
        return outputResource;
    }

    public void setOutputResource(ArrayList<Resource> outputResource) {
        this.outputResource = outputResource;
    }
}
