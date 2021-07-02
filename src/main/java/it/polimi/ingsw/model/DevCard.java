package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Color;
import it.polimi.ingsw.enumeration.Level;
import it.polimi.ingsw.enumeration.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains all information of a Development cards
 */
public class DevCard implements Serializable {
    //state
    private final int id;
    private final Color color;
    private final Level level;
    private final int victoryPoint;
    private final ArrayList<Resource> costList;
    private  ArrayList<Resource> productInputList;
    private  ArrayList<Resource> productOutputList;


    //costruttore e metodi set usati per inizializzare le carte

    /**
     * Constructor of Development card
     * @param id id
     * @param l level
     * @param c color
     * @param v victory point
     * @param cost cost of card
     */
    public DevCard(int id, Level l, Color c, int v,Resource... cost)
    {
        this.id = id;
        this.level = l;
        this.color = c;
        this.victoryPoint = v;
        this.costList = new ArrayList<Resource>(Arrays.asList(cost));
    }

    public void setProductInputList(Resource... productInputList) {
        this.productInputList = new ArrayList<Resource>(Arrays.asList(productInputList));
    }

    public void setProductOutputList(Resource... productOutputList) {
        this.productOutputList = new ArrayList<Resource>(Arrays.asList(productOutputList));
    }

    //metodi getters
    public int getId() {
        return id;
    }

    public Level getLevel() {
        return level;
    }

    /**
     * Return next level of this card
     * @return next level of this.level
     */
    public Level getNextLevel() {
        switch (level){
            case LV1:
                return Level.LV2;
            case LV2:
                return Level.LV3;
            default:
                return null;
        }
    }

    public Color getColor() {
        return color;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }

    public ArrayList<Resource> getCostList() {
        return costList;
    }

    public ArrayList<Resource> getProductInputList() {
        return productInputList;
    }

    public ArrayList<Resource> getProductOutputList() {
        return productOutputList;
    }

}