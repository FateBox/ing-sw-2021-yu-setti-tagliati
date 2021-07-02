package it.polimi.ingsw.connection;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents each created lobby and launches the game when the player list is complete.
 */
public class Lobby {

    private final int lobbyID;
    private HashMap<String,Connection> playerList;
    private final int maxPlayerNumber;
    private boolean full;

    public Lobby(int lobbyID, int maxPlayerNumber)
    {
        this.lobbyID=lobbyID;
        this.maxPlayerNumber=maxPlayerNumber;
        full=false;
        playerList=new HashMap<>();
    }

    /**
     * Adds a new player to the lobby.
     * @param playerNick Nickname of the new player.
     * @param connection Connection of the new player.
     */
    public void addPlayer(String playerNick, Connection connection)
    {
        playerList.put(playerNick,connection);
        if(playerList.size()==maxPlayerNumber)
        {
            full=true;
        }
    }

    /**
     * Remove a player from the lobby.
     * @param playerNick Nickname of the player to remove.
     */
    public void removePlayer(String playerNick)
    {
        playerList.remove(playerNick);
        full=false;
    }

    public boolean isFull()
    {
        return full;
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


    /**
     * Launches the match by instantiating the gameController and the game.
     */
    public void startGame() {

        GameController gameController = new GameController(getPlayerNickList());
        Game game = gameController.getGame();

        for(Connection c : playerList.values()) {
            c.addObserver(gameController);
            c.sendText("Start!");
            game.addObserver(c);
        }

        gameController.start();

    }

    public void stopConnection() {
        for(Connection c : playerList.values()) {
            if(c.isActive()) {
                c.close();
            }
        }
    }

}
