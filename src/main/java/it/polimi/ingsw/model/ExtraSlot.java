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
    ExtraSlot(Resource resource ){
        costList= new ArrayList<>();
        costList.add(resource);
        productList = new ArrayList<>();
        productList.add(Resource.ANY);
        productList.add(Resource.FAITH);
    }
    ExtraSlot()
    {
        costList = new ArrayList<>();
        costList.add(Resource.ANY);
        costList.add(Resource.ANY);
        productList= new ArrayList<>();
        productList.add(Resource.ANY);
    }

}
