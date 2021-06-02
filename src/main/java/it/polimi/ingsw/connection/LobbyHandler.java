package it.polimi.ingsw.connection;

import java.util.HashMap;

public class LobbyHandler {

    private HashMap<Integer,Lobby> lobbyHashMap;
    private HashMap<String,Integer> playerHashMap;
    private int countId;

    public LobbyHandler()
    {
        lobbyHashMap=new HashMap<>();
        playerHashMap=new HashMap<>();
        countId=0;
    }
    public void addPlayer(String nickname)
    {
        playerHashMap.put(nickname,-1);
    }

    public void removePlayer(String nickname)
    {
        lobbyHashMap.get(playerHashMap.get(nickname)).removePlayer(nickname);
        playerHashMap.remove(nickname);
    }

    public int getNotFullLobby(int maxNumPlayer)
    {
        for (Lobby l:lobbyHashMap.values()) {
            if(l.isAvailable()&&l.getMaxPlayerNumber()==maxNumPlayer)
            {
                return l.getLobbyID();
            }
        }
        return -1;
    }

    public Lobby findLobby(int id)
    {
        return lobbyHashMap.get(id);
    }

    public boolean isNickRepeated(String nick)
    {
        if(playerHashMap.containsKey(nick)){
            return true;
        }
        return false;
    }

    public void createLobby(int id, int num)
    {
        lobbyHashMap.put(id,new Lobby(id,num));
    }

    public void removeLobby(int id)
    {
        for (String nick: lobbyHashMap.get(id).getPlayerNickList()) {
            playerHashMap.put(nick,-1);
        }
        lobbyHashMap.remove(id);
    }

    public int joinLobby(String nickname, Connection connection, int maxNumPlayer)
    {
        int lobbyID=getNotFullLobby(maxNumPlayer);
        if (lobbyID==-1)
        {
            lobbyID = chooseLobbyId();
            createLobby(lobbyID, maxNumPlayer);
        }
        playerHashMap.put(nickname,lobbyID);
        lobbyHashMap.get(lobbyID).addPlayer(nickname, connection);
        return lobbyID;
    }

    public int chooseLobbyId()
    {
        return countId++;
    }

}
