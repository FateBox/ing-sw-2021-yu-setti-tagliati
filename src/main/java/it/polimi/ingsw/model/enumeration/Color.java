package it.polimi.ingsw.model.enumeration;
//elenco colori carte sviluppo
public enum Color {
    GREEN(0),BLUE(1),YELLOW(2),PURPLE(3);

    private final int index;

    private Color (int index)

    {this.index =index;}

    public int get()
    {
        return  index;
    }
}
