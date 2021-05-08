package it.polimi.ingsw.connection;

import it.polimi.ingsw.enumeration.Status;

import java.net.Socket;

public class PlayerSocket {
    public String getNickname() {
        return nickname;
    }

    public Socket getSocket() {
        return socket;
    }

    public PlayerSocket(String nickname, Socket socket) {
        this.nickname = nickname;
        this.socket = socket;
    }

    private String nickname;
    private Socket socket;
    private Status status;

}
