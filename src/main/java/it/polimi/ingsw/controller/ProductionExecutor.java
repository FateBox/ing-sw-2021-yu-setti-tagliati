package it.polimi.ingsw.controller;

public class ProductionExecutor extends ActionExecutor {

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

    private GameController gameController;
    public ProductionExecutor(GameController gameController) {
        super(gameController);
    }

    public void execute()
    {

    }



}
