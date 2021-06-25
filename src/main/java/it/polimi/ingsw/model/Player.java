package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

import java.io.IOException;
import java.util.*;

import static it.polimi.ingsw.enumeration.Resource.*;

public class Player {
    private String nickname;
    private int faithLocation;
    private int victoryPoints;
    private ArrayList<LeaderCard> leaderCards;
    private boolean[] popeFavor;
    /**Resource space**/
    //Array containing number of each resources ordered by: COIN, SERVANT,SHIELD, STONE.
    private HashMap<Resource,Integer> strongBox;
    //A matrix where only lower diagonal half will be used as a triangular matrix.
    private ArrayList<ArrayList<Resource>> depots;
    //Resource stored in hand by player
    //private ArrayList <Resource> inHandResources;
    /**Development space**/
    private ArrayList<DevSlot> devSlots;
    /** Leader properties **/
    // contains base slot and leader slots
    private ArrayList<Resource> developmentDiscounts;
    private ArrayList<Resource> marketDiscounts;
    private ArrayList<SpecialDepot> specialDepots;
    private boolean didAction;
    private boolean leaderPicked;
    private ArrayList<Resource> gain;

    public void forwardFaithLocation(int box)
    {
        this.faithLocation += box;
        if (faithLocation > 24) {
            faithLocation = 24;
        }
    }

    public int getFaithLocation()
    {
        return this.faithLocation;
    }

    public int VP()
    {
        int i;
        int r = 0;

        //Faith track
        if (getFaithLocation() == 24)
            i = 20;
        else if (getFaithLocation()>20)
        {
            i = 16;
        }
        else if (getFaithLocation()>17)
        {
            i = 12;
        }

        else if (getFaithLocation()>14)
        {
            i = 9;
        }

        else if (getFaithLocation()>11)
        {
            i = 6;
        }

        else if (getFaithLocation()>8)
        {
            i = 4;
        }

        else if (getFaithLocation()>5)
        {
            i = 2;
        }

        else if (getFaithLocation()>2)
        {
            i = 1;
        }

        else
        {
            i = 0;
        }

        //Pope favour
        if (popeFavor[0])
        {i+=2;}
        if (popeFavor[1])
        {i+=3;}
        if (popeFavor[2])
        {i+=4;}

        //Resource
        /*
        ArrayList<Integer> listOfResource=new  ArrayList<Integer>(strongBox.values());
        for (Integer num:listOfResource) {
            i+=num.intValue();
        }
        for (int h = 0; h< depots.size(); h++) {
            r += depots.get(h).size();
        }

        i += (r/5);
        */
        //strongBox
        for(Resource resource: strongBox.keySet())
        {
            r+=strongBox.get(resource);
        }
        //depot
        for(ArrayList<Resource> row: depots)
        {
            r+=row.size();
        }
        //special depot
        for (SpecialDepot sp:specialDepots)
        {
            r+=sp.getQuantity();
        }
        i += (r/5);

        //Development cards
        for (DevSlot d : devSlots) {

            if (d.getType()==SlotType.CARD)
            {
              i+=((CardSlot) d).getAllVictoryPoint();
            }
        }

        //Leader cards
        for (LeaderCard lc : leaderCards)
        {
            if (lc.isActive()) {
                i += lc.getVictoryPoint();
            }
        }
        this.victoryPoints = i;
        return victoryPoints;
    }

    /** strongbox **/
    // Requires a storable resource
    // Remove and return resource removed, throw Exception if there's no resource of that type in strongbox
    public void drawStrongBox(Resource r) throws Exception
    {
        if(r==WHITE || r==FAITH)
            throw new Exception("Wrong input");
        if(strongBox.get(r)<=0)
            throw new Exception("Resource not present");
        strongBox.put(r,strongBox.get(r)-1);
    }

    // Requires a storable resource or throw exception
    // Insert selected resource in strongbox
    public void insertStrongBox(Resource r) throws Exception
    {
        if(r==WHITE || r==FAITH)
            throw new Exception();
        else
            strongBox.put(r,strongBox.get(r)+1);
    }

    public int getQuantityStrongBox(Resource r)
    {
        if(r == FAITH || r == WHITE)
            return 0;
        return strongBox.get(r);
    }
    /** leaders **/
    //classic getters and setters
    public void addLeader(LeaderCard l)
    {
        leaderCards.add(l);
    }
    public ArrayList<LeaderCard> getLeader()
    {
        return leaderCards;
    }
    /** pope favor **/
    //
    public void setPopeFavor(int num)
    {
            popeFavor[num] = true;
    }
    public boolean getPopeFavor(int num)
    {
        return popeFavor[num];
    }

    /** depots **/
    //given row, return type of resource inside of this row. Input should be a number between 0 and 2, if no resource is present, returns null
    public Resource getDepotsRowType(int row)
    {
        if(depots.get(row).size()>0)
        {
            return depots.get(row).get(0);
        }
        return null;
    }

    //given resource, return number of this kind of resource inside depot.
    public int getQuantityDepot(Resource r)
    {
        int num=0;
        for(ArrayList<Resource> row:depots)
        {
            for(Resource res: row)
            {
                if (res==r)
                    num++;
            }
        }
        return num;
    }

    public ArrayList<Resource> getDepotRow(int row)
    {
        return new ArrayList<>(depots.get(row));
    }



