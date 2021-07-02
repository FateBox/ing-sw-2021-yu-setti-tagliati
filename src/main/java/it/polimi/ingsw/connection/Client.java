package it.polimi.ingsw.connection;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.Observable;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.Scanner;

/**
 * This class handles the client-side socket connection and notifies to View the information received from server.
 * Its observer is View.
 */
public class Client extends Observable<Message>{

    private final String ip;
    private final int port;
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

    /**
     * Starts the client socket connection.
     * @throws IOException when the connection fails.
     */
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


    /**
     * Handles the input objects from the socket.
     * @param objIn ObjectInputStream of the socket.
     */
    public void read(ObjectInputStream objIn) {

        System.out.println("Reading thread start");

        try {
            while(true) {
                Message message = (Message)objIn.readObject();
                notify(message);
            }
        } catch (Exception e) {
            System.out.println("Reading thread stopped");
        }

    }

    /**
     * Sends Message objects to the socket.
     * @param m Message object to send.
     */
    public void write(Message m) {

        //System.out.println("Start writing");

        try{
            objOut.writeObject(m);
            objOut.flush();
            objOut.reset();
        } catch (Exception e) {
            System.out.println("Stop writing");
        }

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}



