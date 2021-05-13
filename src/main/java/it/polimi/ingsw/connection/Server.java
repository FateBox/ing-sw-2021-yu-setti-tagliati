package it.polimi.ingsw.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private ArrayList<PlayerSocket> lobby;
    private ArrayList<String> playerNameList;
    public Server(int port){
        this.port = port;
    }

    public void startServer() throws IOException {
        //It creates threads when necessary, otherwise it re-uses existing one when possible
        lobby=new ArrayList<PlayerSocket>();
        playerNameList=new ArrayList<String>();
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try{
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            System.err.println(e.getMessage()); //port not available
            return;
        }
        System.out.println("Server ready");
        while (true){
            try{
                Socket socket = serverSocket.accept();
                executor.submit(new Connection(socket,this));
            }catch(IOException e){
                break; //In case the serverSocket gets closed
            }
        }
        executor.shutdown();
        serverSocket.close();
    }

    public ArrayList<PlayerSocket> getLobby() {
        return lobby;
    }

    public ArrayList<String> getPlayerNameList() {
        return playerNameList;
    }

    public boolean checkRepeatedNick(String nick)
    {
        if(playerNameList.contains(nick))
        {
            return true;
        }
        return false;
    }
}
