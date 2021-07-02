package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.PlayerInformation;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.enumeration.AbilityType;
import it.polimi.ingsw.enumeration.ImageEnum;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.util.*;

public class MainController {

    private View view;
    @FXML
    private Pane market;
    @FXML
    private Pane devcards;
    @FXML
    private Pane leadercards;
    @FXML
    private Pane strongbox;
    @FXML
    private Pane depot;
    @FXML
    private Pane devslots;
    @FXML
    private Pane path;
    @FXML
    private Pane actions;
    @FXML
    private Pane leader;
    @FXML
    private Pane player;
    @FXML
    private TextArea text;

    private int[] initialInput;
    int actionInput; //scelta azione giocatore
    int chooseInput; //scelta decisioni
    int depotInput; //scelta cella del deposito
    int leaderInput; //scelta leader
    int positionInput; //scelta dello slot per la carta sviluppo comprata
    int[] devSlotInput; //scelta slot da attivare
    int[] anyInput; //scelta risorse any
    int resourcesInput; //risorse prelevate dal deposito
    int[] exchangeInput;  //scelta risorse bianche
    Scanner input = new Scanner(System.in); //variabile ingresso input



    private boolean done=false;

    public boolean isDone() {
        return done;
    }
    public int[] getInitialInput() { return initialInput; }

    public int[] getAnyInput() {
        return anyInput;
    }

    public int getDepotInput() {
        return depotInput;
    }

    public int getAction() {
        return actionInput;
    }

    public int getChooseInput() {
        return chooseInput;
    }

    public int getLeaderInput() {
        return leaderInput;
    }

    public int[] getDevSlotInput() {
        return devSlotInput;
    }

    public int getResourcesInput() {
        return resourcesInput;
    }

    public int[] getExchangeInput() {
        return exchangeInput;
    }


    public int getPositionInput() {
        return positionInput;
    }

    public void initialLeader(PlayerInformation pi, String cn, int nPlayer)
    {
        done=false;
        initialInput[0]=0;
        initialInput[1]=0;
        for(int i=0;i<4;i++){
            ((GridPane)leadercards.getChildren().get(0)).getChildren().get(i).setDisable(true);
            ((GridPane)leadercards.getChildren().get(0)).getChildren().get(i).setVisible(false);
        }
        int i=0;
        for (LeaderCard lc : pi.getLeaderCards()) {
            il(i,lc);
            i++;
        }
        leadercards.setVisible(true);
        leadercards.setDisable(false);

    }

    private void il(int i,LeaderCard lc){
        ((GridPane)leadercards.getChildren().get(0)).getChildren().get(i).setDisable(false);
        ((GridPane)leadercards.getChildren().get(0)).getChildren().get(i).setVisible(true);
        ((ImageView)((GridPane)leadercards.getChildren().get(0)).getChildren().get(i)).setImage(new Image(ImageEnum.getUrl(("LEADER"+lc.getID()))));
        ((GridPane)leadercards.getChildren().get(0)).getChildren().get(i).setOnMouseClicked(e->{
            if(initialInput[0]==0){
                initialInput[0]=+1;
            }
            else {
                initialInput[1]=+1;
                done=true;
                leadercards.setDisable(true);
                leadercards.setVisible(false);
            }
            ((GridPane)leadercards.getChildren().get(0)).getChildren().get(i).setDisable(true);
        });
    }

    public void initialResource(int nPlayer) {
        done=false;
        switch (nPlayer) {
            case 0:
                initialInput[0] = 0;
                initialInput[1] = 0;
                break;
            case 1:
                //System.out.println("\nChoose a resource: Coin(1) Servant(2) Shield(3) Stone(4)\n");
                Platform.runLater(()->{
                    ((Label)actions.getChildren().get(5)).setText("Choose a resource: Coin(1) Servant(2) Shield(3) Stone(4)");
                });



               ir(1);
                break;
            default:
                Platform.runLater(()->{
                    ((Label)actions.getChildren().get(5)).setText("Choose 2 resource: Coin(1) Servant(2) Shield(3) Stone(4)");
                });


                ir(2);
        }
    }

    private void ir(int i){
        Platform.runLater(()->{

            ((Button)actions.getChildren().get(0)).setText("Coin(1)");
            ((Button)actions.getChildren().get(1)).setText("Servant(2)");
            ((Button)actions.getChildren().get(2)).setText("Shield(3)");
            ((Button)actions.getChildren().get(3)).setText("Stone(4)");
        actions.getChildren().get(0).setVisible(true);
        actions.getChildren().get(0).setDisable(false);
        actions.getChildren().get(1).setVisible(true);
        actions.getChildren().get(1).setDisable(false);
        actions.getChildren().get(2).setVisible(true);
        actions.getChildren().get(2).setDisable(false);
        actions.getChildren().get(3).setVisible(true);
        actions.getChildren().get(3).setDisable(false);
        ((Button)actions.getChildren().get(0)).setOnAction(e->{
            if(initialInput[0]==0){
                initialInput[0]=1;
                if(i==1){
                    done=true;
                }else {
                    ir(i);
                }
            }else {
                initialInput[1]=1;
                actions.setDisable(true);
                actions.setVisible(false);
                if(i==2){
                    done=true;
                }
            }
        });
        ((Button)actions.getChildren().get(1)).setOnAction(e->{
            if(initialInput[0]==0){
                initialInput[0]=2;
                if(i==1){
                    done=true;
                }else {
                    ir(i);
                }
            }else {
                initialInput[1]=2;
                actions.setDisable(true);
                actions.setVisible(false);
                if(i==2){
                    done=true;
                }
            }
        });
        ((Button)actions.getChildren().get(2)).setOnAction(e->{
            if(initialInput[0]==0){
                initialInput[0]=3;
                if(i==1){
                    done=true;
                }else {
                    ir(i);
                }
            }else {
                initialInput[1]=3;
                actions.setDisable(true);
                actions.setVisible(false);
                if(i==2){
                    done=true;
                }
            }
        });
        ((Button)actions.getChildren().get(3)).setOnAction(e->{
            if(initialInput[0]==0){
                initialInput[0]=4;
                if(i==1){
                    done=true;
                }else {
                    ir(i);
                }
            }else {
                initialInput[1]=4;
                actions.setDisable(true);
                actions.setVisible(false);
                if(i==2){
                    done=true;
                }
            }
        });});
    }

