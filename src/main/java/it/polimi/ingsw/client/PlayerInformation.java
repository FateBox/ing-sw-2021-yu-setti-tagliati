package it.polimi.ingsw.client;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.DevSlot;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.SpecialDepot;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerInformation {

    private String nick;
    private ArrayList<ArrayList<Resource>> depot;
    private HashMap<Resource, Integer> strongBox;
    private ArrayList<LeaderCard> leaderCards;
    private ArrayList<Resource> leaderDiscount;
    private ArrayList<SpecialDepot> leaderDepots;
    private ArrayList<Resource> leaderMarket;
    private ArrayList<DevSlot> devSlots;
    private Integer position; //posizione percorso fede
    private boolean[] popeFavor;

    public PlayerInformation(String s)
    {
        nick = s;
        depot = new ArrayList<ArrayList<Resource>>();
        depot.add(new ArrayList<Resource>(1)); //row1
        depot.add(new ArrayList<Resource>(2)); //row2
        depot.add(new ArrayList<Resource>(3)); //row3
        strongBox = new HashMap<>(4);
        leaderCards = new ArrayList<>(4);
        leaderDiscount = new ArrayList<>(4);
        leaderDepots = new ArrayList<>(4);
        leaderMarket = new ArrayList<>(4);
        devSlots = new ArrayList<>(5);
        popeFavor = new boolean[3];
    }

    public ArrayList<ArrayList<Resource>> getDepot() {
        return depot;
    }

    public void setDepot(ArrayList<ArrayList<Resource>> depot) {
        this.depot = depot;
    }

    public String getNick() {
        return nick;
    }

    public HashMap<Resource, Integer> getStrongBox() {
        return strongBox;
    }

    public void setStrongBox(HashMap<Resource, Integer> strongBox) {
        this.strongBox = strongBox;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void setLeaderCards(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public ArrayList<Resource> getLeaderDiscount() {
        return leaderDiscount;
    }

    public void setLeaderDiscount(ArrayList<Resource> leaderDiscount) {
        this.leaderDiscount = leaderDiscount;
    }

    public ArrayList<SpecialDepot> getLeaderDepots() {
        return leaderDepots;
    }

    public void setLeaderDepots(ArrayList<SpecialDepot> leaderDepots) {
        this.leaderDepots = leaderDepots;
    }

    public ArrayList<Resource> getLeaderMarket() {
        return leaderMarket;
    }

    public void setLeaderMarket(ArrayList<Resource> leaderMarket) {
        this.leaderMarket = leaderMarket;
    }

    public ArrayList<DevSlot> getDevSlots() {
        return devSlots;
    }

    public void setDevSlots(ArrayList<DevSlot> devSlots) {
        this.devSlots = devSlots;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public boolean[] getPopeFavor() {
        return popeFavor;
    }

    public void setPopeFavor(boolean[] popeFavor) {
        this.popeFavor = popeFavor;
    }

}
