package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

import java.util.ArrayList;


public class LeaderCard {
    private Player owner;
    private final String ID;
    private ArrayList<Resource> resourcesRequirements;
    private ArrayList<Level> devLevelRequirements;
    private ArrayList<Color> devColorRequirements;
    private ArrayList<Integer> devColLevQuantity;
    private final AbilityType type;
    private final Resource res;
    private final int victoryPoint;
    private Boolean active;

    LeaderCard(String id,int victoryPoint, AbilityType value, Resource abilityResource)
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
    LeaderCard setOwner(Player player)
    {
        owner = player;
        return this;
    }
    protected LeaderCard setDevRequirements(Color color)
    {
        this.devLevelRequirements.add(null);
        this.devColorRequirements.add(color);
        this.devColLevQuantity.add(1);
        return this;
    }
    LeaderCard setLevelRequirements(Color color, int quantity)
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
    protected LeaderCard setResourcesRequirements(Resource resource, int quantity)
    {
        for(int i=0; i<quantity-1;i++)
        {
            resourcesRequirements.add(resource);
        }
        return this;
    }
    public String getID() {
        return ID;
    }
    public Boolean isactive() {return active;}
    public AbilityType getType() {
        return type;
    }

    public boolean isPlayable(Player player)
    {
        boolean result = player.ownsResources(resourcesRequirements);
        for (int i=0; i<devColorRequirements.size();i++)
        {
            if(devLevelRequirements.get(i) == null)
                if(!(player.hasDevCard(devColorRequirements.get(i),devColLevQuantity.get(i))))
                    return false;
                else{}
                else
            if(!(player.hasDevCard(devLevelRequirements.get(i),devColorRequirements.get(i),devColLevQuantity.get(i) )));
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
                ExtraSlot slot = new ExtraSlot(res);
                player.addExtraslots(slot);
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
}
