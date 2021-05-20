package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public class ExtraSlotOld implements DevSlotOld {

    ArrayList<Resource> costList;
    ArrayList<Resource> productList;
    int victoryPoint;

    /** Getters **/
    public ArrayList<Resource> getCostList()
    {
        return costList;
    }
    public ArrayList<Resource> getProductList()
    {
        return productList;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }

    /** Constructor **/
    ExtraSlotOld(Resource resource ){
        costList= new ArrayList<>();
        costList.add(resource);
        productList = new ArrayList<>();
        productList.add(Resource.ANY);
        productList.add(Resource.FAITH);
    }
    ExtraSlotOld()
    {
        costList = new ArrayList<>();
        costList.add(Resource.ANY);
        costList.add(Resource.ANY);
        productList= new ArrayList<>();
        productList.add(Resource.ANY);
    }

}
