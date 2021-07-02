package it.polimi.ingsw;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.Cli;
import it.polimi.ingsw.client.gui.Gui;
import javafx.application.Application;

/**
 * It is the main class of the Client application.
 */
public class ClientMain {

    public static void main( String[] args )
    {

        View view;
        Cli cli;
        Gui gui;


        if(args.length==0) {
            gui = new Gui();
            Application.launch(Gui.class);

        } else {
            cli = new Cli();
            view = new View(cli);
            view.start("","");
        }

    }
}
