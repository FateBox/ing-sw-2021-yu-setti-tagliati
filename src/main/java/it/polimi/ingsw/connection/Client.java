package it.polimi.ingsw.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private String nickname;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void startClient() throws IOException {

        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");

        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            while (true){
                String inputLine = stdin.nextLine();
                objOut.writeObject(inputLine);
                objOut.flush();
                String socketLine = (String) (objIn.readObject());
                System.out.println(socketLine);
            }
        } catch(NoSuchElementException | ClassNotFoundException e){
            System.out.println("Connection closed");
        } finally {
            stdin.close();
            objOut.close();
            objIn.close();
            socket.close();
        }
    }
}
