package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

import java.util.Optional;

public class LorenzoCard {
    private final LorenzoType abilitytype;
    private  Optional<Color> color;

    public LorenzoCard(LorenzoType abilitytype, Color color) {
        this.abilitytype = abilitytype;
        this.color = Optional.ofNullable(color);
    }

    public LorenzoType getAbilityType() {
        return abilitytype;
    }

    public Optional<Color> getColor() {
        return color;
    }
    public void use()
    {

        switch(abilitytype) {
            case DISCARD :
                break;
            case MOVE:
                break;
            case SHUFFLE:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + abilitytype);
        }


    }
}

