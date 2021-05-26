package it.polimi.ingsw.connection;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.enumeration.State;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection extends Observable<Message> implements Runnable{

    private Socket socket;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private Server server;
    private String nickname;
    private Boolean active;
    private State state;

    public Connection(Socket socket, Server server)
    {
        this.socket=socket;
        this.server=server;
    }

    public void sendMessage(Message m)  {
        try {
            objOut.writeObject(m);
            objOut.flush();
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
            String nick=nickname;
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
                sendText("Someone else is already using this Nickname, please insert another.");
            }
            else{
                sendText("Got it! Welcome to the lobby "+ playerNickname);
                this.nickname = playerNickname;
                repeatNick = false;
            }
        }
    }




}
