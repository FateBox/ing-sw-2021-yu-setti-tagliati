package it.polimi.ingsw.connection;

public class Message {
    private String playerNick;
    private int opCod;
    private String info;

    public Message() {
    }

    public String getPlayerNick() {
        return playerNick;
    }

    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

    public int getOpCod() {
        return opCod;
    }

    public void setOpCod(int opCod) {
        this.opCod = opCod;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
