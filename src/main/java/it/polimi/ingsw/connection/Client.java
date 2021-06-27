package it.polimi.ingsw.connection;

import it.polimi.ingsw.Message;
import it.polimi.ingsw.enumeration.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private String nickname;
    private Scanner stdin;

    private Thread fromSocket;
    private Thread toSocket;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.stdin = new Scanner(System.in);
    }

    public void start() throws IOException {

        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");

        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());

        try{

            fromSocket = new Thread(() -> read(objIn));
            fromSocket.start();

            toSocket = new Thread(() -> write(objOut));
            toSocket.start();


            fromSocket.join();


        } catch(Exception e){
            System.out.println("Connection closed");
        } finally {

            if(!fromSocket.isInterrupted()) {
                fromSocket.stop();
            }
            if(!toSocket.isInterrupted()) {
                toSocket.stop();
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
                String input = ((Message)objIn.readObject()).getText();
                System.out.println(input);
            }
        } catch (Exception e) {
            System.out.println("Reading thread exception");
        }

    }

    public void write(ObjectOutputStream objOut) {

        System.out.println("Writing thread start");

        try{
            while (true) {
                String inputLine = stdin.nextLine();
                Message message=new Message();
                message.setText(inputLine);
                message.setType(MessageType.SERVER);
                objOut.writeObject(message);
                objOut.flush();
                objOut.reset();
            }
        } catch (Exception e) {
            System.out.println("Writing thread exception");
        }

    }
}



