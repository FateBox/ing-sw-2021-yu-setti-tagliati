package it.polimi.ingsw.client;

import java.util.*;

import it.polimi.ingsw.enumeration.AbilityType;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;

public class cli{

        /**state**/
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

    public int[] getAnyInput() {
        return anyInput;
    }

    public int getDepotInput() {
        return depotInput;
    }



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

    public int[] getDevSlotInput() {
        return devSlotInput;
    }

    public int getResourcesInput() {
        return resourcesInput;
    }

    public int[] getExchangeInput() {
        return exchangeInput;
    }

        //costruttore
        public cli() {
            devSlotInput = new int[6];//indica tutti i possibili slot, anche quelli non posseduti dal giocatore
            anyInput = new int[2];
        }

    public int getPositionInput() {
        return positionInput;
    }

    public void position() {
        System.out.println("\nChoose a slot to insert the DevCard: 1 | 2 | 3 \n");
        positionInput = input.nextInt();
    }
        //metodi che prendono in ingresso gli input dell'utente

        public void watch()
        {
            System.out.println("\nWatch: Development grid (1), Market (2), Opponent boards (3)\n");
            actionInput = input.nextInt(); //questo input indica quale azione l'utente ha intenzione di fare
        }

        public void action()
        {
            System.out.println("\nAction: Development grid (1), Market (2), Leader (3), Production (4), Opponent boards (5)\n");
            actionInput = input.nextInt(); //questo input indica quale azione l'utente ha intenzione di fare
        }

        public void depotAction(PlayerInformation pi)
    {
        printDepot(pi);
        System.out.println("\nAction: Insert (1), Swap (2), Confirm (3)\n");
        actionInput = input.nextInt(); //questo input indica quale azione l'utente ha intenzione di fare
    }

        public void chooseMarket(Resource[][] m, Resource fm){
            printMarket(m, fm);
            System.out.println("\nChoose a row or column or come back (0)\n");
            chooseInput = input.nextInt(); //questo input indica la striscia scelta nel caso di mercato (1..7) o il voler tornare indietro (0)

        }

        public  void chooseInsert(PlayerInformation pi){
            printDepotInsert(pi);
            System.out.println("\nChoose a gain element\n");
            chooseInput = input.nextInt();
            System.out.println("\nChoose a cell\n");
            depotInput = input.nextInt();
        }

        public void chooseSwap(PlayerInformation pi){
            printDepotSwap(pi);
            System.out.println("\nChoose a cell\n");
            chooseInput = input.nextInt();
            System.out.println("\nChoose another cell\n");
            depotInput = input.nextInt();
        }

        public void chooseDev(ArrayList<DevCard> deck){
            printDevGrid(deck);
            System.out.println("\nChoose a card or come back (0)\n");
            chooseInput = input.nextInt();//è lo stesso inoput di prima, ma in questo caso indica la carta scelta dalla griglia (1..12) o 0
        }

        public void chooseLeader(PlayerInformation pi, String cn) { //usato per scegliere il leader da attivare o scartare
            printPlayerLeader(pi,cn);
            System.out.print("\nChoose a leader or come back (0)");
            leaderInput = input.nextInt();
        }

        public void interactLeader()
        {
            System.out.println("\nDiscard (0) or activate (1) the leader\n");
            chooseInput = input.nextInt(); //l'input indica cosa si intende fare con la carta leader scelta.
        }

        public void marketLeader(int w){ //usato se si hanno 2 leader mercato, il giocatore deve specificare per ogni bianco
            exchangeInput = new int[w];
            System.out.println("\nFor each white resource, choose a leader card: (0) or (1)\n");
            for (int i = 0; i < w; i++) {
                exchangeInput[i] = input.nextInt(); //questo array di input indica per ciascun bianco quale risorsa si vuole ottenere
            }
        }

        public void discountLeader(int q){ //q è passata dal controller e indica quante carte discount ha attivato il giocatore
            switch(q)
            {
                case 1: System.out.println("\nDo you want to use the discount? No (0) Yes (1)\n");
                    break;
                case 2: System.out.println("\nWhich discount do you want to use? None (0) First (1) Second (2) Both (3)\n");
                    break;
                default:
                    break;
            }
            leaderInput = input.nextInt();
        }

        public void chooseAny(int n, String s) //s è "pay" o "take"
        {

            System.out.println("\nChoose "+n+" resource to"+s+": COIN (0), SERVANT (1), SHIELD (2), STONE (3), FAITH (4)\n");
            if(n == 1)
            {
                anyInput[0] = input.nextInt();
                anyInput[1] = -1;
            }
            else
            {
                    anyInput[0] = input.nextInt();
                    anyInput[1] = input.nextInt();;
            }
        }
        public void chooseSlot(PlayerInformation pi) {
            printSlot(pi); // only prints available slots
            int i = 0;
            System.out.println("\nChoose slots, 6 to confirm\n");// 0 base 1-3 normale 4-5 speciale 6 ok
            devSlotInput[i] = input.nextInt();
            while(devSlotInput[i] != 6 && i < 6) {
                i++;
                devSlotInput[i] = input.nextInt();
            }

        }


        public void depotTaking(Resource r){
            System.out.println("\nIndicate how many "+r+" you want to withdraw from depot\n");
            resourcesInput = input.nextInt(); //questo input indica per ogni risorsa passata, quante risorse il giocatore vuole prelevare dal deposito
        }

        public void specialDepotTaking(Resource r){
            System.out.println("\nIndicate how many "+r+" you want to withdraw from special depot\n");
            resourcesInput = input.nextInt(); //questo input indica per ogni risorsa passata, quante risorse il giocatore vuole prelevare dal deposito
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
        }

