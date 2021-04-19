package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public class ExtraSlot implements CardSlot{

    ArrayList<Resource> costList;
    ArrayList<Resource> productList;
    int victoryPoint;

    /** Getters **/
    public ArrayList<Resource> getCostlist()
    {
        return costList;
    }
    public ArrayList<Resource> getProductlist()
    {
        return productList;
    }
    public int getVictoryPoint() {
        return victoryPoint;
    }

    /** Constructor **/
    ExtraSlot(ArrayList<Resource> costList, ArrayList<Resource> productList, int victoryPoint ){
        this.costList=costList;
        this.victoryPoint=victoryPoint;
        this.productList=productList;
    }

}
