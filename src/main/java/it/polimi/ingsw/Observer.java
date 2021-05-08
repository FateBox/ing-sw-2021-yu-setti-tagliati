package it.polimi.ingsw;

import it.polimi.ingsw.connection.Message;

public interface Observer {
    void update(Message m);
}
