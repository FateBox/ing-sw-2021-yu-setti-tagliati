package it.polimi.ingsw.connection;

import it.polimi.ingsw.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Observable<Message> implements Runnable{

    private Socket socket;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private Server server;
    private String nickname;
    private Boolean active;

    public Connection(Socket socket, Server server)
    {
        this.socket=socket;
        this.server=server;
    }

    public void sendMessage(Message m) throws IOException {
        objOut.writeObject(m);
        objOut.flush();

    }
    public void sendText(String text) throws IOException {
        objOut.writeUTF(text);
        objOut.flush();
    }
    public void asyncSend(final Message m){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMessage(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public synchronized void closeConnection() {
        try{
            String nick="peppo";
            String opcode="0";
            String text="connection closed";
            sendMessage(new Message(nick,opcode,text));
            socket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        active = false;
    }

    private synchronized boolean isActive(){
        return active;
    }

    @Override
    public void run() {
        boolean repeatNick=true;
        try{
            objIn = new ObjectInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
            sendText("Welcome! What's your name?");
            while(repeatNick)
            {
                String playerNickname= objIn.readUTF();
                if(server.checkRepeatedNick(playerNickname))
                {
                    sendText("Someone else is already using this Nickname, please insert another.");
                }
                else{
                    sendText("Got it! Welcome to the lobby "+ playerNickname);
                    this.nickname = playerNickname;
                    server.getPlayerNameList().add(playerNickname);
                    repeatNick = false;
                }
            }

            while(isActive()){
            }
        } catch(IOException e){
            System.err.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
