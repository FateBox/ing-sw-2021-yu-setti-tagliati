package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public interface CardSlot {
     ArrayList<Resource> getCostList();
     ArrayList<Resource> getProductList();
     int getVictoryPoint();
}
