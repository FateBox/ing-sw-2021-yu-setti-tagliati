package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.View;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class HomeController {


    @FXML
    private VBox serverVBox;
    @FXML
    private TextField ip;
    @FXML
    private TextField port;
    @FXML
    private Button okButton;
    @FXML
    private Label text;


    @FXML
    private Pane modePane;
    @FXML
    private TextField nickname;
    @FXML
    private ChoiceBox modeChoice;
    @FXML
    private Button playButton;

    private boolean nickSet = false;


    private View view;



    public void submit() {

        String ipAnswer = ip.getCharacters().toString();
        String portAnswer = port.getCharacters().toString();
        new Thread(() -> view.start(ipAnswer,portAnswer)).start();

    }


    public void confirm() {

        if(!nickSet) {
            String name = nickname.getCharacters().toString();
            view.sendMessage(view.createServerTextMessage(name));
            view.setNickClient(name);

        } else {
            if(modeChoice.getValue().equals("4 players")) {
                view.sendMessage(view.createServerTextMessage("4"));
            } else if(modeChoice.getValue().equals("2 players")) {
                view.sendMessage(view.createServerTextMessage("2"));
            } else if(modeChoice.getValue().equals("3 players")) {
                view.sendMessage(view.createServerTextMessage("3"));
            } else {
                view.sendMessage(view.createServerTextMessage("1"));
            }

            modeChoice.setDisable(true);
            playButton.setDisable(true);
        }

    }

    public void showText(String str) {
        Platform.runLater(() -> text.setText(str));


        if(str.equals("Got it!")) {
            nickSet=true;
            showMode();
        }


    }

    public void setView(View view) {
        this.view = view;
    }

    public void showServer() {
        serverVBox.setDisable(false);
        serverVBox.setVisible(true);
        modePane.setDisable(true);
        modePane.setVisible(false);
        ip.setText("localhost");
        port.setText("8000");
    }

    public void showNick() {
        serverVBox.setDisable(true);
        serverVBox.setVisible(false);
        modePane.setDisable(false);
        modePane.setVisible(true);
        nickname.setDisable(false);
        nickname.setVisible(true);
        modeChoice.setDisable(true);
        modeChoice.setVisible(false);
        playButton.setText("Ok");
    }


    public void showMode() {
        nickSet=true;
        nickname.setDisable(true);
        nickname.setVisible(false);
        modeChoice.setDisable(false);
        modeChoice.setVisible(true);


        ArrayList<String> modes = new ArrayList<>();
        modes.add("1 player");
        modes.add("2 players");
        modes.add("3 players");
        modes.add("4 players");
        modeChoice.setItems((FXCollections.observableArrayList(modes)));
        modeChoice.setStyle("-fx-font: 16px \"Bell MT\";");

        playButton.setText("Play");
    }



}
