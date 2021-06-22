package it.polimi.ingsw.model;
import java.util.ArrayList;
import it.polimi.ingsw.enumeration.*;

public class LeaderCard {

    private Player owner;
    private final int ID;
    private ArrayList<Resource> resourcesRequirements;
    private ArrayList<Color> devColorRequirements;



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
    }

    public LeaderCard setOwner(Player player)
    {
        owner = player;
        return this;
    }

    public void setDisResLeader(Color color1, Color color2) //usato per Leader sconto e risorse
    {
        this.devColorRequirements.add(color1);
        this.devColorRequirements.add(color2);
    }

    public void setDevLeader(Color color) //usato per Leader sviluppo
    {
        this.devColorRequirements.add(color);
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
    public void setActive(boolean active)
    {
        this.active=active;
    }

    public boolean isPlayable(Player player)
    {
        switch (type)
        {
            case DEPOT:
            {
                if(!player.ownsResources(resourcesRequirements)){
                    return false;
                }
                break;
            }
            case DISCOUNT:
            {
                if(!(player.hasDevCard(devColorRequirements.get(0),1) && player.hasDevCard(devColorRequirements.get(1),1)))
                    return false;
                break;
            }
            case RESOURCE:
            {
                if(!(player.hasDevCard(devColorRequirements.get(0),2) && player.hasDevCard(devColorRequirements.get(1),1)))
                return false;
                break;
            }
            case PRODUCTION:
            {
                if(!(player.hasDevCard(Level.LV2,devColorRequirements.get(0),1)))
                    return false;
                break;
            }
            default:
            {
                return false;
            }
        }
        return true;
    }



    public int getVictoryPoint() { return this.victoryPoint; }

    public Resource getRes()
    {
        return this.res;
    }

    public ArrayList<Resource> getResourcesRequirements() {
        return resourcesRequirements;
    }

    public void setResourcesRequirements(ArrayList<Resource> resourcesRequirements) {
        this.resourcesRequirements = resourcesRequirements;
    }


    public ArrayList<Color> getDevColorRequirements() {
        return devColorRequirements;
    }

    public void setDevColorRequirements(ArrayList<Color> devColorRequirements) {
        this.devColorRequirements = devColorRequirements;
    }

}
