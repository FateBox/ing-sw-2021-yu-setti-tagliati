package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

import java.util.*;

import static it.polimi.ingsw.enumeration.Resource.*;

public class Player {
    private String nickname;
    private int faithLocation;
    private int victoryPoints;
    private ArrayList<LeaderCard> leaderCards;
    private boolean[] popeFavor;
    private int devCardCount;
    //Resource space
    //Array containing number of each resources ordered by: COIN, SERVANT,SHIELD, STONE.
    private HashMap<Resource,Integer> strongBox;
    //A matrix where only lower diagonal half will be used as a triangular matrix.
    private ArrayList<ArrayList<Resource>> depots;
    //Development space**/
    private ArrayList<DevSlot> devSlots;
    //Leader properties
    // contains base slot and leader slots
    private ArrayList<Resource> developmentDiscounts;
    private ArrayList<Resource> marketDiscounts;
    private ArrayList<SpecialDepot> specialDepots;
    private boolean didAction;
    private boolean leaderPicked;
    private ArrayList<Resource> gain;

    //constructor
    public Player(String nickname) {
        this.leaderPicked=false;
        this.didAction=false;
        this.nickname = nickname;
        this.devCardCount=0;
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


    /** various getters and setters**/
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
    public ArrayList<SpecialDepot> getSpecialDepots()
    {
        return this.specialDepots;
    }
    public ArrayList<DevSlot> getDevSlots()
    {
        return this.devSlots;
    }
    public void addDevelopmentDiscounts(Resource developmentDiscounts) {
        this.developmentDiscounts.add( developmentDiscounts );
    }
    public HashMap<Resource,Integer> getStrongbox()
    {
        return strongBox;
    }
    public void addMarketDiscounts(Resource resource) {
        this.marketDiscounts.add(resource);
    }
    public void forwardFaithLocation(int box)
    {
        this.faithLocation += box;
        if (faithLocation > 24) {
            faithLocation = 24;
        }
    }
    public boolean[] getPopeFavor()
    {
        return popeFavor;
    }
    public void addDevCard()
    {
        this.devCardCount++;
    }
    public int getDevCardCount()
    {
        return devCardCount;
    }
    public int getFaithLocation()
    {
        return this.faithLocation;
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
    //given resource, return number of this kind of resource inside depot.

    public void addLeader(LeaderCard l)
    {
        leaderCards.add(l);
    }
    public ArrayList<LeaderCard> getLeader()
    {
        return leaderCards;
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



    public int vp()
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

    // strongbox
    // Requires a storable resource
    // Remove and return resource removed, throw Exception if there's no resource of that type in strongbox
    public void drawStrongBox(Resource r,int num)
    {
        strongBox.put(r,strongBox.get(r)-num);
    }
    // Requires a storable resource
    // Insert selected resource in strongbox
    public void insertStrongBox(Resource r)
    {
            strongBox.put(r,strongBox.get(r)+1);
    }

    //leaders
    //classic getters and setters

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


    public ArrayList<Resource> getDepotRow(int row)
    {
        return new ArrayList<>(depots.get(row));
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
        return getNumResourceSp(r)+getNumResourceStrongbox(r)+getNumResourceDepot(r);
    }

    public int getNumResourceDepot(Resource r)
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
    public int getNumResourceSp(Resource r)
    {
        int playerResource=0;
        for (SpecialDepot sd:specialDepots) {
            if(sd.getRes().equals(r))
            {
                playerResource= sd.getQuantity();
            }

        }
        return playerResource;
    }
    public int getNumResourceStrongbox(Resource r)
    {
        if(r == FAITH || r == WHITE)
            return 0;
        return strongBox.get(r);
    }


    public boolean hasDevCard(Color color, int quantity)
    {
        int result=0;
        for(DevSlot devSlot:devSlots)
        {
            if(devSlot.getType()==SlotType.CARD)
            {
                result+=((CardSlot) devSlot).getQuantityDevCard(color);
                //result+=((CardSlot) devSlot).getQuantityDevCard(color,level);
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



    public void drawResourceHash(HashMap<Resource,Integer> toBePaid, HashMap<Resource,Integer> paymentDepot, HashMap<Resource,Integer> paymentLeader)
    {
        //draw resource from Depot
        for(Resource r:paymentDepot.keySet())
        {
            for(ArrayList<Resource> row: getDepots())
            {
                if(!row.isEmpty()&&row.get(0).equals(r))
                {
                    for (int i=0;i<paymentDepot.get(r);i++)
                    {
                        row.remove(row.lastIndexOf(r));
                    }
                }

            }
            toBePaid.put(r,toBePaid.get(r)-paymentDepot.get(r));
        }
        //draw resource from Leader Depot
        for (Resource r:paymentLeader.keySet())
        {
            for (SpecialDepot sp: getSpecialDepots())
            {
                if (sp.getRes().equals(r))
                {
                    for(int i=0;i<paymentLeader.get(r);i++)
                    {
                        sp.removeResource(sp.getRow().lastIndexOf(r));
                    }
                }
            }
            toBePaid.put(r,toBePaid.get(r)-paymentLeader.get(r));
        }

        //draw resource from strongbox
        for(Resource r: toBePaid.keySet())
        {
            drawStrongBox(r,toBePaid.get(r));
        }
    }
}