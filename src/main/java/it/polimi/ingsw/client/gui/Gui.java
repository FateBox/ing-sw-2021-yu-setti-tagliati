package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launches the GUI for the client application.
 */

public class Gui extends Application {

    private Stage stage;
    private Scene scene;
    private HomeController homeController;

    private MainController mainController;


    private View view;

    /* 0: server ip/port setting
       1: nickname setting & mode setting
       2: in game
     */
    private int current;


    /**
     * Sets the initial scene for the GUI by loading the first scene.
     * @param st Default window
     * @throws Exception
     */
    @Override
    public void start(Stage st) throws Exception {
        this.stage = st;
        this.stage.setTitle("Masters of Renaissance");
        this.stage.setResizable(false);


        current = 0;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent home = loader.load();

        this.scene = new Scene(home);
        homeController = loader.getController();

        view = new View(this);
        homeController.setView(view);


        stage.setScene(scene);

        homeController.showServer();

        stage.setOnCloseRequest(e-> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();

    }


    public void setView(View view) {
        this.view = view;
    }


    /**
     * Displays messages received from server, which should be handled in different scenes.
     * @param text The text message to display.
     */
    public void printText(String text) {
        if(current<2) {

            System.out.println(text);
            Platform.runLater(() -> homeController.showText(text));

            if(text.equals("Welcome! What's your name?")) {
                setCurrent(1);
                askNickname();
            }

            if(text.equals("Start!")) {
                setCurrent(2);

                Platform.runLater(() -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
                    try {
                        System.out.println("here");
                        Parent main = loader.load();
                        this.scene = new Scene(main);
                        this.mainController = loader.getController();
                        this.mainController.setView(view);
                        stage.setScene(scene);

                    }catch(Exception e) {
                        System.out.println("error in loading scene");
                    }

                });
            }

        } else {
            Platform.runLater(() -> mainController.printText(text));
        }

    }


    /**
     * Initializes the scene for asking nickname to the user.
     */
    public void askNickname() {
        Platform.runLater(() -> homeController.showNick());
    }


    public int getCurrent() {
        return this.current;
    }

    public void setCurrent(int num) {
        current = num;
    }

}
