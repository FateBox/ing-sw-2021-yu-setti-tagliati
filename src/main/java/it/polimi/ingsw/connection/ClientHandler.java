package it.polimi.ingsw.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ArrayList<PlayerSocket> lobby;

    public ClientHandler(Socket socket, ArrayList<PlayerSocket> lobby){
        this.socket = socket;
        this.lobby = lobby;
    }

    private boolean checkDuplicationNick(String nick)
    {
        for (PlayerSocket p: lobby) {
            if(p.getNickname().equals(nick))
                return true;
        }
        return false;
    }
    @Override
    public void run() {
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
            String nick;
            do{
                objOut.writeObject("insert a nickname");
                nick = objIn.readUTF();
                objOut.reset();
                if(checkDuplicationNick(nick)) {

                    objOut.writeObject("duplicated nickname");
                    objOut.flush();
                }
                else
                {
                    lobby.add(new PlayerSocket(nick, socket));
                    objOut.writeObject("added properly in lobby");
                    objOut.flush();
                    break;
                }
            }while(true);

            while (true){
                Object o = objIn.readObject();
                if(((String)o).equals("quit")){
                    break;
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
    //
    //
    //
    public void sendMessage(Message message)
    {
        try {

            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
            objOut.reset();
            objOut.writeObject(message);
            objOut.flush();
        }catch (IOException e)
        {
    }

    }
}