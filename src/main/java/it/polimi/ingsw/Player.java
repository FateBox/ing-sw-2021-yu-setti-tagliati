package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.*;

import java.util.ArrayList;

public class Player {
    private String nickname;
    private ArrayList<LeaderCard> leaderCards;
    private boolean[] popeFavor;
    /**Resource space**/
    //Array containing number of each resources ordered by: COIN, SERVANT,SHIELD, STONE.
    private int[] strongBox;
    //A matrix where only lower diagonal half will be used as a triangular matrix.
    private ArrayList<ArrayList<Resource>> depots;
    /**Development space**/
    private ArrayList<DevSlot> devSlots;
    private ArrayList<ExtraSlot> extraslots;

    /** strongbox **/
    // Requires a storable resource
    // Remove and return resource removed, throw Exception if there's no resource of that type in strongbox
    public Resource drawStrongBox(Resource r) throws Exception
    {
        switch (r)
        {
            case COIN:
                if(this.strongBox[0] > 0)
                    this.strongBox[0]--;
                else
                    throw new Exception();
                break;
            case SERVANT:
                if(this.strongBox[1] > 0)
                    this.strongBox[1]--;
                else
                    throw new Exception();
                break;
            case SHIELD:
                if(this.strongBox[2] > 0)
                    this.strongBox[2]--;
                else
                    throw new Exception();
                break;
            case STONE:
                if(this.strongBox[3] > 0)
                    this.strongBox[3]--;
                else
                    throw new Exception();
                break;
            default:
                throw new Exception();
        }
        return r;
    }
    // Requires a storable resource
    // Insert selected resource in strongbox
    public Player insertStrongBox(Resource r) throws Exception
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
        return this;
    }
    // todo: needs to evaluate resource managment through the app
    public ArrayList<Resource> getAllResources()
    {
        ArrayList<Resource> res;
        for(int i=0; i<4; i++)
        {

        }
        return null;
    }
    //same
    public ArrayList<Resource> getResCount()
    {
        return null;
    }
    /** leaders **/
    //classic getters and setters
    public boolean addLeader(LeaderCard l)
    {
        return
                this.leaderCards.add(l);
    }
    public boolean removeLeader(LeaderCard l)
    {
        return
                this.leaderCards.remove(l);
    }
    public LeaderCard getLeader(int i)
    {
        return leaderCards.get(i);
    }

    /** pope favor **/
    //set pope favor token check
    public void setPopeFavor(int num) throws Exception
    {
        if (num <3) {
            popeFavor[num] = true;
        }
        else throw new Exception();
    }

    /** depots **/
    //
    public void insertdepots(Resource r, int raw) throws Exception
    {
        if (r == Resource.STONE || r == Resource.WHITE)
            throw new Exception();
        else
        switch (raw) {
            case 1:
                if (depots.get(0).isEmpty()) {
                    depots.get(0).add(r);
                    return;
                } else
                break;
            case 2:
                if ((depots.get(1).contains(r) && depots.get(1).size() <= 2) || depots.get(1).isEmpty()) {
                    depots.get(1).add(r);
                    return;
                } else
                break;
            case 3:
                if ((depots.get(2).contains(r) && depots.get(2).size() <= 3) || depots.get(2).isEmpty()) {
                    depots.get(2).add(r);
                    return;
                } else break;
            default:
                throw new IllegalStateException("Unexpected value");
        }


    }
    //
    public Resource removedepots(int raw)
    {
        if(raw>0 && raw <=3)
        return depots.get(raw-1).remove(0);
        else return null;
    }
    //
    public void swapdepots(int firstraw, int secondraw) throws Exception
    {
        if(firstraw>3 || firstraw<0 || secondraw>3 || secondraw<0)
            throw new Exception();
        else
        {
            if (isDepotSwappable(firstraw, secondraw)){
                ArrayList<Resource> temp = depots.get(firstraw-1);
                ArrayList<Resource> t2 = depots.get(firstraw-1);
                t2 = depots.get(secondraw-1);
                t2 = depots.get(secondraw-1) ;
                t2 = temp;
        }
        }
    }
    public boolean isDepotSwappable(int firstraw, int secondraw)
    {
        int greater = Math.max(firstraw, secondraw);
        int lower = Math.min(firstraw, secondraw);

        return depots.get(greater).size() > lower;
    }
    /** rest **/
    public String getNickname() {
        return nickname;
    }


    /** creator **/
    //incomplete
    public Player(String nickname) {
    this.nickname = nickname;
    this.leaderCards = new ArrayList<LeaderCard>(4);
    popeFavor = new boolean[3];
    strongBox= new int[4];
    this.depots = new ArrayList<ArrayList<Resource>>(3);
    for (int i=0; i<4; i++)
    {
        depots.add(new ArrayList<Resource>(3));
    }

    }
}
