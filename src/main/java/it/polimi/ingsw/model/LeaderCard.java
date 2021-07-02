package it.polimi.ingsw.model;
import java.io.Serializable;
import java.util.ArrayList;
import it.polimi.ingsw.enumeration.*;

/**
 * Contains all information of a Leader card
 */
public class LeaderCard implements Serializable {

    private Player owner;
    private final int ID;
    private ArrayList<Resource> resourcesRequirements;
    private ArrayList<Color> devColorRequirements;

    private final AbilityType type;
    private final Resource res;
    private final int victoryPoint;
    private Boolean active;

    /**
     * Constructor of leader card
     * @param id leader id
     * @param victoryPoint victory point
     * @param value Ability type
     * @param abilityResource corresponding resource
     */
    public LeaderCard(int id,int victoryPoint, AbilityType value, Resource abilityResource)
    {
        res = abilityResource;
        active=false;
        this.victoryPoint=victoryPoint;
        ID = id;
        type=value;
        resourcesRequirements = new ArrayList<>();
        devColorRequirements = new ArrayList<>();
    }

    /**
     * Setter for leader card with DISCOUNT ability
     * @param color1 color1
     * @param color2 color2
     */
    public void setDisResLeader(Color color1, Color color2) //usato per Leader sconto e risorse
    {
        this.devColorRequirements.add(color1);
        this.devColorRequirements.add(color2);
    }

    /**
     * Setter for leader card with PRODUCTION ability
     * @param color color
     */
    public void setDevLeader(Color color) //usato per Leader sviluppo
    {
        this.devColorRequirements.add(color);
    }

    /**
     * Setter for leader with DEPOT ability
     * @param resource resource
     * @return
     */
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

    /**
     * Check if this card is playable by given player
     * @param player Player
     * @return a boolean
     */
    public boolean isPlayable(Player player)
    {
        switch (type) {
            case DEPOT -> {

                if (!player.ownsResources(resourcesRequirements)) {
                    return false;
                }
                break;
            }
            case DISCOUNT -> {
                if (!(player.hasDevCard(devColorRequirements.get(0), 1) && player.hasDevCard(devColorRequirements.get(1), 1)))
                    return false;
                break;
            }
            case RESOURCE -> {
                if (!(player.hasDevCard(devColorRequirements.get(0), 2) && player.hasDevCard(devColorRequirements.get(1), 1)))
                    return false;
                break;
            }
            case PRODUCTION -> {
                if (!(player.hasDevCard(Level.LV2, devColorRequirements.get(0), 1)))
                    return false;
                break;
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    /**
     * Return victory point of this card
     * @return victory point
     */
    public int getVictoryPoint() { return this.victoryPoint; }

    /**
     * Return resource of reference for this card
     * @return resource
     */
    public Resource getRes()
    {
        return this.res;
    }

    /**
     * Return Resource requirement for this card
     * @return Arraylist of resource
     */
    public ArrayList<Resource> getResourcesRequirements() {
        return resourcesRequirements;
    }
    /**
     * Return color requirement for this card
     * @return Arraylist of resource
     */
    public ArrayList<Color> getDevColorRequirements() {
        return devColorRequirements;
    }
}
