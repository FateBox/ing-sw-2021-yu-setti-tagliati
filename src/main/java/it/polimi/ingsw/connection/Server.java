package it.polimi.ingsw.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;

    public Server(int port){
        this.port = port;
    }

    private LobbyHandler lobbyHandler;

    public void startServer() throws IOException {
        //It creates threads when necessary, otherwise it re-uses existing one when possible

        lobbyHandler=new LobbyHandler();
        ServerSocket serverSocket;
        try{
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            System.err.println(e.getMessage()); //port not available
            return;
        }
        System.out.println("Server ready");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Connection newConnection = new Connection(socket, this);
                new Thread(newConnection).start();
            } catch (IOException e) {
                break; //In case the serverSocket gets closed
            }
        }
        serverSocket.close();
    }


    public LobbyHandler getLobbyHandler() {
        return lobbyHandler;
    }
}
