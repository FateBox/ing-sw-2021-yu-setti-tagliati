package it.polimi.ingsw;

import java.util.ArrayList;

public abstract class Observable {
    public Observable(){
        this.observers=new ArrayList<>();
    }
    ArrayList<Observer> observers;


    void attach(Observer observer){
        observers.add(observer);
    }
    void detach(Observer observer)
    {
        observers.remove(observer);
    }
}
