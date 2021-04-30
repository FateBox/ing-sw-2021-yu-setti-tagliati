package it.polimi.ingsw.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
            while (true){
                Object o = objIn.readObject();
                if(((String)o).equals("quit")){
                    break;
                } else {
                    objOut.reset();
                    objOut.writeObject("Received: " + (String) o);
                    objOut.flush();
                }
            }
            //close connections
            objOut.close();
            objIn.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
    }
}