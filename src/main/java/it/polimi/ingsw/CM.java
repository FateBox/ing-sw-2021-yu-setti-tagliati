package it.polimi.ingsw;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.Cli;
import it.polimi.ingsw.client.gui.Gui;
import it.polimi.ingsw.connection.Client;
import javafx.application.Application;

import java.io.IOException;
import java.util.Scanner;

public class CM {

    public static void main( String[] args )
    {

        View view;
        Cli cli;
        Gui gui;


        if(args.length==0) {
            cli = new Cli();
            view = new View(cli);
            view.start("","");

        } else {

            gui = new Gui();
            Application.launch(Gui.class);
        }


    }
}