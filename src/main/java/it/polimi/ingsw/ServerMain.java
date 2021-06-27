package it.polimi.ingsw;

import it.polimi.ingsw.connection.Server;

import java.io.IOException;

public class ServerMain {
    public static void main( String[] args )
    {
        Server server = new Server();

        try {
            server.start();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage());
        }

    }
}
