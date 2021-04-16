package it.polimi.ingsw.model.enumeration;
//elenco livello carte sviluppo

public enum Level {

    LV1(0),LV2(1),LV3(2);

    private int index;

    private Level (int index)
    {this.index =index;}

    public int get()
    {
        return  index;
    }
}