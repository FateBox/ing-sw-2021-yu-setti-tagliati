package it.polimi.ingsw.connection;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    private final int lobbyID;
    private HashMap<String,Connection> playerList;
    private final int maxPlayerNumber;
    private boolean full;
    GameController gameController;

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


    public void startGame(Lobby lobby) {

        GameController gameController = new GameController(lobby.getPlayerNickList());
        Game game = gameController.getGame();

        for(Connection c : playerList.values()) {
            c.addObserver(gameController);
            c.sendText("Start!");
            game.addObserver(c);
        }

        gameController.start();

    }

}
