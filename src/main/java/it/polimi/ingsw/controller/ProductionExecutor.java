package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;

public class ProductionExecutor implements ActionExecutor {

    /**
     * informazioni richieste
     * scelta di quali slot attivare array di Integer
     * scelta di come pagare le risorse richieste
     * {
     *     per lo base slot, se scelto deve sceglire quali risorse pagare dal deposito
     * }
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

    private Game game;
    public ProductionExecutor(Game game) {
        this.game=game;
    }

    public boolean verifyData()
    {

        return true;
    }

    public void execute()
    {

    }



}
