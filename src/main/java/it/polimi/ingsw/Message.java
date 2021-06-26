package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.MessageType;
import it.polimi.ingsw.enumeration.PlayerAction;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.DevCard;
import it.polimi.ingsw.model.DevSlot;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.SpecialDepot;

import java.util.ArrayList;
import java.util.HashMap;

public class Message {
    private boolean broadCast;
    private String playerNick;
    private MessageType type;
    private PlayerAction playerAction;
    private String text;
    private HashMap<Resource,Integer> paymentDepot;
    private HashMap<Resource,Integer> paymentLeader;
    private int idLeader1;
    private int idLeader2;
    private int rowCol;
    private int slotToInsert;
    private ArrayList<Resource> resources;

    //update section
    //init
    private ArrayList<String> playerNickList;
    private ArrayList<DevCard> devDeck;
    private ArrayList<LeaderCard> leaderDeck;
    //faith track;
    private ArrayList<Integer> faithTrack;
    //market
    private ArrayList<ArrayList<Resource>> depot;
    private ArrayList<SpecialDepot> specialDepots;
    private Resource[][] market;
    private Resource freeMarble;
    //purchase
    private ArrayList<DevSlot> devSlots;
    private int devCardId;
    //production
    private ArrayList<Integer> productionSlots;
    private ArrayList<Resource> extraInput;
    private ArrayList<Resource> extraOutput;
    //



    //turn indication
    private String currentPlayer;

    //error message


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

    public int getDevCardId() {
        return devCardId;
    }

    public void setDevCardId(int devCardId) {
        this.devCardId = devCardId;
    }

    public ArrayList<DevSlot> getDevSlots() {
        return devSlots;
    }

    public void setDevSlots(ArrayList<DevSlot> devSlots) {
        this.devSlots = devSlots;
    }

    public Resource getFreeMarble() {
        return freeMarble;
    }

    public void setFreeMarble(Resource freeMarble) {
        this.freeMarble = freeMarble;
    }

    public Resource[][] getMarket() {
        return market;
    }

    public void setMarket(Resource[][] market) {
        this.market = market;
    }

    public ArrayList<Integer> getFaithTrack() {
        return faithTrack;
    }

    public void setFaithTrack(ArrayList<Integer> faithTrack) {
        this.faithTrack = faithTrack;
    }

    public ArrayList<LeaderCard> getLeaderDeck() {
        return leaderDeck;
    }

    public void setLeaderDeck(ArrayList<LeaderCard> leaderDeck) {
        this.leaderDeck = leaderDeck;
    }

    public ArrayList<DevCard> getDevDeck() {
        return devDeck;
    }

    public void setDevDeck(ArrayList<DevCard> devDeck) {
        this.devDeck = devDeck;
    }

    public ArrayList<String> getPlayerNickList() {
        return playerNickList;
    }

    public void setPlayerNickList(ArrayList<String> playerNickList) {
        this.playerNickList = playerNickList;
    }


    public int getSlotToInsert() {
        return slotToInsert;
    }

    public void setSlotToInsert(int slotToInsert) {
        this.slotToInsert = slotToInsert;
    }

    public HashMap<Resource, Integer> getPaymentDepot() {
        return paymentDepot;
    }

    public void setPaymentDepot(HashMap<Resource, Integer> paymentDepot) {
        this.paymentDepot = paymentDepot;
    }

    public HashMap<Resource, Integer> getPaymentLeader() {
        return paymentLeader;
    }

    public void setPaymentLeader(HashMap<Resource, Integer> paymentLeader) {
        this.paymentLeader = paymentLeader;
    }

    public ArrayList<Resource> getExtraInput() {
        return extraInput;
    }

    public void setExtraInput(ArrayList<Resource> extraInput) {
        this.extraInput = extraInput;
    }

    public ArrayList<Resource> getExtraOutput() {
        return extraOutput;
    }

    public void setExtraOutput(ArrayList<Resource> extraOutput) {
        this.extraOutput = extraOutput;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<Integer> getProductionSlots() {
        return productionSlots;
    }

    public void setProductionSlots(ArrayList<Integer> productionSlots) {
        this.productionSlots = productionSlots;
    }
}
