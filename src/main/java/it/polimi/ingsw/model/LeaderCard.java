package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

import java.util.ArrayList;


public abstract class LeaderCard {
    private Player owner;
    private final String ID;
    private ArrayList<Resource> resourcesRequirements;
    private ArrayList<Level> devLevelRequirements;
    private ArrayList<Color> devColorRequirements;
    private final AbilityType type;
    private final Resource res;
    private Boolean active;

    LeaderCard(Player owner, String id, AbilityType value, Resource abilityResource)
    {
        this.owner = owner;
        res = abilityResource;
        active=false;
        ID = id;
        type=value;
        resourcesRequirements = new ArrayList<>();
        devColorRequirements = new ArrayList<>();
        devLevelRequirements = new ArrayList<>();

    }

    protected void setDevRequirements(Color color)
    {
        this.devLevelRequirements.add(null);
        this.devColorRequirements.add(color);
    }
    protected void setDevLevelRequirements(Level level, Color color)
    {
        this.devLevelRequirements.add(level);
        this.devColorRequirements.add(color);
    }
    protected void setResourcesRequirements(Resource resource, int quantity)
    {
        for(int i=0; i<quantity-1;i++)
        {
            resourcesRequirements.add(resource);
        }
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
            if(!(player.hasDevCard(devLevelRequirements.get(i),devColorRequirements.get(i) )));
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
