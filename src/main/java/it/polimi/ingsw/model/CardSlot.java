package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public interface CardSlot {
     ArrayList<Resource> getCostlist();
     ArrayList<Resource> getProductlist();
     int getVictoryPoint();
}