    public void position() {
        for(Node x : devslots.getChildren()){
            x.setVisible(false);
            x.setDisable(true);
        }
        done=false;
        devcards.setDisable(false);
        devcards.setVisible(true);
        ((ImageView) devslots.getChildren().get(0)).setOnMouseClicked(e -> {positionInput=1;
            done=true;
            devslots.setDisable(true);
        });
        ((ImageView) devslots.getChildren().get(1)).setOnMouseClicked(e -> {positionInput=2;
            done=true;
            devslots.setDisable(true);});
        ((ImageView) devslots.getChildren().get(2)).setOnMouseClicked(e -> {positionInput=3;
            done=true;
            devslots.setDisable(true);});
    }

    public void watch()
    {
        Platform.runLater(()->{


        resetAction();
        done=false;
        System.out.println("\nWatch: Development grid (1), Market (2)\n");
        ((Button) actions.getChildren().get(0)).setText("grid");
        ((Button) actions.getChildren().get(1)).setText("market");
        ((Button) actions.getChildren().get(0)).setVisible(true);
        ((Button) actions.getChildren().get(1)).setVisible(true);
        actions.setVisible(true);
        actions.setDisable(false);
        ((Button) actions.getChildren().get(0)).setOnAction(e->{actionInput=1;done=true;actions.setDisable(true);actions.setVisible(false);});
        ((Button) actions.getChildren().get(1)).setOnAction(e->{actionInput=2;done=true;actions.setDisable(true);actions.setVisible(false);});
        });
    }
    private void resetAction(){
        for(Node x : actions.getChildren()){
            x.setVisible(false);
            x.setDisable(true);
        }
    }

    public void action(PlayerInformation pi, boolean[] popeSpace, String cn, HashMap<String, PlayerInformation> m)
    {
        done=false;
        resetAction();
        printPlayerBoard(pi,popeSpace, cn);
        printFaithTrack(m, popeSpace);
        actions.getChildren().get(5).setVisible(true);
        Platform.runLater(()->{
            ((Label)actions.getChildren().get(5)).setText("Action: Development grid (1), Market (2), Leader (3), Production (4), End Turn(6)");


        ((Button) actions.getChildren().get(0)).setText("grid");
        ((Button) actions.getChildren().get(1)).setText("market");
        ((Button) actions.getChildren().get(2)).setText("leader");
        ((Button) actions.getChildren().get(3)).setText("Production");
        ((Button) actions.getChildren().get(4)).setText("End Turn");
        ((Button) actions.getChildren().get(0)).setVisible(true);
        ((Button) actions.getChildren().get(1)).setVisible(true);
        ((Button) actions.getChildren().get(2)).setVisible(true);
        ((Button) actions.getChildren().get(3)).setVisible(true);
        ((Button) actions.getChildren().get(4)).setVisible(true);
        ((Button) actions.getChildren().get(0)).setDisable(false);
        ((Button) actions.getChildren().get(1)).setDisable(false);
        ((Button) actions.getChildren().get(2)).setDisable(false);
        ((Button) actions.getChildren().get(3)).setDisable(false);
        ((Button) actions.getChildren().get(4)).setDisable(false);
        actions.setVisible(true);
        actions.setDisable(false);
        ((Button) actions.getChildren().get(0)).setOnAction(e->{actionInput=1;actions.setVisible(false);done=true;});
        ((Button) actions.getChildren().get(1)).setOnAction(e->{actionInput=2;actions.setVisible(false);done=true;});
        ((Button) actions.getChildren().get(2)).setOnAction(e->{actionInput=3;actions.setVisible(false);done=true;});
        ((Button) actions.getChildren().get(3)).setOnAction(e->{actionInput=4;actions.setVisible(false);done=true;});
        ((Button) actions.getChildren().get(4)).setOnAction(e->{actionInput=6;actions.setVisible(false);done=true;}); });
    }

    public void depotAction(PlayerInformation pi)
    {
        done=false;
        resetAction();
        printDepot(pi);
        actions.getChildren().get(5).setVisible(true);
        Platform.runLater(()->{
            ((Label)actions.getChildren().get(5)).setText("Action: Insert (1), Swap (2), Confirm (3)");


        ((Button) actions.getChildren().get(0)).setText("Insert");
        ((Button) actions.getChildren().get(1)).setText("Swap");
        ((Button) actions.getChildren().get(2)).setText("Confirm");
        actions.setVisible(true);
        actions.setDisable(false);
        ((Button) actions.getChildren().get(0)).setOnAction(e->{actionInput=1;actions.setVisible(false);done=true;});
        ((Button) actions.getChildren().get(1)).setOnAction(e->{actionInput=2;actions.setVisible(false);done=true;});
        ((Button) actions.getChildren().get(2)).setOnAction(e->{actionInput=3;actions.setVisible(false);done=true;});});
    }

