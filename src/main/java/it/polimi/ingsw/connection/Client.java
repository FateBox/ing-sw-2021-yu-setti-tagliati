package it.polimi.ingsw.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void startClient() throws IOException {

        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");

        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            while (true){
                String inputLine = stdin.nextLine();
                objOut.writeUTF(inputLine);
                objOut.flush();
                String socketLine = objIn.readUTF();
                System.out.println(socketLine);
            }
        } catch(NoSuchElementException e){
            System.out.println("Connection closed");
        } finally {
            stdin.close();
            in.close();
            out.close();
            objOut.close();
            objIn.close();
            socket.close();
        }
    }
}
