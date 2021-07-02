package it.polimi.ingsw.controller;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.client.Cli;
import it.polimi.ingsw.connection.Client;
import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    ArrayList<String> nickList;
    ArrayList<Client> clientList;
    GameController gameController;
    private void createCustomGame()
    {
        int i= 0;
        nickList=new ArrayList<>();
        nickList.add("player1");
        nickList.add("player2");
        nickList.add("player3");
        nickList.add("player4");
        clientList=new ArrayList<>();
        for (String p:nickList)
        {
            clientList.add(new Client("localhost",8000));
            clientList.get(i).setNickname(nickList.get(i));
            i++;
        }
        gameController=new GameController(nickList);

    }
}