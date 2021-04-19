package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public interface CardSlot {
    public ArrayList<Resource> getCostlist();
    public ArrayList<Resource> getProductlist();
    public int getVictoryPoint();
}
