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
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
            while (true){
                String line = objIn.readUTF();
                if(line.equals("quit")){
                    break;
                } else {
                    objOut.reset();
                    objOut.writeUTF("Received: " + line);
                    objOut.flush();
                }
            }
            //close connections
            in.close();
            out.close();
            objOut.close();
            objIn.close();
            socket.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}