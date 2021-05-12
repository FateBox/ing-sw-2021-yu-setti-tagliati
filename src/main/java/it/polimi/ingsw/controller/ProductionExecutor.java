package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.awt.*;
import java.util.ArrayList;

public class ProductionExecutor extends ActionController {

    /*
    chosen[0] equivale al base slot, chosen[1/2/3] equivalgono ai devslot, chosen[4/5] agli eventuali leader slot
     */
    ArrayList<Integer> chosenSlots;
    Resource[] baseCost;
    Resource baseProduct;
    /*
    chosen[0][0/1/2/3] equivale allo strongbox, chosen[1] al deposito, chosen[2], chosen [3] ai depositi extra
     */
    ArrayList<ArrayList<Resource>> chosenPayment;
    /*
    come chosenPayment
     */
    Player player;
    //= turnController.getPlayerInTurn();

    void validateChosenSlots()
    {
        ArrayList<Resource>resourceRequired;
        //
        //foreach chosenSlots adds resource in resourceRequired
        //and player.ownsResources()
    }
    void validatePayment()
    {
        //depends on
    }
    /**
     * informazioni richieste
     * scelta di quali slot attivare
     * scelta di come pagare le risorse richieste
     * {
     *     per lo base slot, se scelto deve sceglire quali risorse pagare e da quale deposito/strongbox prendere le risorse
     *     per gli altri slot deve scegliere solo come pagare
     * }dove inserire le risorse
     *
     * operazioni da eseguire
     * validare le scelte
     * eseguire le operazioni
     * {
     *     rimuovere le risorse pagate
     *     aggiungere le risorse acquistate e muovere il percorso fede
     * }
     *
     */
    public ProductionExecutor(GameController gameController) {
        super(gameController);
    }

    public void execute()
    {

    }



}
