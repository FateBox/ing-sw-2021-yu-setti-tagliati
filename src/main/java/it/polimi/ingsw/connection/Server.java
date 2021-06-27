package it.polimi.ingsw.connection;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Server {

    private int port;
    private Scanner scanner;
    private LobbyHandler lobbyHandler;
    private ServerSocket serverSocket;

    public Server(){
        lobbyHandler = new LobbyHandler();
        scanner = new Scanner(System.in);
    }


    public void start() throws IOException {

        port = 0;

        try{
            System.out.println("Choose the connection port");
            port = scanner.nextInt();

            serverSocket = new ServerSocket(port);


        } catch (IOException e){
            System.err.println(e.getMessage()); //port not available

        }

        System.out.println("Server ready on port " + port);

        run();

    }


    public void run() {

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Connection newConnection = new Connection(socket, this);
                System.out.println("New connection");
                new Thread(newConnection).start();

            } catch (IOException e) {
                e.printStackTrace();
                //In case the serverSocket gets closed
            }
        }
    }




    public LobbyHandler getLobbyHandler() {
        return lobbyHandler;
    }

}