    /** various getters **/
    public String getNickname() {
        return nickname;
    }
    public ArrayList<ArrayList<Resource>> getDepots() {
        return depots;
    }
    public ArrayList<Resource> getDevelopmentDiscounts() {
        return developmentDiscounts;
    }
    public ArrayList<Resource> getMarketDiscounts() {
        return marketDiscounts;
    }
    public Resource getSpecialDepot(int i) {
        return specialDepots.get(i).getRes();
    }
    public ArrayList<DevSlot> getDevSlots()
    {
        return this.devSlots;
    }
    /** various setters **/
    public void addDevelopmentDiscounts(Resource developmentDiscounts) {
        this.developmentDiscounts.add( developmentDiscounts );
    }
    public void addMarketDiscounts(Resource resource) {
        this.marketDiscounts.add(resource);
    }

    public void addSpecialDepot(Resource r) {
        this.specialDepots.add(new SpecialDepot(r));
    }

    public void setDepots (ArrayList<ArrayList<Resource>> d)
    {
        this.depots = new ArrayList<ArrayList<Resource>> (d);
    }

    public void setSpecialDepots(ArrayList<SpecialDepot> specialDepots)
    {
        this.specialDepots=specialDepots;
    }

    /** creator **/
    public Player(String nickname) {
        this.leaderPicked=false;
        this.didAction=false;
        this.nickname = nickname;
        this.faithLocation = 0;
        this.victoryPoints = 0;
        this.leaderCards = new ArrayList<LeaderCard>();
        popeFavor = new boolean[3];
        strongBox= new HashMap<>();
        strongBox.put(COIN,0);
        strongBox.put(SHIELD,0);
        strongBox.put(STONE,0);
        strongBox.put(SERVANT,0);
        this.depots = new ArrayList<ArrayList<Resource>>();
        for (int i=1; i<4; i++)
        {
            depots.add(new ArrayList<Resource>(i));
        }
        this.devSlots = new ArrayList<>();
        devSlots.add(new BasicSlot());
        devSlots.add(new CardSlot());
        devSlots.add(new CardSlot());
        devSlots.add(new CardSlot());
        this.developmentDiscounts = new ArrayList<>();
        this.marketDiscounts = new ArrayList<>();
        this.specialDepots = new ArrayList<SpecialDepot>();
    }

    //For given parameter, check if player has all the required conditions
    public boolean ownsResources(ArrayList<Resource> resList)
    {
        ArrayList<Resource> InputResource=new ArrayList<>();
        InputResource.add(COIN);
        InputResource.add(STONE);
        InputResource.add(SERVANT);
        InputResource.add(SHIELD);
        int requiredQuantity=0;
        int playerResource=0;
        if (resList.size()==0)
            return true;

        for(Resource r: InputResource)
        {
            requiredQuantity= Collections.frequency(resList,r);

            if(requiredQuantity!=0)
            {
                playerResource=getNumResource(r);
                if(playerResource<requiredQuantity)
                    return false;
            }
        }
        return true;
    }
    public int getNumResource(Resource r)//return amount of specified resource that player has.
    {
        int playerResource=0;
        for (SpecialDepot sd:specialDepots) {
            if(sd.getRes().equals(r))
            {
                playerResource= sd.getQuantity();
            }
        }
        return playerResource+getQuantityDepot(r)+getQuantityStrongBox(r);
    }

    public int getNumResourceDepot(Resource r)
    {
        int playerResource=0;
        for (SpecialDepot sd:specialDepots) {
            if(sd.getRes().equals(r))
            {
                playerResource= sd.getQuantity();
            }
        }
        return playerResource+getQuantityDepot(r);
    }

    public boolean hasDevCard(Color color, int quantity)
    {
        int result=0;
        for(DevSlot devSlot:devSlots)
        {
            if(devSlot.getType()==SlotType.CARD)
            {
                result+=((CardSlot) devSlot).getQuantityDevCard(color);
                result+=((CardSlot) devSlot).getQuantityDevCard(color);
            }
        }
        return result>=quantity;
    }

    public boolean hasDevCard(Level level, Color color, int quantity)
    {
        int result=0;
        for(DevSlot devSlot:devSlots)
        {
            if(devSlot.getType()==SlotType.CARD)
            {
                result+=((CardSlot) devSlot).getQuantityDevCard(color, level);
            }
        }
        return result>=quantity;
    }

    //given id of leaderCard, return true if such leader is present
    public boolean hasLeader(int id)
    {
        for (LeaderCard c:leaderCards) {
            if(c.getID()==id)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isLeaderActive(int id)
    {
        for (LeaderCard c:leaderCards) {
            if(c.getID()==id && c.isActive())
            {
                return true;
            }
        }
        return false;
    }


    public ArrayList<Resource> getGain() {
        return gain;
    }

    public void setGain(ArrayList<Resource> gain) {
        this.gain = gain;
    }

    public LeaderCard getLeaderById(int id)
    {
        for(LeaderCard l: leaderCards)
        {
            if(l.getID()==id)
                return l;
        }
        return null;
    }

    public boolean isLeaderPicked() {
        return leaderPicked;
    }

    public void setLeaderPicked(boolean leaderPicked) {
        this.leaderPicked = leaderPicked;
    }

    public boolean isDidAction() {
        return didAction;
    }

    public void setDidAction(boolean didAction) {
        this.didAction = didAction;
    }
}