package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public class ExtraSlot implements CardSlot{

    ArrayList<Resource> costList;
    ArrayList<Resource> productList;

    public ArrayList<Resource> getCostlist()
    {
        return costList;
    }
    public ArrayList<Resource> getProductlist()
    {
        return productList;
    }

    @Override
    public int getVictoryPoint() {
        return 0;
    }
}
