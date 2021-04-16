package it.polimi.ingsw.enumeration;

public enum Resource {

    COIN(0),
    SERVANT(1),
    SHIELD(2),
    STONE(3),
    WHITE(4),
    FAITH(5);
    private int value;
    private Resource (int value)
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
