package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.MessageType;
import it.polimi.ingsw.enumeration.PlayerAction;
import it.polimi.ingsw.enumeration.Resource;

import java.util.HashMap;

public class Message {
    private boolean broadCast;
    private String playerNick;
    private MessageType type;
    private PlayerAction playerAction;
    private String text;
    private HashMap<Resource,Integer> payment;
    private int idLeader;

    //error message
    public Message(String playerNick,String text)
    {
        this.type=MessageType.ERROR;
        this.broadCast=false;
        this.text=text;
    }
    //Broadcast error message
    public Message(String text)
    {
        this.type=MessageType.ERROR;
        this.broadCast=true;
        this.text=text;
    }



    public String getPlayerNick() {
        return playerNick;
    }

    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }


    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public int getIdLeader() {
        return idLeader;
    }

    public void setIdLeader(int idLeader) {
        this.idLeader = idLeader;
    }
}
