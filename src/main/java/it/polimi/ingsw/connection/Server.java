package it.polimi.ingsw.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * It initializes the server application.
 */
public class Server {

    private int port;
    private LobbyHandler lobbyHandler;
    private ServerSocket serverSocket;
    private Scanner scanner = new Scanner(System.in);

    public Server(){
        lobbyHandler = new LobbyHandler();
    }


    /**
     * Initializes the server socket on a selected port.
     * @throws IOException when the connection fails.
     */
    public void start() throws IOException {

        port = 8000;

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


    /**
     * Launches the server, it should be always ready to accept new client connections.
     */
    public void run() {

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Connection newConnection = new Connection(socket, this);
                System.out.println("New connection");
                new Thread(newConnection).start();

            } catch (IOException e) {
                System.out.println("Server stop");
                //In case the serverSocket gets closed

            }
        }
    }

    public void deactiveMatch(int lobbyID) {
        Lobby lobby = lobbyHandler.findLobby(lobbyID);
        lobby.stopConnection();
    }

    public LobbyHandler getLobbyHandler() {
        return lobbyHandler;
    }

}
