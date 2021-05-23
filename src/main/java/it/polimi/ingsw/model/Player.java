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
    private ArrayList<ExtraSlotOld> extraslots;
    private HashSet<Resource> developmentDiscounts;
    private ArrayList<Resource> marketDiscounts;
    private ArrayList<Resource> specialDepot;

    public void setFaithLocation(int box)
    {
        this.faithLocation += box;
    }

    public int getFaithLocation()
    {
        return this.faithLocation;
    }

    public int VP ()
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
        ArrayList<Integer> listOfResource=new  ArrayList<Integer>(strongBox.values());
        for (Integer num:listOfResource) {
            i+=num.intValue();
        }
        for (int h = 0; h< depots.size(); h++) {
            r += depots.get(h).size();
        }

        i += (r/5);

        //Development cards
        for (DevSlot d : devSlots) {

            if (d instanceof CardSlot)
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
    public void setPopeFavor(int num)
    {
            popeFavor[num] = true;
    }
    public boolean getPopeFavor(int num)
    {
        return popeFavor[num];
    }

    /** depots **/
    //returns null if special depot required doesn't exists, row is empty or row is wrong
    //nobody should call it with wrong row, it should be ok, it could be private, but may be usefull in controller
    //IF USED IN CONTROLLER DEPENDING ON CLIENT INPUT IT'S WORTH MAKE IT LAUNCH EXCEPTIONS
    public Resource getDepotsRowType(int row)
    {


        if(row >= 3 && row <5)
            return specialDepot.get(row-3);
        if(row >=5 || row<0 || depots.get(row).isEmpty())
            return null;
        else return depots.get(row).get(0);
    }
    //
    //
    private boolean isResViable(Resource resource)
    {
            if(resource == FAITH || resource == WHITE)
                return false;
            return !(getDepotsRowType(0) == resource || getDepotsRowType(1) == resource || getDepotsRowType(2) == resource);
    }
    //
    //if insert not viable returns an Exception
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
            case 4:
                if(depots.get(3).size()<2 && r == specialDepot.get(0))
                    depots.get(3).add(r);
                else throw new Exception();
                break;
            case 5:
                if(depots.get(4).size()<2 && r == specialDepot.get(1))
                    depots.get(4).add(r);
                else throw new Exception();
                break;
            default:
                throw new Exception("Unexpected input value");
        }
        return this;
    }
    //
    //returns null if it didn't work
    public Resource removeDepots(int row)
    {
        if(row >0 && row <=5 && !(depots.get(row-1).isEmpty()))
        return depots.get(row -1).remove(0);
        else return null;
    }
    //
    public void swapDepotsRow(int firstRow, int secondRow) throws Exception
    {
        if(firstRow >5 || firstRow <=0 || secondRow >5 || secondRow <=0)
            throw new Exception("Unexpected input value");
        else
        {
            if (isDepotRowSwappable(firstRow, secondRow)){
                ArrayList<Resource> temp = new ArrayList<Resource>( depots.get(firstRow -1));
                depots.get(firstRow-1).clear();
                depots.get(firstRow-1).addAll(depots.get(secondRow-1));
                depots.get(secondRow-1).clear();
                depots.get(secondRow-1).addAll(temp);
        }
            else throw new Exception("Operation violate state invariant properties");
        }
    }
    //
    public boolean isDepotRowSwappable(int firstrow, int secondrow) throws Exception
    {
        int greater = Math.max(firstrow, secondrow);
        int lower = Math.min(firstrow, secondrow);
        if(greater > 5)
            return false;
        if(greater>3)
        {
            if(greater == lower-1 || specialDepot.get(greater-4)== null)
                throw new Exception();
            ArrayList<Resource> greaterRow = depots.get(greater - 1);
            ArrayList<Resource> lowerRow = depots.get(lower -1);
            int lowerMaxSize = lower;
            Resource leaRowType = specialDepot.get(greater-4);
            Resource depRowType = getDepotsRowType(lower-1);

            if(lower > 3)
                return false;
            // checks resource type compatibility
            if(depRowType == null && !(isResViable(leaRowType) ))
                return false;
            if(depRowType != null && (leaRowType != depRowType || !(isResViable(leaRowType))))
                return false;
            if(lowerMaxSize < greaterRow.size() || lowerRow.size() > 2)
                return false;

        }
        else {
            int size;
            size = depots.get(greater - 1).size();
            return size <= lower;
        }
        return false;

    }
    public int getQuantityDepots(Resource resource)
    {
        int temp=0;
        int i=0;
        for(ArrayList<Resource> row : depots)
        {
            if(getDepotsRowType(i)==resource)
                temp += depots.get(i).size();
            i++;
        }
        return temp;
    }
    public ArrayList<Resource> getDepots(int row)
    {
        return new ArrayList<>(depots.get(row));
    }


    /** various getters **/
    public String getNickname() {
        return nickname;
    }
    /**public DevSlot_Old_old getDevSlot(int i) throws Exception{
        if(i >=0 && i <=2 )return devSlotOlds.get(i);
        throw new Exception("wrong input");
    }**/
    public ArrayList<ExtraSlotOld> getExtraslots() {
        return extraslots;
    }
    public HashSet<Resource> getDevelopmentDiscounts() {
        return developmentDiscounts;
    }
    public ArrayList<Resource> getMarketDiscounts() {
        return marketDiscounts;
    }
    public Resource getSpecialdepot(int i) {
        return specialDepot.get(i);
    }

    /** various setters **/
    public void addExtraslots(ExtraSlotOld extraslot) {
        this.extraslots.add(extraslot) ;
    }
    public void addDevelopmentDiscounts(Resource developmentDiscounts) {
        this.developmentDiscounts.add( developmentDiscounts );
    }
    public void addMarketDiscounts(Resource resource) {
        this.marketDiscounts.add(resource);
    }
    public void addSpecialDepot(Resource r) {
        this.specialDepot.add(r);
        this.depots.add(new ArrayList<Resource> (2));
    }

    public void setDepots (ArrayList<ArrayList<Resource>> d)
    {
        this.depots = new ArrayList<ArrayList<Resource>> (d);
    }


    /** creator **/
    public Player(String nickname) {
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
        this.extraslots = new ArrayList<ExtraSlotOld>();
        extraslots.add(new ExtraSlotOld());
        this.developmentDiscounts = new HashSet<>();
        this.marketDiscounts = new ArrayList<>();
        this.specialDepot = new ArrayList<Resource>();
    }


    /**
    public Boolean ownsResources(ArrayList<Resource> res)
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

    public Boolean hasDevCard(Level level, Color color, int quantity)
    {
        int result=0;
        for(DevSlot_Old_old d: devSlotOlds)
        {
            result +=d.getQuantityDevCard(level, color);
        }
        return result>=quantity;
    }
    public Boolean hasDevCard(Color color, int quantity)
    {
        int result=0;
        for(DevSlot_Old_old d: devSlotOlds)
        {
            result +=d.getQuantityDevCard(color);
        }
        return result>=quantity;
    }
     **/
 }