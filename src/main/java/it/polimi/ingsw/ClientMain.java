package it.polimi.ingsw;

import it.polimi.ingsw.connection.Client;

import java.io.IOException;

public class ClientMain {
    public static void main( String[] args )
    {
        Client client=new Client("127.0.0.1", 8000);
        try {
            client.startClient();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
