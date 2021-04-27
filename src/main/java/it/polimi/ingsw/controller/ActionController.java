package it.polimi.ingsw.controller;

public abstract class ActionController {

    /*

    every action controller must have methods called after server receives right command message, that modify model.
    these methods can store action state inside the class if necessary and forward new possibleOperation through model.

    YES NOW I UNDERSTAND WHY IT IS WORTH CREATING A NEW ACTION CONTROLLER EVERY ACTION, but clearing old state does the same

    now the real problems is, can we just implement a "weird RPC" that return values through model observable.
    we do need to use a decoder class between virtualView and ALL controller classes?
    do we have to store message information in virtual view and observe that by a decoder class?

    all that solutions seems transparent to controller in my opinion.
    is any of them surely valid?
     */
}
