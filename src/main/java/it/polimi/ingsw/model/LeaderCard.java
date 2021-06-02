package it.polimi.ingsw.model;



import java.util.ArrayList;


import it.polimi.ingsw.enumeration.*;



public class LeaderCard {

    private Player owner;
    private final int ID;
    private ArrayList<Resource> resourcesRequirements;
    private ArrayList<Level> devLevelRequirements;
    private ArrayList<Color> devColorRequirements;
    private ArrayList<Integer> devColLevQuantity;
    private final AbilityType type;
    private final Resource res;
    private final int victoryPoint;
    private Boolean active;

    LeaderCard(int id,int victoryPoint, AbilityType value, Resource abilityResource)
    {
        res = abilityResource;
        active=false;
        this.victoryPoint=victoryPoint;
        ID = id;
        type=value;
        resourcesRequirements = new ArrayList<>();
        devColorRequirements = new ArrayList<>();
        devLevelRequirements = new ArrayList<>();
        devColLevQuantity = new ArrayList<>();
    }

    public LeaderCard setOwner(Player player)
    {
        owner = player;
        return this;
    }

    public LeaderCard setDisResLeader(Color color1, Color color2) //usato per Leader sconto e risorse
    {
        this.devLevelRequirements.add(null);

        this.devColorRequirements.add(color1);

        if (type.equals(AbilityType.DISCOUNT))
            this.devColLevQuantity.add(1);

        else if (type.equals(AbilityType.RESOURCE))
            this.devColLevQuantity.add(2);

        this.devColorRequirements.add(color2);
        this.devColLevQuantity.add(1);
        return this;
    }

    public LeaderCard setDevLeader(Color color) //usato per Leader sviluppo
    {
        this.devLevelRequirements.add(Level.LV2);
        this.devColorRequirements.add(color);
        this.devColLevQuantity.add(1);
        return this;
    }

    public LeaderCard setDepLeader(Resource resource) //usato per leader deposito
    {
        for(int i=0; i<5; i++)
        {
            resourcesRequirements.add(resource);
        }
        return this;
    }

    public int getID() {
        return ID;
    }
    public Boolean isActive() {return active;}
    public AbilityType getType() {
        return type;
    }
    /*
    protected LeaderCard setDevRequirements(Color color)
    {
        this.devLevelRequirements.add(null);
        this.devColorRequirements.add(color);
        this.devColLevQuantity.add(1);
        return this;
    }
    protected LeaderCard setDevRequirements(Color color, int quantity)
    {
        this.devLevelRequirements.add(null);
        this.devColorRequirements.add(color);
        this.devColLevQuantity.add(quantity);
        return this;
    }
    protected LeaderCard setDevLevelRequirements(Color color, Level level)
    {
        this.devLevelRequirements.add(level);
        this.devColorRequirements.add(color);
        this.devColLevQuantity.add(1);
        return this;
    }
    protected LeaderCard setDevLevelRequirements(Level level, Color color, int quantity)
    {
        this.devLevelRequirements.add(level);
        this.devColorRequirements.add(color);
        this.devColLevQuantity.add(quantity);
        return this;
    }
    protected LeaderCard setResourcesRequirements(Resource resource, int quantity) {
        for (int i = 0; i < quantity; i++) {
            resourcesRequirements.add(resource);
        }
        return this;
    }*/

    public boolean isPlayable()
    {
        return isPlayable(owner);
    }
    public boolean isPlayable(Player player)
    {
        boolean result = player.ownsResources(resourcesRequirements);
        for (int i=0; i<devColorRequirements.size();i++)
        {
            if(devLevelRequirements.get(i) == null) {
                if (!(player.hasDevCard(devColorRequirements.get(i), devColLevQuantity.get(i))))
                    return false;
            }
            else
            if(!(player.hasDevCard(devLevelRequirements.get(i),devColorRequirements.get(i),devColLevQuantity.get(i) )))
                result = false;
        }
        return result;
    }

    public boolean use (Player player)
    {
        if(active || !isPlayable(player))
        {
            return false;
        }

        switch (type) {
            case DISCOUNT:
            {
                player.addDevelopmentDiscounts(res);
            }
            break;
            case PRODUCTION:
            {
                //
            }
            break;
            case DEPOT:
            {
                player.addSpecialDepot(res);
            }
            break;
            case RESOURCE:
            {
                player.addMarketDiscounts(res);
            }
            break;

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        this.active=true;
        return true;
    }

    public int getVictoryPoint() { return this.victoryPoint; }
}