        private void printPlayerBoard(PlayerInformation pi, boolean[] popeSpace, String cn) {
            printDepot(pi);
            printStrongBox(pi);
            printSlot(pi);
            printPlayerLeader(pi,cn);
            printPopeFavor(pi, popeSpace);
        }

        private void printPlayerLeader(PlayerInformation pi, String cn) {
            if (pi.getNick().equals(cn)) {
                for (LeaderCard lc : pi.getLeaderCards()) {

                    printLeader(lc);
                }
            }
            else{
                for (LeaderCard lc : pi.getLeaderCards()) {
                    printPartlyLeader(lc);
                }
            }
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
            System.out.println("Pope Favor \n 2 PV (5-8):" + s[0] + ", 3 PV (12-16): " + s[1] + ", 4 PV (19-24): " + s[2] + "\n"); //inserire variabili
        }

        public void printStrongBox(PlayerInformation pi) {
            System.out.println("\nStrongBox\n Coin: " + pi.getStrongBox().get(Resource.COIN) + "\n Servant:" + pi.getStrongBox().get(Resource.SERVANT) + " \n Shield: " + pi.getStrongBox().get(Resource.SHIELD) + "\n Stone: " + pi.getStrongBox().get(Resource.STONE) + "\n"); //inserire variabili
        }

        public void printDepot (PlayerInformation pi) { //Stampa del deposito normale
            for (int i = 0; i<3; i++) {
                System.out.println(pi.getDepot().get(i));
            }
            for (int i =0; i<pi.getLeaderDepots().size(); i++)
            {
                System.out.println(pi.getLeaderDepots().get(i).getRow());
            }
        }

    public void printDepotInsert (PlayerInformation pi) { //tutte le righe sono numerate
            int j = 1;
        for (int i = 0; i<3; i++) {
            System.out.println(pi.getDepot().get(i)+" "+"("+j+")");
            j++;
        }
        j--; //Quando esce dal for sarà 4, invece di 3
        for (int i =0; i<pi.getLeaderDepots().size(); i++)
        {
            System.out.println(pi.getLeaderDepots().get(i).getRow()+" "+"("+j+")"); //j può essere 4 o 5
            j++;
        }
    }

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

        public void printDevGrid (ArrayList<DevCard> deck)
        {
            for (int i = 0; i < 12; i++) {
                System.out.print(i + ") ");
                if (deck.get(i) == null) {
                    System.out.println("empty deck");
                } else {
                    printDevCard(deck.get(i));
                }
            }
        }

        public void printPartlyLeader(LeaderCard lc) //usato per stampare carte leader altri giocatori
        {
            if (lc.isActive())
            {
                System.out.print("Leader Card "+lc.getType()+" "+lc.getRes()+": "+lc.getVictoryPoint()+" VP");
            }
            else //è inattiva
            {
                System.out.println("Covered Leader Card");
            }
        }

        public void printLeader (LeaderCard lc) {
            System.out.print("Leader Card "+lc.getType()+" "+lc.getRes()+": "+lc.getVictoryPoint()+" VP");
            if (lc.isActive())
            {
                System.out.println("Active");
            }
            else //è inattiva
            {

                System.out.println("Inactive");
                if (lc.getType() == AbilityType.DEPOT) {
                    System.out.println(" (5 "+lc.getResourcesRequirements()+" are required)");
                }
                else if (lc.getType() == AbilityType.DISCOUNT) {
                    System.out.println(" (a "+lc.getDevColorRequirements().get(0)+" and a"+lc.getDevColorRequirements().get(1)+" DevCards are required)");
                }
                else if (lc.getType() == AbilityType.PRODUCTION) {
                    System.out.println(" (a second level "+lc.getDevColorRequirements().get(0)+" is required)");
                }
                else{               //AbilityType.RESOURCE:
                    System.out.println(" (two "+lc.getDevColorRequirements().get(0)+" cards and a"+lc.getDevColorRequirements().get(1)+" card are required)");
                }
            }

        }

        /**metodo che ordina i giocatori in base al punteggio, stampando la classifica. valutare se spostare sul server**/
        /*public void printRanking ()
        {
            ArrayList<Integer> a = new ArrayList<Integer>(gc.getRanking());
            Collections.sort(a);
            Collections.reverse(a);
            for (Integer i : a) {
                int index = gc.getRanking().indexOf(i);
                gc.getRanking().set(index, -1);
                System.out.println(gc.getPlayerList().get(index).getNickname() + ": " + i);
            }
        }*/

        private void printSlot (PlayerInformation pi)
        { //stampa il numero di carte sviluppo, slot base, slot normali attivi e slot leader
            int i = 0;
            for (DevSlot dv : pi.getDevSlots()) {
                i++;
                switch (dv.getType()) {
                    case LEADER:
                        System.out.print(i + ") Leader Slot: " + dv.getInputResource() + " --> " + dv.getOutputResource());
                        break;

                    case CARD:
                        System.out.print(i + ") Card Slot: ");
                        printDevCard(((CardSlot) dv).getDevCards().get(((CardSlot)dv).getDevCards().size() - 1)); //mi stampa totalmente la prima in cima
                        for (int j = ((CardSlot)dv).getDevCards().size() - 2; j > -1; j--) { //mi stampa parzialmente le altre
                            printDevCard(((CardSlot)dv).getDevCards().get(j));
                        }
                        break;

                    case BASIC:
                        System.out.print(i + ") Basic Slot: Any, Any --> Any");
                        break;
                    default:
                        break;

                }
            }
        }


    public void seeBoards() {

    }

    public void printExpense(HashMap<Resource, Integer> hm) {
        System.out.print("Price: ");
        for (Resource r : hm.keySet())
        {
            System.out.print(hm.get(r) +" "+ r);
        }
        System.out.println("\n");

    }
}
