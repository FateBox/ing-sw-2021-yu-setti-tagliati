package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private ArrayList<SpecialDepot> specialDepot;

    /** strongbox **/
    // Requires a storable resource
    // Remove and return resource removed, throw Exception if there's no resource of that type in strongbox
    public Player drawStrongBox(Resource r) throws Exception
    {
        if(r==WHITE || r==FAITH)
            throw new Exception("Wrong input");
        if(strongBox[r.getValue()]<=0)
            throw new Exception("Resource not present");
        strongBox[r.getValue()]--;
        return this;
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
    public int getQuantityStrongBox(Resource r)
    {
        return strongBox[r.getValue()];
    }
    // ---> IMPLEMENT IT LATER IF USEFULL AND NO CHANGES IN CODE <---
    public ArrayList<Resource> drawStrongBox(ArrayList<Resource> r) throws Exception {return null;}
    // implemented
    public Player insertStrongBox(Resource... res) throws Exception {
        ArrayList<Resource> resources = new ArrayList<Resource>(Arrays.asList(res));
        for(Marble m : Marble.values())
        strongBox[m.getResource().getValue()] += Collections.frequency(resources, m.getResource());
        return this;}
    public int getResourceQuantity(Resource res)
    {
        if(res == FAITH || res == WHITE)
            return 0;
        return strongBox[res.getValue()];
    }
    /** leaders **/
    //classic getters and setters
    public Player addLeader(LeaderCard l)
    {
        leaderCards.add(l);
        return this;
    }
    public boolean removeLeader(LeaderCard l)
    {
        return
                this.leaderCards.remove(l);
    }
    public ArrayList<LeaderCard> getLeader()
    {
        return leaderCards;
    }
    public void useLeader (LeaderCard leaderCard)
    {
            leaderCard.use(this);
    }
    /** pope favor **/
    //
    public void setPopeFavor(Level num)
    {
            popeFavor[num.get()] = true;
    }
    public boolean getPopeFavor(Level num)
    {
        return popeFavor[num.get()];
    }

    /** depots **/
    //
    //
    public Resource getDepotsRowType(int row)
    {
        if(depots.get(row).isEmpty())
            return null;
        else return depots.get(row).get(0);
    }
    private boolean isResViable(Resource resource)
    {
        return !(getDepotsRowType(0)==resource || getDepotsRowType(1)==resource || getDepotsRowType(2)==resource);
    }
    public Player insertDepots(Resource r, int row) throws Exception
    {
        if (r == FAITH || r == Resource.WHITE )
            throw new Exception();
        else
        switch (row) {
            case 1:
                if (depots.get(0).isEmpty() && isResViable(r)) {
                    depots.get(0).add(r);

                } else throw new Exception();
                break;
            case 2:
                if ((depots.get(1).contains(r) && depots.get(1).size() <= 2) || (depots.get(1).isEmpty() && isResViable(r) )) {
                    depots.get(1).add(r);

                } else
                    throw new Exception();
                break;
            case 3:
                if ((depots.get(2).contains(r) && depots.get(2).size() <= 3) || (depots.get(2).isEmpty() && isResViable(r))) {
                    depots.get(2).add(r);

                } else throw new Exception();
                    break;
            default:
                throw new Exception("Unexpected input value");
        }
        return this;
    }
    //
    public Resource removeDepots(int row)
    {
        if(row >0 && row <=3 && !(depots.get(row).isEmpty()))
        return depots.get(row -1).remove(0);
        else return null;
    }
    //
    public void swapDepots(int firstRow, int secondRow) throws Exception
    {
        if(firstRow >3 || firstRow <0 || secondRow >3 || secondRow <0)
            throw new Exception("Unexpected input value");
        else
        {
            if (isDepotSwappable(firstRow, secondRow)){
                ArrayList<Resource> temp = new ArrayList<Resource>( depots.get(firstRow -1));
                depots.get(firstRow-1).clear();
                depots.get(firstRow-1).addAll(depots.get(secondRow-1));
                depots.get(secondRow-1).clear();
                depots.get(secondRow-1).addAll(temp);
        }
            else throw new Exception("Operation violate state invariant properties");
        }
    }
    public boolean isDepotSwappable(int firstrow, int secondrow)
    {
        int greater = Math.max(firstrow, secondrow);
        int lower = Math.min(firstrow, secondrow);

        int size =depots.get(greater-1).size();
        return size <= lower;
    }
    public int getQuantityDepots(Resource resource)
    {
        if(isResViable(resource))
            return 0;
        for(int row = 0; row<3; row++)
        {
            if(getDepotsRowType(row)==resource)
                return depots.get(row).size();
        }
        return 0;
    }
    public ArrayList<Resource> getDepots(int row)
    {
        return new ArrayList<>(depots.get(row));
    }
    /** various getters **/
    public String getNickname() {
        return nickname;
    }
    public DevSlot getDevSlot(int i) throws Exception{
        if(i >=0 && i <=2 )return devSlots.get(i);
        throw new Exception("wrong input");
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
    public SpecialDepot getSpecialdepot(int i) {
        return specialDepot.get(i);
    }


    /** various setters **/
    public void addExtraslots(ExtraSlot extraslot) {
        this.extraslots.add(extraslot) ;
    }
    public void addDevelopmentDiscounts(Resource developmentDiscounts) {
        this.developmentDiscounts.add( developmentDiscounts );
    }
    public void addMarketDiscounts(Resource resource) {
        this.marketDiscounts.add(resource);
    }
    public void addSpecialDepot(Resource r) {
        this.specialDepot.add(new SpecialDepot(r));
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
    devSlots.add(new DevSlot());
    devSlots.add(new DevSlot());
    devSlots.add(new DevSlot());
    this.extraslots = new ArrayList<ExtraSlot>();
    extraslots.add(new ExtraSlot());
    this.developmentDiscounts = new HashSet<>();
    this.marketDiscounts = new HashSet<>();
    this.specialDepot = new ArrayList<SpecialDepot>();
    }


    //
    Boolean ownsResources(ArrayList<Resource> res)
    {
        if(res == null)
            return true;
        for(Marble m: Marble.values())
        {
            int requiredQuantity = Collections.frequency(res, m.getResource());
            if(requiredQuantity != 0)
            {
                requiredQuantity = requiredQuantity - getQuantityStrongBox(m.getResource());
                int temp = getQuantityDepots(m.getResource());
                requiredQuantity = requiredQuantity - temp;
                if(requiredQuantity >0) return false;
            }
        }
        return true;
    }
    //
    Boolean hasDevCard(Level level, Color color, int quantity)
    {
        int result=0;
        for(DevSlot d: devSlots)
        {
            result +=d.getQuantityDevCard(level, color);
        }
        return result>=quantity;
    }
    Boolean hasDevCard(Color color, int quantity)
    {
        int result=0;
        for(DevSlot d: devSlots)
        {
            result +=d.getQuantityDevCard(color);
        }
        return result>=quantity;
    }
 }