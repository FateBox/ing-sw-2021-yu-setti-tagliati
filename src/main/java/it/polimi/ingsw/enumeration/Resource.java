package it.polimi.ingsw.enumeration;

public enum Resource {

    COIN(0),
    SERVANT(1),
    SHIELD(2),
    STONE(3),
    FAITH(4),
    WHITE(5),
    ANY(6);

    private int value;
     Resource (int value)
    {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public Marble getMarble() throws Exception
    {
        int v=getValue();
        switch (v)
        {
            case 0:
                return Marble.COIN;
            case 1:
                return Marble.SERVANT;
            case 2:
                return Marble.SHIELD;
            case 3:
                return Marble.STONE;
            default:
                throw new Exception();
        }
    }

}
