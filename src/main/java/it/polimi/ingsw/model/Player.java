package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

import java.util.ArrayList;
import java.util.HashSet;

import static it.polimi.ingsw.enumeration.Resource.*;

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
    /** Leader properties **/
    // contains base slot and leader slots
    private ArrayList<ExtraSlot> extraslots;
    private HashSet<Resource> developmentDiscounts;
    private HashSet<Resource> marketDiscounts;
    //not sure if good, needs suggestion
    //-> at start it's a new arr(4) with arr.get(0/1/2/3) = null; when leader is played arr.get(i) gets 0;
    private ArrayList<Integer> specialdepot;


    /** strongbox **/
    // Requires a storable resource
    // Remove and return resource removed, throw Exception if there's no resource of that type in strongbox
    public Resource drawStrongBox(Resource r) throws Exception
    {
        if(r==WHITE || r==FAITH || strongBox[r.getValue()]<=0)
            throw new Exception();
        strongBox[r.getValue()]--;
        return r;
    }
    // Requires a storable resource or throw exception
    // Insert selected resource in strongbox
    public Player insertStrongBox(Resource r) throws Exception
    {
        if(r==WHITE || r==FAITH)
            throw new Exception();
        else
         this.strongBox[r.getValue()]++;

        return this;
    }
    // ---> IMPLEMENT IT LATER IF USEFULL AND NO CHANGES IN CODE <---
    public ArrayList<Resource> drawStrongBox(ArrayList<Resource> r) throws Exception {return null;}
    public ArrayList<Resource> insertStrongBox(ArrayList<Resource> r) throws Exception {return null;}
    public int getResourceQuantity(Resource res)
    {
        if(res == FAITH || res == WHITE)
            return 0;
        return strongBox[res.getValue()];
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
    public void useLeader (LeaderCard leaderCard)
    {
        leaderCard.use(this);
    }
    /** pope favor **/
    //Requires an integer from 1 to 3, set corresponding pope favor check to true
    public void setPopeFavor(int num) throws Exception
    {
        if (num <3 && num >0) {
            popeFavor[num] = true;
        }
        else throw new Exception();
    }

    /** depots **/
    //
    public void insertDepots(Resource r, int row) throws Exception
    {
        if (r == Resource.STONE || r == Resource.WHITE)
            throw new Exception();
        else
        switch (row) {
            case 1:
                if (depots.get(0).isEmpty()) {
                    depots.get(0).add(r);

                } else throw new Exception();
                break;
            case 2:
                if ((depots.get(1).contains(r) && depots.get(1).size() <= 2) || depots.get(1).isEmpty()) {
                    depots.get(1).add(r);

                } else
                    throw new Exception();
                break;
            case 3:
                if ((depots.get(2).contains(r) && depots.get(2).size() <= 3) || depots.get(2).isEmpty()) {
                    depots.get(2).add(r);

                } else throw new Exception();
                    break;
            default:
                throw new IllegalStateException("Unexpected value");
        }


    }
    //
    public Resource removeDepots(int row)
    {
        if(row >0 && row <=3)
        return depots.get(row -1).remove(0);
        else return null;
    }
    //
    public void swapDepots(int firstraw, int secondraw) throws Exception
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
    /** various getters **/
    public String getNickname() {
        return nickname;
    }
    public ArrayList<DevSlot> getDevSlots() {
        return devSlots;
    }
    public ArrayList<ExtraSlot> getExtraslots() {
        return extraslots;
    }
    public HashSet<Resource> getDevelopmentDiscounts() {
        return developmentDiscounts;
    }
    public HashSet<Resource> getMarketDiscounts() {
        return marketDiscounts;
    }
    public ArrayList<Integer> getSpecialdepot() {
        return specialdepot;
    }



    /** creator **/
    public Player(String nickname) {
    this.nickname = nickname;
    this.leaderCards = new ArrayList<LeaderCard>(4);
    popeFavor = new boolean[3];
    strongBox= new int[4];
    this.depots = new ArrayList<ArrayList<Resource>>(3);
    for (int i=1; i<4; i++)
    {
        depots.add(new ArrayList<Resource>(i));
    }
    this.devSlots = new ArrayList<>(3);
    this.extraslots = new ArrayList<ExtraSlot>();
    extraslots.add(new ExtraSlot());
    this.developmentDiscounts = new HashSet<>();
    this.marketDiscounts = new HashSet<>();
    this.specialdepot = new ArrayList<>(2);

    }


}
