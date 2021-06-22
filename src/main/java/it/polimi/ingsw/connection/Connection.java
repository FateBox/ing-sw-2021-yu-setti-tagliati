package it.polimi.ingsw.connection;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    public void asyncSendMessage(final Message m){
        new Thread(new Runnable() {
            @Override
            public void run() {
                    sendMessage(m);
            }
        }).start();
    }

    //* synchronized method (?)
    public void sendText(String text){

        try {
            objOut.writeUTF(text);
            objOut.flush();
            objOut.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void asyncSendText(final String text){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendText(text);
            }
        }).start();
    }

    public synchronized void closeConnection() {
        try{
            String text="connection closed";
            asyncSendMessage(new Message(text));
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
            //ask Game mode and player number in case of mp
            spOrMp();

            while(isActive()){
                Message newMessage=null;
                try {
                    newMessage=(Message) objIn.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                notify(newMessage);
            }
        } catch(IOException e){
            System.err.println(e.getMessage());
        } finally {
            close();
        }
    }

    private void askNickname() throws IOException {
        boolean repeatNick=true;
        sendText("Welcome! What's your name?");
        while(repeatNick)
        {
            String playerNickname= objIn.readUTF();
            if(server.getLobbyHandler().isNickRepeated(playerNickname))
            {
                asyncSendText("Someone else is already using this Nickname, please insert another.");
            }
            else{
                asyncSendText("Got it! Welcome to the lobby "+ playerNickname);
                this.nickname = playerNickname;
                repeatNick = false;
            }
        }
    }

    public void spOrMp()//send player to the lobby handler and take the player to a lobby;
    {
        int maxPlayer;
        try {
                maxPlayer=Integer.parseInt(objIn.readUTF());
                lobbyId=server.getLobbyHandler().joinLobby(nickname,this, maxPlayer);
        } catch (IOException e) {
            e.printStackTrace();
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
