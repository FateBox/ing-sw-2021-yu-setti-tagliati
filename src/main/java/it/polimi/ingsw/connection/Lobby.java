package it.polimi.ingsw.connection;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    private HashMap<String, Connection> connectionHashMap;
    public ArrayList<String> getPlayerNameList(){
        return new ArrayList<String>(connectionHashMap.keySet());
    }

    public HashMap<String, Connection> getConnectionHashMap() {
        return connectionHashMap;
    }

}
