package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

import java.util.ArrayList;


public abstract class LeaderCard {
    private Player owner;
    private final String ID;
    private final ArrayList<Resource> cost;
    private final AbilityType type;
    private final Resource res;
    private Boolean active;

    LeaderCard(Player owner, String id, ArrayList<Resource> purchasecost, AbilityType value, Resource abilityresource)
    {
        this.owner = owner;
        res = abilityresource;
        active=false;
        cost=purchasecost;
        ID = id;
        type=value;

    }

    public String getID() {
        return ID;
    }
    public Boolean isactive() {return active;}
    public ArrayList<Resource> getCost()
    {
        return new ArrayList<>(cost);
    }
    public AbilityType getType() {
        return type;
    }
    public LeaderCard activate()
    {
        active=true;
        return this;
    }
    public void use (Player player)
    {
        this.active=true;

        switch (type) {
            case DISCOUNT:
                break;
            case PRODUCTION:
                break;
            case DEPOT:
                break;
            case RESOURCE:
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
