package it.polimi.ingsw.enumeration;

public enum Resource {

    COIN(0),
    SERVANT(1),
    SHIELD(2),
    STONE(3),
    FAITH(4),
    WHITE(5);

    private int value;
     Resource (int value)
    {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
