package it.polimi.ingsw.model;

import java.util.Arrays;

public class Player {
    private int id;
    private int popeSpPoint;
    private int[] leaderCard;
    private boolean[] isLeaderActive;
    private int devCardCount;
    public Player(int id) {
        this.id=id;
        popeSpPoint=0;
        leaderCard= new int[]{-1,-1};
        isLeaderActive= new boolean[]{false,false};
        devCardCount=0;
    }

    public void useLeaderCard(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopeSpPoint() {
        return popeSpPoint;
    }

    public void setPopeSpPoint(int popeSpPoint) {
        this.popeSpPoint = popeSpPoint;
    }

    public int[] getLeaderCard() {
        return leaderCard;
    }

    public void setLeaderCard(int[] leaderCard) {
        this.leaderCard = leaderCard;
    }

    public boolean getIsLeaderActive(int i) {
        return isLeaderActive[i];
    }

    public void setIsLeaderActive(int i) {
        this.isLeaderActive[i] =true;
    }

    public int getDevCardCount() {
        return devCardCount;
    }

    public void setDevCardCount(int devCardCount) {
        this.devCardCount = devCardCount;
    }


}
