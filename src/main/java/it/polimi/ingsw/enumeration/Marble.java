package it.polimi.ingsw.enumeration;

public enum Marble {
    COIN(0),
    SERVANT(1),
    SHIELD(2),
    STONE(3);

    private int value;

    Marble(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Resource getResource() {
        int v = getValue();
        switch (v) {
            case 0:
                return Resource.COIN;
            case 1:
                return Resource.SERVANT;
            case 2:
                return Resource.SHIELD;
            case 3:
                return Resource.STONE;
            default:
                return null;
        }
    }
}



