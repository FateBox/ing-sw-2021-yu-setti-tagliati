package it.polimi.ingsw.connection;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.enumeration.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UTFDataFormatException;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Observable<Message> implements Runnable, Observer<Message> {

    private Socket socket;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private Server server;
    private String nickname;
    private int lobbyId;
    private Boolean active;

    public Connection(Socket socket, Server server)
    {
        this.socket=socket;
        this.server=server;
        this.active=true;
    }

    public void sendMessage(Message m)  {
        try {
            objOut.writeObject(m);
            objOut.flush();
            objOut.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //* synchronized method (?)
    public void sendText(String text){

        Message message=new Message();
        message.setText(text);
        message.setType(MessageType.SERVER);
        sendMessage(message);
    }


    public synchronized void closeConnection() {
        try{
            String text="connection closed";
            Message m=new Message();
            m.setBroadCast(false);
            m.setType(MessageType.ERROR);
            m.setText(text);
            sendMessage(m);
            socket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        active = false;
    }

    private synchronized boolean isActive(){
        return active;
    }

    private void close(){
        closeConnection();
        System.out.println("De-registering client...");
        server.getLobbyHandler().removePlayer(nickname);
        System.out.println("Done!");
    }


    @Override
    public void run() {

        try{
            objIn = new ObjectInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());

            askNickname();
            System.out.println("Player " + nickname + " join the game");
            //ask Game mode and player number in case of mp
            spOrMp();

            Lobby l = server.getLobbyHandler().findLobby(lobbyId);
            if(l.isFull()) {
                l.startGame(server.getLobbyHandler().findLobby(lobbyId));
            }

            while(isActive()){

                Message newMessage=(Message) objIn.readObject();
                System.out.println("Message from " + nickname);

                notify(newMessage);

            }

        } catch(Exception e){
            System.out.println(nickname + ": connection stop");
        } finally {
            close();
        }
    }

    private void askNickname() throws IOException {
        boolean repeatNick=true;
        sendText("Welcome! What's your name?");
        String playerNickname = "ok";
        while(repeatNick)
        {
            try {
                playerNickname= ((Message)objIn.readObject()).getText();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(playerNickname);

            if(server.getLobbyHandler().isNickRepeated(playerNickname))
            {
                sendText("Someone else is already using this Nickname, please insert another.");
            }
            else{
                sendText("Got it! Welcome to the lobby "+ playerNickname);
                this.nickname = playerNickname;
                repeatNick = false;
            }
        }

    }

    public void spOrMp()//send player to the lobby handler and take the player to a lobby;
    {
        int maxPlayer;
        boolean done = false;

        while(!done) {
            try {
                sendText("Choose mode: 1/2/3/4 player(s)");
                maxPlayer=Integer.parseInt(((Message)objIn.readObject()).getText());
                if(maxPlayer>0 && maxPlayer<5) {
                    lobbyId=server.getLobbyHandler().joinLobby(nickname,this, maxPlayer);
                    sendText("You join the lobby " + lobbyId);
                    System.out.println("Player " + nickname + " join the lobby " + lobbyId);
                    done = true;
                } else {
                    sendText("Wrong number");
                }
            } catch (IOException e) {
                sendText("Input error");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void update(Message message) {
        if(message.isBroadCast() || message.getPlayerNick().equals(nickname))
        {
            sendMessage(message);
        }
    }
}
