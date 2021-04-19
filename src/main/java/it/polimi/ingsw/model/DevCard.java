package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.Level;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;
import java.util.Arrays;

public class DevCard {
    //state
    private int id;
    private Color color;
    private Level level;
    private int victoryPoint;
    private ArrayList<Resource> costList;
    private ArrayList<Resource> productInputList;
    private ArrayList<Resource> productOutputList;


    //costruttore e metodi set usati per inizializzare le carte

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

    public Color getColor() {
        return color;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }

    public ArrayList<Resource> getCostList() {
        return costList;
    }
    //particolare metodo getters che restituisce il prezzo scontato della carta in base all'effetto della carta leader
    public ArrayList<Resource> getReducedCost(Resource r) {
        ArrayList<Resource> reducedCost = new ArrayList<Resource>(this.costList);
        reducedCost.remove(r);
        return reducedCost;
    }

    public ArrayList<Resource> getProductInputList() {
        return productInputList;
    }

    public ArrayList<Resource> getProductOutputList() {
        return productOutputList;
    }

}