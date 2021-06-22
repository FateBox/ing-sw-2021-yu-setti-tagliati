package it.polimi.ingsw.client;

import java.util.*;

import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.DevCard;
import it.polimi.ingsw.model.LeaderCard;
public class cli extends view{

        /**state**/

        int actionInput; //scelta azione



    int chooseInput; //scelta elemento azione
        int leaderInput; //scelta leader

    public int getPositionInput() {
        return positionInput;
    }

    public void position() {
        System.out.println("\nChoose a slot to insert the DevCard: 1 | 2 | 3 \n");
        positionInput = input.nextInt();
    }

    int positionInput; //scelta dello slot per la carta sviluppo comprata
        int interactInput; //legato ai leader
        int[] devSlotInput; //scelta slot
        int[] resourcesInput; //risorse prelevate dal deposito
        int[] exchangeInput;  //scelta risorse bianche

        Scanner input = new Scanner(System.in);

        /**methods**/
        public int getAction() {
            return actionInput;
        }

    public int getChooseInput() {
        return chooseInput;
    }

    public int getLeaderInput() {
        return leaderInput;
    }

    public int getInteractInput() {
        return interactInput;
    }

    public int[] getDevSlotInput() {
        return devSlotInput;
    }

    public int[] getResourcesInput() {
        return resourcesInput;
    }

    public int[] getExchangeInput() {
        return exchangeInput;
    }

        //costruttore
        public cli() {
            devSlotInput = new int[6]; //indica tutti i possibili slot, anche quelli non posseduti dal giocatore
            resourcesInput = new int[4]; //indica le 4 risorse, rispettivamente COIN, SERVANT, SHIELD, STONE
        }


        //metodi che prendono in ingresso gli input dell'utente

        public int action()
        {
            System.out.println("\nAction: Development grid (1) Market (2) Leader (3) Production (4) Opponent boards (5)\n");
                return actionInput = input.nextInt(); //questo input indica quale azione l'utente ha intenzione di fare
        }

        public void chooseMarket(){
            printMarket(gc.getMarket(), gc.getFreeMarble());
            System.out.println("\nChoose a row or column or come back (0)\n");
            chooseInput = input.nextInt(); //questo input indica la striscia scelta nel caso di mercato (1..7) o il voler tornare indietro (0)

        }

        public int chooseDev(){
            printDevGrid();
            System.out.println("\nChoose a card or come back (0)\n");
            return chooseInput = input.nextInt();//è lo stesso inoput di prima, ma in questo caso indica la carta scelta dalla griglia (1..12) o 0
        }

        public void chooseLeader() { //usato per scegliere il leader da attivare o scartare
            printLeader();
            System.out.print("\nChoose a leader or come back (0)");
            chooseInput = input.nextInt();
        }

        public void interactLeader()
        {
            System.out.println("\nchoose whether to come back(0), discard (1) or activate (2) the leader\n");
            interactInput = input.nextInt(); //l'input indica cosa si intende fare con la carta leader scelta.
        }

        public void marketLeader(int w){ //usato se si hanno 2 leader mercato, il giocatore deve specificare per ogni bianco
            exchangeInput = new int[w];
            System.out.println("\nFor each white resource, choose a leader card\n");
            for (int i = 0; i < w; i++) {
                exchangeInput[i] = input.nextInt(); //questo array di input indica per ciascun bianco quale risorsa si vuole ottenere
            }
        }

        public int discountLeader(int q){ //q è passata dal controller e indica quante carte discount ha attivato il giocatore
            switch(q)
            {
                case 1: System.out.println("\nDo you want to use the discount? No (0) Yes (1)\n");
                    break;
                case 2: System.out.println("\nWhich discount do you want to use? None (0) First (1) Second (2) Both (3)\n");
                    break;
            }
            return leaderInput = input.nextInt();
        }

        public void chooseSlot(int s) { //s è il numero di tutti gli slot attivi, passato dal controller
            int i, j=0;
            printSlot(gc.getClientP()); // printa solo gli slot attivi
            i = input.nextInt();
            while (j<s && i!=0) {
                System.out.println("\nChoose slots\n");
                devSlotInput[i - 1] = 1; //l'input indica quali di tutti gli slot possibili (base, normali, leader), il giocatore vuole attivare. Lo slot passa da 0 (inattivo) a 1 (attivo)
                j++;                    // per interrompere la richiesta di slot bisogna inserire 0, oppure bisogna attivare un numero di slot pari ad s, che sono quelli effettivamente possibili
                i = input.nextInt();    //il controller guarderà poi che non siano stati attivati slot non posseduti
                //se il giocatore preme direttamente 0, il controller non attiva nessuno slot e torna semplicemente indietro
            }
        }


