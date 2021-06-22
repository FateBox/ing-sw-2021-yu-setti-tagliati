package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.MessageType;
import it.polimi.ingsw.enumeration.PlayerAction;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.SpecialDepot;

import java.util.ArrayList;
import java.util.HashMap;

public class Message {
    private boolean broadCast;
    private String playerNick;
    private MessageType type;
    private PlayerAction playerAction;
    private String text;
    private HashMap<Resource,Integer> payment;
    private int idLeader1;
    private int idLeader2;
    private int rowCol;
    private int devCardId;
    private int slotToInsert;
    private int
    private ArrayList<Resource> resources;
    private ArrayList<ArrayList<Resource>> depot;
    private ArrayList<SpecialDepot> specialDepots;
    //error message
    public Message(String playerNick,String text)
    {
        this.type=MessageType.ERROR;
        this.broadCast=false;
        this.text=text;
    }
    //Broadcast error message
    public Message(String text)
    {
        this.type=MessageType.ERROR;
        this.broadCast=true;
        this.text=text;
    }



    public String getPlayerNick() {
        return playerNick;
    }

    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }


    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public int getRowCol() {
        return rowCol;
    }

    public void setRowCol(int rowcol) {
        this.rowCol = rowcol;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    public ArrayList<ArrayList<Resource>> getDepot() {
        return depot;
    }

    public void setDepot(ArrayList<ArrayList<Resource>> depot) {
        this.depot = depot;
    }

    public ArrayList<SpecialDepot> getSpecialDepots() {
        return specialDepots;
    }

    public void setSpecialDepots(ArrayList<SpecialDepot> specialDepots) {
        this.specialDepots = specialDepots;
    }

    public boolean isBroadCast() {
        return broadCast;
    }

    public void setBroadCast(boolean broadCast) {
        this.broadCast = broadCast;
    }

    public int getIdLeader1() {
        return idLeader1;
    }

    public void setIdLeader1(int idLeader1) {
        this.idLeader1 = idLeader1;
    }

    public int getIdLeader2() {
        return idLeader2;
    }

    public void setIdLeader2(int idLeader2) {
        this.idLeader2 = idLeader2;
    }
}
