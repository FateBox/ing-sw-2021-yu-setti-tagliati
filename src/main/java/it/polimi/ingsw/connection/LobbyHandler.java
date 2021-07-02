package it.polimi.ingsw.connection;

import java.util.HashMap;


/**
 * It handles lobby creation and prepares the players for their right lobby.
 */
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

    /*

        public void addPlayer(String nickname)
        {
            playerHashMap.put(nickname,-1);
        }
     */


    /**
     * Removes a player from the game.
     * @param nickname Nickname of the player to remove.
     */
    public void removePlayer(String nickname)
    {
        //lobbyHashMap.get(playerHashMap.get(nickname)).removePlayer(nickname);
        playerHashMap.remove(nickname);
    }

    /**
     * Gets the incomplete lobbies, which are still waiting for new players.
     * @param maxNumPlayer The number of players that the lobby creator sets.
     * @return Index of an incomplete lobby.
     */
    public int getNotFullLobby(int maxNumPlayer)
    {
        for (Lobby l:lobbyHashMap.values()) {
            if((!l.isFull())&&l.getMaxPlayerNumber()==maxNumPlayer)
            {
                return l.getLobbyID();
            }
        }
        return -1;
    }

    /**
     * Finds a lobby in the lobbies list through its id.
     * @param id Lobby ID.
     * @return The selected lobby.
     */
    public Lobby findLobby(int id)
    {
        return lobbyHashMap.get(id);
    }

    /**
     * Checks if the nickname of the player is already present in the game.
     * @param nick Nickname to check.
     * @return true if the nickname is already used by others, false otherwise.
     */
    public boolean isNickRepeated(String nick)
    {
        if(playerHashMap.containsKey(nick)){
            return true;
        }
        return false;
    }

    /**
     * Creates a new lobby.
     * @param id New lobby's id.
     * @param num Number of players in the lobby.
     */
    public void createLobby(int id, int num)
    {
        lobbyHashMap.put(id,new Lobby(id,num));
    }


    /*
    public void removeLobby(int id)
    {
        for (String nick: lobbyHashMap.get(id).getPlayerNickList()) {
            playerHashMap.put(nick,-1);
        }
        lobbyHashMap.remove(id);
    }*/

    /**
     * Assigns the players to their lobby.
     * @param nickname Nickname of the player.
     * @param connection Connection of the player.
     * @param maxNumPlayer Game mode (max. number of players in match) chosen by the player.
     * @return The id of the joined lobby.
     */
    public int joinLobby(String nickname, Connection connection, int maxNumPlayer)
    {
        int lobbyID=getNotFullLobby(maxNumPlayer);
        if (lobbyID==-1)
        {
            lobbyID = chooseLobbyId();
            createLobby(lobbyID, maxNumPlayer);
        }
        playerHashMap.put(nickname,lobbyID);

        Lobby lobby = lobbyHashMap.get(lobbyID);
        lobby.addPlayer(nickname, connection);


        return lobbyID;
    }

    /**
     * Updates the newest lobby id.
     * @return The updated lobby id.
     */
    public int chooseLobbyId()
    {
        return countId++;
    }

}
