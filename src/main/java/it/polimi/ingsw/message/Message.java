package it.polimi.ingsw.message;

import java.util.List;

public class Message {
    private boolean broadCast;
    private String playerNick;
    private String opCod;
    private String text;
    public Message(String playerNick, String opCod, String text) {

    }
    public Message(boolean broadCast, String opCod, String text) {

    }
    public Message(boolean broadCast, String opCod) {

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
