package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

public class LorenzoCard {
    private LorenzoType type;
    private Color color;

    public LorenzoCard(LorenzoType type)
    {
        this.color=null;
    }
    public LorenzoCard(LorenzoType type, Color color)
    {

    }

    public LorenzoType getType() {
        return type;
    }

    public void setType(LorenzoType type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

