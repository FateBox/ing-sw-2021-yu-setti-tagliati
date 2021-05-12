package it.polimi.ingsw.connection;

public class Message {
    private String playerNick;
    private String opCod;
    private String text;

    public Message(String playerNick, String opCod, String text) {

    }

    public String getPlayerNick() {
        return playerNick;
    }

    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

    public String getOpCod() {
        return opCod;
    }

    public void setOpCod(String opCod) {
        this.opCod = opCod;
    }

    public Object getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
