package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

/**
 * Contains all information of lorenzo cards
 */
public class LorenzoCard {
    private LorenzoType type;
    private Color color;

    /**
     * constructor for type Shuffle and Move
     * @param type ability type
     */
    public LorenzoCard(LorenzoType type)
    {
        this.type=type;
        this.color=null;
    }

    /**
     * constructor for type Discard which require a color field
     * @param type ability type
     * @param color color
     */
    public LorenzoCard(LorenzoType type, Color color)
    {
        this.type=type;
        this.color=color;
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

