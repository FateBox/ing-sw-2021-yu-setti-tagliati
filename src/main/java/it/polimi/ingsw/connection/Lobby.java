package it.polimi.ingsw.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lobby {

    private int lobbyID;
    private HashMap<String,Connection> playerList;
    private int maxPlayerNumber;
    private boolean full;


    public Lobby(int lobbyID, int maxPlayerNumber)
    {
        this.lobbyID=lobbyID;
        this.maxPlayerNumber=maxPlayerNumber;
        full=false;
        playerList=new HashMap<>();
    }

    public void addPlayer(String playerNick, Connection connection)
    {
        playerList.put(playerNick,connection);
        if(playerList.size()==maxPlayerNumber)
        {
            full=true;
        }
    }

    public void removePlayer(String playerNick)
    {
        playerList.remove(playerNick);
        full=false;
    }

    public boolean isAvailable()
    {
        if(full)
        {
            return false;
        }
        return true;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public ArrayList<String> getPlayerNickList() {
        return new ArrayList<String>(playerList.keySet());
    }

    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }
}