        public void resourcesPay(Resource r, int i){
            System.out.println("\nIndicate how many "+r+" you want to withdraw from depot\n");
            resourcesInput[i] = input.nextInt(); //questo input indica per ogni risorsa passata, quante risorse il giocatore vuole prelevare dal deposito
        }

        //metodi che stampano informazioni all'utente. Andranno inserite le informazioni specifiche del giocatore in alcuni metodi
        private void printFaithTrack() {
            for (int i=0; i<gc.getPlayerList().size(); i++)
            {
                System.out.println("Faith Track\n : /24 (4 PV)\n : /24 (6 PV)\n : /24 (6 PV)"); //da inserire variabili
            }
            System.out.println("Next : /24\n Victory points: 1 (3/24), 2 (6/24), 4 (9/24), 6 (12/24), 9 (15/24), 12 (18/24), 16 (21/24), 20 (24/24)"); //da inserire variabile
        }

        private void printGameBoard() {
            printDepot();
            printStrongBox(gc.getClientP());
            printSlot(gc.getClientP());
            printPlayerLeader(gc.getClientP());
            printPopeFavor(gc.getClientP());
        }

        private void printPlayerLeader(PlayerClient clientP) {
        }

        private void printPopeFavor(PlayerClient pc) {
            String[] s = new String[3];
            for (int i = 0; i < 3; i++) {
                if (pc.getPopeFavor(i)) {
                    s[i] = "taken";
                } else if (gc.getPope(i)) {
                    s[i] = "inactive";
                } else {
                    s[i] = "missed";
                }
            }
            System.out.println("Pope Favor \n 2 PV (5-8):" + s[0] + ", 3 PV (12-16): " + s[1] + ", 4 PV (19-24): " + s[2] + "\n"); //inserire variabili
        }

        private void printStrongBox(PlayerClient pc) {
            System.out.println("\nStrongBox\n Coin: " + pc.getQuantityStrongBox(0) + "\n Servant:" + pc.getQuantityStrongBox(1) + " \n Shield: " + pc.getQuantityStrongBox(2) + "\n Stone: " + pc.getQuantityStrongBox(3) + "\n"); //inserire variabili
        }

        private void printDepot () {
        }


        public void printMarket (Resource[][]market, Resource freeMarble){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(market[i][j] + "   ");
                }
                System.out.println("< " + (i + 1) + "\n");
            }
            System.out.println("  ^        ^        ^      ^");
            System.out.println("  4        5        6      7");
            System.out.println("Free: " + freeMarble + "\n");
        }


        public void printDevCard (DevCard dc){
            System.out.println(dc.getLevel() + " | " + dc.getColor() + " | " + dc.getVictoryPoint() + " | " + dc.getCostList() + " | " + dc.getProductInputList() + " --> " + dc.getProductOutputList());
        }

        public void printPartlyDevCard (DevCard dc){
            System.out.println(dc.getLevel() + " | " + dc.getColor() + " | " + dc.getVictoryPoint());
        }

        public void printDevGrid ()
        {
            for (int i = 0; i < 12; i++) {
                System.out.print(i + ") ");
                if (gc.getDevCard(i) == null) {
                    System.out.println("empty deck");
                } else {
                    printDevCard(gc.getDevCard(i));
                }
            }
        }


        private void printLeader (LeaderCard lc) {
            System.out.print("Leader Card "+lc.getType()+" "+lc.getRes()+": "+lc.getVictoryPoint()+" VP");
            if (lc.isActive())
            {
                System.out.println("Active");
            }
            else
            {
                System.out.println("Inactive");
                switch (lc.getType())
                case :
                case:
                case:
                case:
            }

        }

        public void printRanking ()
        {
            ArrayList<Integer> a = new ArrayList<Integer>(gc.getRanking());
            Collections.sort(a);
            Collections.reverse(a);
            for (Integer i : a) {
                int index = gc.getRanking().indexOf(i);
                gc.getRanking().set(index, -1);
                System.out.println(gc.getPlayerList().get(index).getNickname() + ": " + i);
            }
        }
        private void printSlot (PlayerClient pc)
        { //stampa il numero di carte sviluppo, slot base, slot normali attivi e slot leader
            int i = 0;
            for (DevSlot dv : pc.getDevSlots()) {
                i++;
                switch (dv.getType()) {
                    case LEADER:
                        System.out.print(i + ") Leader Slot: " + dv.getInputResource() + " --> " + dv.getOutputResource());
                        break;

                    case CARD:
                        System.out.print(i + ") Card Slot: ");
                        printDevCard(dv.getDevCards().get(dv.getDevCards().size() - 1));
                        for (int j = dv.getDevCards().size() - 2; j > -1; j--) {
                            printDevCard(dv.getDevCards().get(j));
                        }
                        break;

                    default:
                        System.out.print(i + ") Basic Slot: Any, Any --> Any");
                        break;

                }
            }
        }


    public void seeBoards() {
    }
}
