package it.polimi.ingsw.connection;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.enumeration.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class handles the updates from the game and the connection socket server-side, one for each connected client.
 */
public class Connection extends Observable<Message> implements Observer<Message>, Runnable {

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

    /**
     * Sends Message object to the client.
     * @param m Message object to send.
     */
    public void sendMessage(Message m)  {
        try {
            objOut.writeObject(m);
            objOut.flush();
            objOut.reset();
        } catch (IOException e) {
            System.out.println("Connection of " + nickname + " reset");
        }
    }


    /**
     * Sends text strings to the client after enveloping them into Messages.
     * @param text Text string to send.
     */
    //* synchronized method (?)
    public void sendText(String text){

        Message message=new Message();
        message.setText(text);
        message.setType(MessageType.SERVER);
        message.setNeedReply(false);
        sendMessage(message);
    }

    /**
     * Sends to the client text strings which needs an reply.
     * @param text Text string to send.
     */
    public void askReply(String text){

        Message message=new Message();
        message.setText(text);
        message.setType(MessageType.SERVER);
        message.setNeedReply(true);
        sendMessage(message);
    }


    /**
     * Closes the socket connection.
     */
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

    public synchronized boolean isActive(){
        return active;
    }

    protected void close() {
        closeConnection();
        System.out.println("De-registering client...");
        server.getLobbyHandler().removePlayer(nickname);
        System.out.println("Done!");
    }


    /**
     * Handles the client-server communication by sending and receiving objects from the socket.
     */
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
                l.startGame();
            }

            while(isActive()){

                Message newMessage=(Message) objIn.readObject();
                System.out.println("received a new message");
                //System.out.println("Message from " + nickname + ": " + newMessage.getText());
                //newMessage.setPlayerNick(nickname);

                notify(newMessage);

            }

        } catch(Exception e){
            System.out.println(nickname + ": connection stop");
        } finally {
            close();
            server.deactiveMatch(lobbyId);
        }
    }

    private void askNickname() throws IOException {
        boolean repeatNick=true;
        askReply("Welcome! What's your name?");
        String playerNickname = "ok";
        while(repeatNick)
        {
            try {
                playerNickname= ((Message)objIn.readObject()).getText();
            } catch (ClassNotFoundException e) {
                System.out.println("Not found");
            }
            System.out.println(playerNickname);

            if(server.getLobbyHandler().isNickRepeated(playerNickname))
            {
                askReply("Someone else is already using this Nickname, please insert another.");
            }
            else{
                sendText("Got it!");
                sendText("Welcome to the lobby, "+ playerNickname);
                this.nickname = playerNickname;
                repeatNick = false;
            }
        }
    }

    private void spOrMp()//send player to the lobby handler and take the player to a lobby;
    {
        int maxPlayer;
        boolean done = false;
        while(!done) {
            try {
                askReply("Choose mode: 1/2/3/4 player(s)");
                maxPlayer=Integer.parseInt(((Message)objIn.readObject()).getText());
                if(maxPlayer>0 && maxPlayer<5) {
                    lobbyId=server.getLobbyHandler().joinLobby(nickname,this, maxPlayer);
                    sendText("You join the lobby " + lobbyId);
                    sendText("Please wait until game starts...");
                    System.out.println("Player " + nickname + " join the lobby " + lobbyId);
                    done = true;
                } else {
                    sendText("Wrong number");
                }
            } catch (IOException e) {
                sendText("Input error");
            } catch (ClassNotFoundException e) {
                System.out.println("Not found");
            }
        }

    }


    /**
     * Receives messages from the game and sends them to the client.
     * @param message Message to send.
     */
    @Override
    public void update(Message message) {
        if(message.isBroadCast() || message.getPlayerNick().equals(nickname))
        {
            sendMessage(message);
        }
    }
}