    public void chooseMarket(Resource[][] m, Resource fm){
        done=false;
        printMarket(m, fm);
        market.setDisable(false);
        market.setVisible(true);
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(12)).setOnMouseClicked(e->{
            System.out.println(1);
            chooseInput=1;
            market.setVisible(false);done=true;});
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(13)).setOnMouseClicked(e->{chooseInput=2;System.out.println(2);
            market.setVisible(false);done=true;});
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(14)).setOnMouseClicked(e->{chooseInput=3;System.out.println(3);
            market.setVisible(false);done=true;});
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(15)).setOnMouseClicked(e->{chooseInput=4;System.out.println(4);
            market.setVisible(false);done=true;});
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(16)).setOnMouseClicked(e->{chooseInput=5;System.out.println(5);
            market.setVisible(false);done=true;});
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(17)).setOnMouseClicked(e->{chooseInput=6;System.out.println(6);
            market.setVisible(false);done=true;});
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(18)).setOnMouseClicked(e->{chooseInput=7;System.out.println(7);
            market.setVisible(false);done=true;});
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(12)).setDisable(false);
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(13)).setDisable(false);
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(14)).setDisable(false);
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(15)).setDisable(false);
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(16)).setDisable(false);
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(17)).setDisable(false);
        ((Button)((GridPane) market.getChildren().get(2)).getChildren().get(18)).setDisable(false);
        ((GridPane) market.getChildren().get(2)).setDisable(false);
    }

    public  void chooseInsert(PlayerInformation pi, ArrayList<Resource> gain){
        done=false;
        resetAction();
        actions.setVisible(true);
        actions.setDisable(false);
        if(gain.isEmpty())
        {
            actions.getChildren().get(5).setVisible(true);
            Platform.runLater(()->{
                ((Label)actions.getChildren().get(5)).setText("You have no resources to insert");
            });

            done=true;
            return;
        }
        printDepot(pi);

        actions.getChildren().get(5).setVisible(true);
        Platform.runLater(()->{
            ((Label)actions.getChildren().get(5)).setText("Choose a gain element"+printGain(gain));
        });


        for(int i=0;i<gain.size();i++){
            cg(i,pi,false);
        }
    }
    public String printGain(ArrayList<Resource> gain) {
        int i = 1;
        String a="";
        for(Resource r: gain)
        {
            a=a+(r+" ("+i+") ");
            i++;
        }
        return a;
    }
    private void cc(PlayerInformation pi){
        resetAction();
        actions.getChildren().get(5).setVisible(true);
        Platform.runLater(()->{
            ((Label)actions.getChildren().get(5)).setText("Choose a row");
        });

        for(int i=0;i<pi.getLeaderDepots().size()+3;i++){
            cg(i,pi,true);
        }
    }

    private void cg(int i,PlayerInformation pi,boolean t){
        ((Button) actions.getChildren().get(i)).setVisible(true);
        ((Button) actions.getChildren().get(i)).setDisable(false);
        ((Button) actions.getChildren().get(i)).setOnAction(e->{
            chooseInput=i+1;
            if(!t){
                cc(pi);
            }else {
                done=true;
            }
        });
    }



    public void chooseSwap(PlayerInformation pi){
        done=false;
        depot.setVisible(true);
        depot.setDisable(false);


        ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e-> {
            chooseInput = 0;
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 1;
                done=true;
                depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 2;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 3;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 4;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 5;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 6;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 7;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 8;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(3)).setOnMouseClicked(e1-> {
                depotInput = 9;
                done=true;depot.setDisable(true);
            });

        });
        ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(0)).setOnMouseClicked(e-> {
            chooseInput = 1;
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 0;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 2;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 3;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 4;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 5;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 6;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 7;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 8;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(3)).setOnMouseClicked(e1-> {
                depotInput = 9;
                done=true;depot.setDisable(true);
            });

        });
        ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(e-> {
            chooseInput = 2;
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 1;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 0;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 3;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 4;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 5;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 6;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 7;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 8;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(3)).setOnMouseClicked(e1-> {
                depotInput = 9;
                done=true;depot.setDisable(true);
            });

        });
        ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(0)).setOnMouseClicked(e-> {
            chooseInput = 3;
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 1;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 2;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 0;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 4;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 5;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 6;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 7;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 8;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(3)).setOnMouseClicked(e1-> {
                depotInput = 9;
                done=true;depot.setDisable(true);
            });

        });
        ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(e-> {
            chooseInput = 4;
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 1;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 2;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 3;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 0;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 5;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 6;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 7;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 8;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(3)).setOnMouseClicked(e1-> {
                depotInput = 9;
                done=true;depot.setDisable(true);
            });

        });
        ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(2)).setOnMouseClicked(e-> {
            chooseInput = 5;
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 1;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 2;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 3;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 4;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 0;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 6;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 7;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 8;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(3)).setOnMouseClicked(e1-> {
                depotInput = 9;
                done=true;depot.setDisable(true);
            });

        });
        ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(0)).setOnMouseClicked(e-> {
            chooseInput = 6;
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 1;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 2;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 3;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 4;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 5;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 0;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 7;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 8;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(3)).setOnMouseClicked(e1-> {
                depotInput = 9;
                done=true;depot.setDisable(true);
            });

        });
        ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(1)).setOnMouseClicked(e-> {
            chooseInput = 7;
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 1;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 2;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 3;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 4;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 5;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 6;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 0;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 8;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(3)).setOnMouseClicked(e1-> {
                depotInput = 9;
                done=true;depot.setDisable(true);
            });

        });
        ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(2)).setOnMouseClicked(e-> {
            chooseInput = 8;
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 1;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 2;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 3;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 4;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 5;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 6;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 7;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 0;
                done=true;depot.setDisable(true);
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(3)).setOnMouseClicked(e1-> {
                depotInput = 9;
                done=true;depot.setDisable(true);
            });


        });
        ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(3)).setOnMouseClicked(e-> {
            chooseInput = 9;
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 1;
                done=true;
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 2;
                done=true;
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 3;
                done=true;
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 4;
                done=true;
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(2)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 5;
                done=true;
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 6;
                done=true;
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(1)).setOnMouseClicked(e1-> {
                depotInput = 7;
                done=true;
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(3)).getChildren().get(2)).setOnMouseClicked(e1-> {
                depotInput = 8;
                done=true;
            });
            ((ImageView) ((HBox) ((VBox) depot.getChildren()).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e1-> {
                depotInput = 0;
                done=true;
            });

        });


    }

    public void chooseDev(ArrayList<DevCard> deck){
        done=false;
        printDevGrid(deck);
        System.out.println("\nChoose a card or come back (0)\n");

        devcards.setDisable(false);
        devcards.setVisible(true);
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(e-> {
            chooseInput = 9;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(1)).setOnMouseClicked(e-> {
            chooseInput = 10;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(2)).setOnMouseClicked(e-> {
            chooseInput = 11;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(3)).setOnMouseClicked(e-> {
            chooseInput = 12;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(4)).setOnMouseClicked(e-> {
            chooseInput = 5;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(5)).setOnMouseClicked(e-> {
            chooseInput = 6;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(6)).setOnMouseClicked(e-> {
            chooseInput = 7;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(7)).setOnMouseClicked(e-> {
            chooseInput = 8;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(8)).setOnMouseClicked(e-> {
            chooseInput = 1;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(9)).setOnMouseClicked(e-> {
            chooseInput = 2;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(10)).setOnMouseClicked(e-> {
            chooseInput = 3;
            done=true;
            devcards.setVisible(false);
        });
        ((ImageView) ((GridPane)devcards.getChildren().get(0)).getChildren().get(11)).setOnMouseClicked(e-> {
            chooseInput = 4;
            done=true;
            devcards.setVisible(false);
        });
    }

    public void chooseLeader(PlayerInformation pi, String cn) { //usato per scegliere il leader da attivare o scartare
        done=false;
        printPlayerLeader(pi,cn);
        System.out.print("\nChoose a leader or come back (0)");
        leader.setVisible(true);
        leader.setDisable(false);
        for(int i=0;i<pi.getLeaderCards().size();i++){
            chooseL(i);
        }
    }
    private void chooseL(int i){
        ((ImageView)((HBox)leader.getChildren().get(0)).getChildren().get(i)).setOnMouseClicked(e-> {
            leaderInput = i + 1;
            done=true;
            leader.setDisable(true);
        });
    }
    public void interactLeader()
    {
        done=false;
        resetAction();
        Platform.runLater(()->{
            ((Label)actions.getChildren().get(5)).setText("Discard (1) or activate (2) the leader");



        ((Button) actions.getChildren().get(0)).setVisible(true);
        ((Button) actions.getChildren().get(1)).setVisible(true);
        ((Button) actions.getChildren().get(0)).setDisable(false);
        ((Button) actions.getChildren().get(1)).setDisable(false);
        ((Button) actions.getChildren().get(0)).setText("Discard");
        ((Button) actions.getChildren().get(1)).setText("Activate");
        actions.setVisible(true);
        actions.setDisable(false);
        ((Button) actions.getChildren().get(0)).setOnAction(e->{chooseInput=1;done=true;resetAction();});
        ((Button) actions.getChildren().get(1)).setOnAction(e->{chooseInput=2;done=true;resetAction();});
        });
    }

    public void marketLeader(int w,PlayerInformation pi){ //usato se si hanno 2 leader mercato, il giocatore deve specificare per ogni bianco
        done=false;
        resetAction();
        exchangeInput = new int[w];
        actions.getChildren().get(5).setVisible(true);
        Platform.runLater(()->{
            ((Label)actions.getChildren().get(5)).setText("\nFor each white resource, choose a leader card: (0) or (1)\n");
        });

        System.out.println("\nFor each white resource, choose a leader card: (0) or (1)\n");
        ml(w);
    }

    private void ml(int w){
        Platform.runLater(()->{


        ((Button) actions.getChildren().get(0)).setVisible(true);
        ((Button) actions.getChildren().get(1)).setVisible(true);
        ((Button) actions.getChildren().get(0)).setDisable(false);
        ((Button) actions.getChildren().get(1)).setDisable(false);
        ((Button) actions.getChildren().get(0)).setText("leader card 0");
        ((Button) actions.getChildren().get(1)).setText("leader card 1");
        actions.setVisible(true);
        actions.setDisable(false);
        ((Button) actions.getChildren().get(0)).setOnAction(e->{exchangeInput[w]=0;
            if(w>0){
                ml(w-1);
            }
            if(w==0){
                done=true;
                resetAction();
            }
        });
        ((Button) actions.getChildren().get(1)).setOnAction(e->{exchangeInput[w]=1;
            if(w>0){
                ml(w-1);
            }
            if(w==0){
                done=true;
                resetAction();
            }
        });});
    }


    public void discountLeader(PlayerInformation pi){ //q è passata dal controller e indica quante carte discount ha attivato il giocatore
        Platform.runLater(()->{
        int q = pi.getLeaderDiscount().size();
        done=false;
        resetAction();
        switch(q)
        {
            case 1: System.out.println("\nDo you want to use the discount? No (0) Yes (1)\n");
                ((Label) actions.getChildren().get(5)).setVisible(true);

                    ((Label)actions.getChildren().get(5)).setText("Do you want to use the discount? No (1) Yes (2)");


                ((Button) actions.getChildren().get(0)).setVisible(true);
                ((Button) actions.getChildren().get(1)).setVisible(true);
                ((Button) actions.getChildren().get(0)).setDisable(false);
                ((Button) actions.getChildren().get(1)).setDisable(false);
                ((Button) actions.getChildren().get(0)).setText("1");
                ((Button) actions.getChildren().get(1)).setText("2");
                actions.setVisible(true);
                actions.setDisable(false);
                ((Button) actions.getChildren().get(0)).setOnAction(e-> {
                    leaderInput = 1;
                    done = true;
                    resetAction();
                });
                ((Button) actions.getChildren().get(1)).setOnAction(e-> {
                    leaderInput = 2;
                    done = true;
                    resetAction();
                });

                break;
            case 2: System.out.println("\nWhich discount do you want to use? None (1) First (2) Second (3) Both (4)\n");
                ((Label) actions.getChildren().get(5)).setVisible(true);
                Platform.runLater(()->{
                    ((Label)actions.getChildren().get(5)).setText("Which discount do you want to use? None (0) First (1) Second (2) Both (3)");
                });

                ((Button) actions.getChildren().get(0)).setVisible(true);
                ((Button) actions.getChildren().get(1)).setVisible(true);
                ((Button) actions.getChildren().get(2)).setVisible(true);
                ((Button) actions.getChildren().get(3)).setVisible(true);
                ((Button) actions.getChildren().get(0)).setDisable(false);
                ((Button) actions.getChildren().get(1)).setDisable(false);
                ((Button) actions.getChildren().get(2)).setDisable(false);
                ((Button) actions.getChildren().get(3)).setDisable(false);
                ((Button) actions.getChildren().get(0)).setText("1");
                ((Button) actions.getChildren().get(1)).setText("2");
                ((Button) actions.getChildren().get(2)).setText("3");
                ((Button) actions.getChildren().get(3)).setText("4");
                actions.setVisible(true);
                actions.setDisable(false);
                ((Button) actions.getChildren().get(0)).setOnAction(e-> {
                    leaderInput = 1;
                    done = true;
                    resetAction();
                });
                ((Button) actions.getChildren().get(1)).setOnAction(e-> {
                    leaderInput = 2;
                    done = true;
                    resetAction();
                });
                ((Button) actions.getChildren().get(2)).setOnAction(e-> {
                    leaderInput = 3;
                    done = true;
                    resetAction();
                });
                ((Button) actions.getChildren().get(3)).setOnAction(e-> {
                    leaderInput = 4;
                    done = true;
                    resetAction();
                });
                break;
            default:
                break;
        }});
    }

    public void chooseAny(int n,  boolean b) //s è "pay" o "take"
    {
        done=false;
        ((Label) actions.getChildren().get(5)).setVisible(true);
        if (b) {
            Platform.runLater(()->{
                ((Label)actions.getChildren().get(5)).setText("Choose "+n+" resource to take: COIN (1), SERVANT (2), SHIELD (3), STONE (4), FAITH (5)");
                ((Label)actions.getChildren().get(5)).setVisible(true);
            });

            System.out.println("\nChoose " + n + " resource to take: COIN (1), SERVANT (2), SHIELD (3), STONE (4), FAITH (5)\n");
        } else {
            Platform.runLater(()->{
                ((Label)actions.getChildren().get(5)).setText("Choose "+n+" resource to Pay: COIN (1), SERVANT (2), SHIELD (3), STONE (4), FAITH (5)");
                ((Label)actions.getChildren().get(5)).setVisible(true);
            });

            System.out.println("\nChoose " + n + " resource to pay: COIN (1), SERVANT (2), SHIELD (3), STONE (4)\n");
        }

        ca(n,n);
    }

    private void ca(int w,int n){
        Platform.runLater(()->{


        ((Button) actions.getChildren().get(0)).setVisible(true);
        ((Button) actions.getChildren().get(1)).setVisible(true);
        ((Button) actions.getChildren().get(2)).setVisible(true);
        ((Button) actions.getChildren().get(3)).setVisible(true);
        ((Button) actions.getChildren().get(4)).setVisible(true);
        ((Button) actions.getChildren().get(0)).setDisable(false);
        ((Button) actions.getChildren().get(1)).setDisable(false);
        ((Button) actions.getChildren().get(2)).setDisable(false);
        ((Button) actions.getChildren().get(3)).setDisable(false);
        ((Button) actions.getChildren().get(4)).setDisable(false);
        ((Button) actions.getChildren().get(0)).setText("1");
        ((Button) actions.getChildren().get(1)).setText("2");
        ((Button) actions.getChildren().get(2)).setText("3");
        ((Button) actions.getChildren().get(3)).setText("4");
        ((Button) actions.getChildren().get(4)).setText("5");
        actions.setVisible(true);
        actions.setDisable(false);
        ((Button) actions.getChildren().get(0)).setOnAction(e-> {
            anyInput[n-w] = 0;
            if(w>1){
                ca(w-1,n);
            }
            else {
                done=true;
                resetAction();
            }
        });
        ((Button) actions.getChildren().get(1)).setOnAction(e-> {
            anyInput[n-w] = 1;
            if(w>1){
                ca(w-1,n);
            }else {
                done=true;
                resetAction();
            }
        });
        ((Button) actions.getChildren().get(2)).setOnAction(e-> {
            anyInput[n-w] = 2;
            if(w>1){
                ca(w-1,n);
            }else {
                done=true;
                resetAction();
            }
        });
        ((Button) actions.getChildren().get(3)).setOnAction(e-> {
            anyInput[n-w] = 3;
            if(w>1){
                ca(w-1,n);
            }else {
                done=true;
                resetAction();
            }
        });
        ((Button) actions.getChildren().get(4)).setOnAction(e-> {
            anyInput[n-w] = 4;
            if(w>1){
                ca(w-1,n);
            }else {
                done=true;
                resetAction();
            }
        });  });
    }
    public void chooseSlot(PlayerInformation pi) {
        done=false;
        printSlot(pi); // printa solo gli slot attivi
        System.out.println("\nChoose slots, 6 to confirm\n");// 0 base 1-3 normale 4-5 speciale 6 ok
        devSlotInput = new int[6];
        for (int j = 0;j<6; j++) {
            devSlotInput[j] = -1;
        }
        cs(0);

    }

    private void cs(int w){
        Platform.runLater(()->{
            actions.setVisible(true);
            actions.setDisable(false);
            ((Label)actions.getChildren().get(5)).setText("Choose slots, 6 to confirm");


        ((Label) actions.getChildren().get(5)).setVisible(true);
        ((ImageView)devslots.getChildren().get(0)).setVisible(true);
        ((ImageView)devslots.getChildren().get(2)).setVisible(true);
        ((ImageView)devslots.getChildren().get(1)).setVisible(true);
        ((ImageView)devslots.getChildren().get(0)).setDisable(false);
        ((ImageView)devslots.getChildren().get(1)).setDisable(false);
        ((ImageView)devslots.getChildren().get(2)).setDisable(false);
        ((Button) actions.getChildren().get(0)).setVisible(true);
        ((Button) actions.getChildren().get(0)).setDisable(false);
        ((Button) actions.getChildren().get(1)).setVisible(true);
        ((Button) actions.getChildren().get(1)).setDisable(false);
        ((Button) actions.getChildren().get(2)).setVisible(true);
        ((Button) actions.getChildren().get(2)).setDisable(false);
        ((Button) actions.getChildren().get(3)).setVisible(true);
        ((Button) actions.getChildren().get(3)).setDisable(false);
        ((Button) actions.getChildren().get(0)).setText("base");
        ((Button) actions.getChildren().get(1)).setText("leader1");
        ((Button) actions.getChildren().get(2)).setText("leader2");
        ((Button) actions.getChildren().get(3)).setText("confirm");
        ((ImageView)devslots.getChildren().get(0)).setOnMouseClicked(e->{
            devSlotInput[1]=1;
            cs(1);
        });
        ((ImageView)devslots.getChildren().get(1)).setOnMouseClicked(e->{
            devSlotInput[2]=2;
            cs(3);
        });
        ((ImageView)devslots.getChildren().get(2)).setOnMouseClicked(e->{
            devSlotInput[3]=3;
            cs(3);
        });
       ((Button) actions.getChildren().get(0)).setOnAction(e->{
           devSlotInput[0]=0;
           cs(0);
       });
        ((Button) actions.getChildren().get(1)).setOnAction(e->{
            devSlotInput[4]=4;
            cs(0);
        });
        ((Button) actions.getChildren().get(2)).setOnAction(e->{
            devSlotInput[5]=5;
            cs(0);
        });
        ((Button) actions.getChildren().get(3)).setOnAction(e->{
            done=true;
            devslots.setDisable(true);
            resetAction();
        }); });
    }


    public void depotTaking(Resource r, String s){
        Platform.runLater(()->{
        done=false;
        resetAction();
        System.out.println("\nIndicate how many "+r+" you want to withdraw from depot\n");
        actions.setVisible(true);
        actions.setDisable(false);
        ((Label) actions.getChildren().get(5)).setVisible(true);

            ((Label)actions.getChildren().get(5)).setText("Indicate how many "+r+" you want to withdraw from"+s+" depot");


        ((TextField) actions.getChildren().get(6)).setVisible(true);
        ((TextField) actions.getChildren().get(6)).setDisable(false);
        ((TextField) actions.getChildren().get(6)).setText("0");
        ((Button) actions.getChildren().get(7)).setVisible(true);
        ((Button) actions.getChildren().get(7)).setDisable(false);
        ((Button) actions.getChildren().get(7)).setText("confirm");
        ((Button) actions.getChildren().get(7)).setOnAction(e->{
            resourcesInput= Integer.parseInt(((TextField) actions.getChildren().get(6)).getText());
            done=true;
            resetAction();
        });
        });
    }

    public void specialDepotTaking(Resource r){
        Platform.runLater(()->{
        resetAction();
        done=false;
        System.out.println("\nIndicate how many "+r+" you want to withdraw from depot\n");
        ((Label) actions.getChildren().get(5)).setVisible(true);

            ((Label)actions.getChildren().get(5)).setText("Indicate how many "+r+" you want to withdraw from special depot");


        ((TextField) actions.getChildren().get(6)).setVisible(true);
        ((TextField) actions.getChildren().get(6)).setDisable(false);
        ((TextField) actions.getChildren().get(6)).setText("0");
        ((Button) actions.getChildren().get(7)).setVisible(true);
        ((Button) actions.getChildren().get(7)).setDisable(false);
        ((Button) actions.getChildren().get(7)).setText("confirm");
        ((Button) actions.getChildren().get(7)).setOnAction(e->{
            resourcesInput= Integer.parseInt(((TextField) actions.getChildren().get(6)).getText());
            done=true;
            resetAction();
        });});
    }

    //metodi che stampano informazioni all'utente. Andranno inserite le informazioni specifiche del giocatore in alcuni metodi
    public void printFaithTrack(HashMap<String, PlayerInformation> hm, boolean[] popeSpace) {
        System.out.println("Faith Track\n");
        for (String nick : hm.keySet())
        {
            System.out.println(nick+": "+hm.get(nick).getPosition()+"/24\n"); //da inserire variabili
        }
        System.out.println("Victory points: 1 (3/24), 2 (6/24), 4 (9/24), 6 (12/24), 9 (15/24), 12 (18/24), 16 (21/24), 20 (24/24)");
        if (popeSpace[0])
            System.out.println("Next : 8/24\n");
        else if(popeSpace[1])
            System.out.println("Next : 16/24\n");
        else if(popeSpace[2])
            System.out.println("Next : 24/24\n");
        else {
            System.out.println("all Pope Space have been activated\n");
        }

        path.setVisible(true);
        path.setDisable(true);
        int pos=hm.get(view.getNickClient()).getPosition();
        ((ImageView)path.getChildren().get(pos)).setImage(new Image(ImageEnum.getUrl("MARKER")));
        for(int i=0;i<24;i++){

            ((ImageView)path.getChildren().get(pos)).setVisible(false);

        }
        ((ImageView)path.getChildren().get(pos)).setVisible(true);
    }

    private void printPlayerBoard(PlayerInformation pi, boolean[] popeSpace, String cn) {
        printDepot(pi);
        printStrongBox(pi);
        printSlot(pi);
        printPlayerLeader(pi,cn);
        printPopeFavor(pi, popeSpace);
        printLeader();
    }

    private void printPlayerLeader(PlayerInformation pi, String cn) {

    }

    private void printPopeFavor(PlayerInformation pi, boolean[] popeSpace) {
        String[] s = new String[3];
        for (int i = 0; i < 3; i++) {
            if (pi.getPopeFavor()[i]) {
                s[i] = "taken";
            } else if (popeSpace[i]) { //in questo caso il popefavore non è posseduto (false), ma il popeSpace è ancora attivabile (true)
                s[i] = "inactive";
            } else {
                s[i] = "missed";
            }
        }
        text.appendText("Pope Favor \n 2 PV (5-8):" + s[0] + ", 3 PV (12-16): " + s[1] + ", 4 PV (19-24): " + s[2] + "\n"); //inserire variabili
    }

    public void printStrongBox(PlayerInformation pi) {
        Platform.runLater((()->{
            System.out.println("\nStrongBox\n Coin: " + pi.getStrongBox().get(Resource.COIN) + "\n Servant:" + pi.getStrongBox().get(Resource.SERVANT) + " \n Shield: " + pi.getStrongBox().get(Resource.SHIELD) + "\n Stone: " + pi.getStrongBox().get(Resource.STONE) + "\n"); //inserire variabili
            String p=view.getNickClient();
            int coin=view.getPlayersInfo().get(p).getStrongBox().get(Resource.COIN);
            ((Label)((GridPane)strongbox.getChildren().get(0)).getChildren().get(4)).setText(String.valueOf(coin));
            int servant=view.getPlayersInfo().get(p).getStrongBox().get(Resource.SERVANT);
            ((Label)((GridPane)strongbox.getChildren().get(0)).getChildren().get(5)).setText(String.valueOf(servant));
            int shield=view.getPlayersInfo().get(p).getStrongBox().get(Resource.SHIELD);
            ((Label)((GridPane)strongbox.getChildren().get(0)).getChildren().get(6)).setText(String.valueOf(shield));
            int stone=view.getPlayersInfo().get(p).getStrongBox().get(Resource.STONE);
            ((Label)((GridPane)strongbox.getChildren().get(0)).getChildren().get(7)).setText(String.valueOf(stone));
            strongbox.setVisible(true);
            strongbox.setDisable(false);
        }));

    }

    public void printDepot (PlayerInformation pi) { //Stampa del deposito normale

        for(int i=0;i<4;i++){
            for(int j=0;j<=i;j++){
                System.out.println(i+" "+j);
                ((ImageView)((HBox)((VBox)depot.getChildren().get(0)).getChildren().get(i)).getChildren().get(j)).setVisible(false);
            }
        }
        ArrayList<ArrayList<Resource>> d= view.getPlayersInfo().get(view.getNickClient()).getDepot();
        System.out.println(d.size());
        for(int i=0;i<d.size();i++){
            for(int j=0;j<d.get(i).size();j++){
                System.out.println("a"+i+" "+j);
                ((ImageView)((HBox)((VBox)depot.getChildren().get(0)).getChildren().get(i)).getChildren().get(j)).setVisible(true);
                ((ImageView)((HBox)((VBox)depot.getChildren().get(0)).getChildren().get(i)).getChildren().get(j)).setImage(new Image(ImageEnum.getUrl(d.get(i).get(j).name().toUpperCase(Locale.ROOT))));
            }
        }


        ArrayList<SpecialDepot> d1=view.getPlayersInfo().get(view.getNickClient()).getLeaderDepots();
        for (int i =0; i<pi.getLeaderDepots().size(); i++)
        {
            for(int j=0;j<d.get(i).size();j++){
                ((ImageView)((HBox)((VBox)depot.getChildren().get(0)).getChildren().get(3)).getChildren().get(2*i+j)).setVisible(true);
                ((ImageView)((HBox)((VBox)depot.getChildren().get(0)).getChildren().get(3)).getChildren().get(2*i+j)).setImage(new Image(ImageEnum.getUrl(d1.get(i).getRow().get(j).name().toUpperCase(Locale.ROOT))));
            }
        }

        depot.setDisable(false);
        depot.setVisible(true);
    }

    //貌似不需要
    public void printDepotSwap (PlayerInformation pi) //Tutte le celle del deposito sono numerate, anche quelle nulle
    {
        int j = 0;
        for (int i = 0; i<3; i++) {
            for (Resource r : pi.getDepot().get(i)) {
                System.out.println(r + " " + "(" + j + ") ");
                j++;
            }
        } //sono numerati anche i null, che sono considerati oggetti di scambio
        j--; //Quando esce dal for sarà 6, invece di 5, quindi va decrementata
        for (int i =0; i<pi.getLeaderDepots().size(); i++) //ogni row ha 2 elementi (considerati anche quelli null)
        {
            for (Resource r : pi.getLeaderDepots().get(i).getRow()) { //j arriva fino a 7 o a 9
                System.out.println(r + " " + "(" + j + ") ");
                j++;
            }
        }
    }


    public void printMarket (Resource[][]market, Resource freeMarble){

        ((ImageView)this.market.getChildren().get(1)).setImage(new Image(ImageEnum.getUrl(freeMarble.name().toUpperCase())));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(market[i][j].name());
                ((ImageView)((GridPane)this.market.getChildren().get(2)).getChildren().get(4*i+j)).setImage(new Image(ImageEnum.getUrl(market[i][j].name().toUpperCase())));
            }
        }
        this.market.setVisible(true);
        this.market.setDisable(false);
    }

    //貌似不需要
    public void printDevCard (DevCard dc){
        text.appendText(dc.getLevel() + " | " + dc.getColor() + " | " + dc.getVictoryPoint() + " | " + dc.getCostList() + " | " + dc.getProductInputList() + " --> " + dc.getProductOutputList());
    }
    //貌似不需要
    public void printPartlyDevCard (DevCard dc){
        text.appendText(dc.getLevel() + " | " + dc.getColor() + " | " + dc.getVictoryPoint());
    }

    public void printDevGrid (ArrayList<DevCard> deck)
    {
        for(int i=0;i<3;i++){
            for (int j=0;j<4;j++){
                if (deck.get(i*4+j) == null) {
                    ((ImageView)((GridPane)devcards.getChildren().get(0)).getChildren().get((2-i)*4+j)).setVisible(false);
                } else {
                    ((ImageView)((GridPane)devcards.getChildren().get(0)).getChildren().get((2-i)*4+j)).setImage(new Image(ImageEnum.getUrl("DEV"+deck.get(i*4+j).getId())));
                    ((ImageView)((GridPane)devcards.getChildren().get(0)).getChildren().get((2-i)*4+j)).setVisible(true);
                    ((ImageView)((GridPane)devcards.getChildren().get(0)).getChildren().get((2-i)*4+j)).setDisable(false);
                }
            }
        }
        devcards.setDisable(false);
    }
    //不需要！
    public void printPartlyLeader(LeaderCard lc) //usato per stampare carte leader altri giocatori
    {
        if (lc.isActive())
        {
            text.appendText("Leader Card "+lc.getType()+" "+lc.getRes()+": "+lc.getVictoryPoint()+" VP");
        }
        else //è inattiva
        {
            text.appendText("Covered Leader Card");
        }
    }

    public void printLeader () {
        for(int i=0;i<2;i++){
            ((ImageView)((HBox)leader.getChildren().get(0)).getChildren().get(i)).setVisible(false);
        }
        ArrayList<LeaderCard> l=view.getPlayersInfo().get(view.getNickClient()).getLeaderCards();
        System.out.println("leadersize"+l.size());
        for(int i=0;i<l.size();i++){
            ((ImageView)((HBox)leader.getChildren().get(0)).getChildren().get(i)).setImage(new Image(ImageEnum.getUrl("LEADER"+l.get(i).getID())));
            ((ImageView)((HBox)leader.getChildren().get(0)).getChildren().get(i)).setVisible(true);
        }
        leader.setVisible(true);
    }

    public void printMarketLeader(PlayerInformation pi){
        for(Resource r : pi.getLeaderMarket())
        {
            text.appendText("Leader Market: "+r+"\n");
        }
    }

    /**metodo che ordina i giocatori in base al punteggio, stampando la classifica. valutare se spostare sul server**/
    public void printRanking (ArrayList<Integer> rank, ArrayList<String> nicks) //nicks: lista ordinata dei giocatori rank: lista dei punteggi associata a lista nicks
    {
        ArrayList<Integer> a = new ArrayList<Integer>(rank); //a: copia di rank che verrà ordinata in base al punteggio
        Collections.sort(a);
        Collections.reverse(a);
        for (Integer i : a) { //per ogni punteggio di a
            int index = rank.indexOf(i); //trova l'indice in rank
            rank.set(index, -1); //il valore di quell'indice diventa -1, per evitare ripetizioni
            text.appendText(nicks.get(index) + ": " + i); //viene stampato indice corrispondente del nome
        }
    }

    private void printSlot (PlayerInformation pi)
    { //stampa il numero di carte sviluppo, slot base, slot normali attivi e slot leader
        ArrayList<DevSlot> s=view.getPlayersInfo().get(view.getNickClient()).getDevSlots();
        for(int i=1;i<s.size()&&i<4;i++){
            if(((CardSlot)s.get(i)).getDevCards().empty()){
                ((ImageView)devslots.getChildren().get(i-1)).setVisible(false);
            }
            else {
                ((ImageView)devslots.getChildren().get(i-1)).setVisible(true);
                ((ImageView)devslots.getChildren().get(i-1)).setImage(new Image(ImageEnum.getUrl("DEV"+((CardSlot)s.get(i)).getDevCards().lastElement().getId())));
            }
        }
        devslots.setDisable(false);
        devslots.setVisible(true);
    }


    public void seeBoards() {

    }

    public void printExpense(HashMap<Resource, Integer> hm) {
        String s="Price: ";

        for (Resource r : hm.keySet())
        {
            s=s+hm.get(r) +" "+ r;
        }
        text.appendText(s);

    }


    public void setView(View view) {
        devSlotInput = new int[6];//indica tutti i possibili slot, anche quelli non posseduti dal giocatore
        anyInput = new int[2];
        initialInput = new int[2];

        this.view=view;
        System.out.println("here");
        view.setMc(this);
        init();
    }

    private void init(){

        ((Label)actions.getChildren().get(5)).setWrapText(true);
        for(int i=0;i<4;i++){
            ((Label)player.getChildren().get(i)).setId("null");
            ((Label)player.getChildren().get(i)).setVisible(false);
        }
        Platform.runLater(()->{
        view.getPlayersInfo().keySet().forEach(e->{
            for(int i=0;i<4;i++){
                if(((Label)player.getChildren().get(i)).getId().equals("null")){
                    ((Label)player.getChildren().get(i)).setText(e);
                    ((Label)player.getChildren().get(i)).setVisible(true);
                    break;
                }
            }
        });
        });
        depot.setVisible(false);
        leader.setVisible(false);
        market.setVisible(false);
    }
    public void printText(String text)
    {
        this.text.appendText("[Server]: "+ text);
    }
}
