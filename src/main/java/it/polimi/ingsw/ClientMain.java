package it.polimi.ingsw;

import it.polimi.ingsw.connection.Client;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {

    public static void main( String[] args )
    {

        Scanner scanner = new Scanner(System.in);
        boolean ok = false;

        while(!ok) {
            System.out.println("Server IP address:");
            String ip = scanner.nextLine();
            System.out.println("Server port:");
            int port = Integer.parseInt(scanner.nextLine());
            // La parte per ip e porta finirà in launcher separati per cli e gui!


            Client client = new Client(ip, port);
            try {
                client.start();
                ok = true;
            } catch (IOException e) {
                System.out.println("Invalid input");;
            }
        }



    }
}
