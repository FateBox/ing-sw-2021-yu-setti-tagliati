package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.*;
import java.util.ArrayList;

public class DevCard {
    private final String ID;
    private final int victorypoint;
    private final ArrayList<Resource> purchasecostlist;
    private final Color color;
    private final int level;
    private final ArrayList<Resource> productioncostlist;
    private final ArrayList<Resource> productionlist;

    DevCard(String id, int vp, ArrayList<Resource> pucl, Color c, int l, ArrayList<Resource> prcl, ArrayList<Resource> pl)
    {
        ID=id;
        victorypoint=vp;
        purchasecostlist=pucl;
        color=c;
        level=l;
        productionlist=pl;
        productioncostlist=prcl;
    }

    public String getID() {
        return ID;
    }

    public int getVictoryPoint() {
        return victorypoint;
    }

    public ArrayList<Resource> getPurchaseCostList() {
        return purchasecostlist;
    }

    public Color getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Resource> getProductionCostList() {
        return new ArrayList<>(productioncostlist);
    }

    public ArrayList<Resource> getProductionList() {
        return new ArrayList<>(productionlist);
    }
}
