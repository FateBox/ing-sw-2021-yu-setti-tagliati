package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.Resource;

public class Player {
    private String nickname;
    private int popeSpPoint;
    //private ArrayList<LeaderCard> leaderCards;
    private boolean[] isLeaderActive;
    private int devCardCount;
    private int[] popeFavor;
    /**Resource space**/
    //Array containing number of each resources ordered by: COIN, SERVANT,SHIELD, STONE.
    private int[] strongBox;
    //A matrix where only lower diagonal half will be used as a triangular matrix.
    private Resource[][] depots;
    //private int[] leaderDepot;
    /**Development space**/
    //
    public void drawStrongBox(Resource r) throws Exception
    {
        switch (r)
        {
            case COIN:
                this.strongBox[0]--;
                break;
            case SERVANT:
                this.strongBox[1]--;
                break;
            case SHIELD:
                this.strongBox[2]--;
                break;
            case STONE:
                this.strongBox[3]--;
                break;
            default:
                throw new Exception();
        }
    }

    public void insertStrongBox(Resource r) throws Exception
    {
        switch (r)
        {
            case COIN:
                this.strongBox[0]++;
                break;
            case SERVANT:
                this.strongBox[1]++;
                break;
            case SHIELD:
                this.strongBox[2]++;
                break;
            case STONE:
                this.strongBox[3]++;
                break;
            default:
                throw new Exception();
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPopeSpPoint() {
        return popeSpPoint;
    }

    public void setPopeSpPoint(int popeSpPoint) {
        this.popeSpPoint = popeSpPoint;
    }

    public boolean[] getIsLeaderActive() {
        return isLeaderActive;
    }

    public void setIsLeaderActive(boolean[] isLeaderActive) {
        this.isLeaderActive = isLeaderActive;
    }

    public int getDevCardCount() {
        return devCardCount;
    }

    public void setDevCardCount(int devCardCount) {
        this.devCardCount = devCardCount;
    }

    public int[] getPopeFavor() {
        return popeFavor;
    }

    public void setPopeFavor(int[] popeFavor) {
        this.popeFavor = popeFavor;
    }

    public int[] getStrongBox() {
        return strongBox;
    }

    public void setStrongBox(int[] strongBox) {
        this.strongBox = strongBox;
    }

    public Resource[][] getDepots() {
        return depots;
    }

    public void setDepots(Resource[][] depots) {
        this.depots = depots;
    }


}
