package it.polimi.ingsw.connection;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.enumeration.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client extends Observable<Message>{
    private String ip;
    private int port;
    private String nickname;
    private Scanner stdin;

    ObjectOutputStream objOut;
    ObjectInputStream objIn;

    private Thread fromSocket;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.stdin = new Scanner(System.in);
    }

    public void start() throws IOException {

        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");

        objOut = new ObjectOutputStream(socket.getOutputStream());
        objIn = new ObjectInputStream(socket.getInputStream());

        try{

            fromSocket = new Thread(() -> read(objIn));
            fromSocket.start();

            fromSocket.join();


        } catch(Exception e){
            System.out.println("Connection closed");
        } finally {

            if(!fromSocket.isInterrupted()) {
                fromSocket.stop();
            }

            stdin.close();
            objOut.close();
            objIn.close();
            socket.close();
        }
    }


    public void read(ObjectInputStream objIn) {

        System.out.println("Reading thread start");

        try {
            while(true) {
                Message message = (Message)objIn.readObject();
                notify(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Reading thread exception");
        }

    }

    public void write(Message m) {

        System.out.println("Start writing");

        try{
            objOut.writeObject(m);
            objOut.flush();
            objOut.reset();
        } catch (Exception e) {
            System.out.println("Writing exception");
        }

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}



