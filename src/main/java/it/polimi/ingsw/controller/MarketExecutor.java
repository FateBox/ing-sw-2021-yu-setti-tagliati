package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumeration.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.SpecialDepot;
import it.polimi.ingsw.Util;
import java.util.ArrayList;


/**
 * It handles the operations regarding the market.
 * Required information:
 * 1) Row or column selection (integer 0-6)
 * 2) If there are two market leaders, it needs the input about how the player would like to convert the white resources
 * for each of them.
 * 3) How to insert the resources in the depot and in leader depots, how to place the resources in the depot.
 *
 * Operations to perform:
 * 1) Saves the choices about each white resource's conversion.
 * 2) Checks if the conversion is correct and if the input is correct.
 * 3) If it is, it modifies the market, inserts and assigns 1 faith point.
 * 4) If not, it sends the corresponding error message to the client.
 *
 */
public class MarketExecutor implements ActionExecutor {

    private ArrayList<Resource> gain;
    private Game game;
    public MarketExecutor(Game game) {
        this.game=game;
        gain=new ArrayList<>();
    }

    /*
     * The controller receives for each client message the information required and sufficient to modify the model.
     * The controller checks the correctness of the operation according to the rules.
     * If ok, it performs the operation, otherwise it sends an error message.
     * The controller receives correct inputs (even they are not performable according to rules)
     */




    /**
     * Takes resources from the market and moves forward the faith track if there's a faith resource.
     * Input from 0 to 6.
     */
    public void choiceResource(int input)
    {
        boolean faith=false;
        if (input < 3) { //from 0 to 2
            gain = new ArrayList<Resource>(game.getRow(input)); //input from 0 to 2 for the row
            game.insertRow(input);
        }
        else { //from 3 to 6
            gain = new ArrayList<Resource>(game.getCol(input-3)); //input from 0 to 3 for the column
            game.insertCol(input-3); //from 0 to 3
        }

        for (Resource r : gain)
        {
            if (r == Resource.FAITH)
            {
                game.forwardPlayer(game.getIndexPlayer(game.getCurrentP()), 1);
                faith=true;
            }
        }
        gain.removeIf(resource -> resource.equals(Resource.FAITH) );
        if(input<3)
        {
            game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+ " chose row "+ input);
        }else {
            game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+ " chose column "+ (input-3));
        }
        if (faith)
        game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+" forward by 1 box, current position " + game.getCurrentP().getFaithLocation()+"/24");
    }


    /**
     * Handles the conversion of white resources.
     * @param list List of resources.
     */
    public void manualChange (ArrayList<Resource> list)
    {
        int i=0;
        if(list.size()!=0)
        {
            for (Resource r:gain) {
                if(r==Resource.WHITE)
                {
                    r=list.get(i);
                    i++;
                }
            }
            game.sendLorenzoAnnouncement(game.getCurrentP().getNickname()+ " used leader ability.");
        }
        else
        {
            gain.removeIf(r -> r.equals(Resource.WHITE));
        }
        game.getCurrentP().setGain(gain);
        System.out.println(gain);
    }

    //market 2

    /**
     * Checks the correctness of depots.
     * @param depots Depots containing resources.
     * @param specialDepots Special depots.
     * @return true if the operation is correct, otherwise false.
     */
    public boolean checkDepot (ArrayList<ArrayList<Resource>> depots, ArrayList<SpecialDepot> specialDepots)
    {
        for(ArrayList<Resource> row:depots) {
            if (!Util.isDepotCorrect(depots)) {
                return false;
            }
        }
        for (SpecialDepot s:specialDepots)
        {
            if(!s.isCorrect())
                return false;
        }
        return true;
    }

    /**
     * Checks that everything inside the given array is contained in player.marketDiscount.
     * @param discount ArrayList of resources representing the discount.
     * @return true if it is correct, otherwise false.
     */
    public boolean checkResArray(ArrayList<Resource> discount)
    {
        for(Resource res: discount)
        {
            if (!game.getCurrentP().getMarketDiscounts().contains(res))
                return false;
        }
        return true;
    }
}
