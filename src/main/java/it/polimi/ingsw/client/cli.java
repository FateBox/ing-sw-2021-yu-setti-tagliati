package it.polimi.ingsw.client;

import java.util.*;

import it.polimi.ingsw.enumeration.AbilityType;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;

public class cli{

        /**state**/
    private int actionInput; //scelta azione giocatore
    private int[] initialInput;
    private int chooseInput; //scelta decisioni
    private int depotInput; //scelta cella del deposito
    private int leaderInput; //scelta leader
    private int positionInput; //scelta dello slot per la carta sviluppo comprata
    private int[] devSlotInput; //scelta slot da attivare
    private int[] anyInput; //scelta risorse any
    private int resourcesInput; //risorse prelevate dal deposito
    private int[] exchangeInput;  //scelta risorse bianche
    private Scanner input = new Scanner(System.in); //variabile ingresso input

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

    public int[] getInitialInput() { return initialInput; }

    public int[] getExchangeInput() {
        return exchangeInput;
    }

    public int getPositionInput() {
        return positionInput;
    }

        //costruttore
        public cli() {
            devSlotInput = new int[6];//indica tutti i possibili slot, anche quelli non posseduti dal giocatore
            anyInput = new int[2];
            initialInput = new int[2];
        }

    public void initialLeader(PlayerInformation pi, String cn, int nPlayer)
    {
        System.out.println("\nReceive 4 leader cards: \n");
        printPlayerLeader(pi, cn);
        System.out.println("\nChoose 2 to keep during the game: (1) (2) (3) (4)\n");
        do {
            initialInput[0] = input.nextInt();
        }
        while (initialInput[0]<1 || initialInput[0]>4);
        do {
            initialInput[1] = input.nextInt();
        }
        while (initialInput[1]<1 || initialInput[1]>4);
    }

    public void initialResource(int nPlayer) {
        switch (nPlayer) {
            case 0:
                initialInput[0] = -1;
                initialInput[1] = -1;
                break;
            case 1:
                System.out.println("\nChoose a resource: Coin(1) Servant(2) Shield(3) Stone(4)\n");
                do {
                    initialInput[0] = input.nextInt();
                }
                while (initialInput[0]<1 || initialInput[0]>4);
                initialInput[1] = -1;
                break;
            default:
                System.out.println("\nChoose 2 resource: Coin(1) Servant(2) Shield(3) Stone(4)\n");
                do {
                    initialInput[0] = input.nextInt();
                }
                while (initialInput[0]<1 || initialInput[0]>4);
                do {
                    initialInput[1] = input.nextInt();
                }
                while (initialInput[1]<1 || initialInput[1]>4);
        }
    }

    public void position() {
        System.out.println("\nChoose a slot to insert the DevCard: 1 | 2 | 3 \n");
        do {
            positionInput = input.nextInt();
        }
        while (positionInput<1 || positionInput>3);
    }
        //metodi che prendono in ingresso gli input dell'utente

        public void watch()
        {
            System.out.println("\nWatch: Development grid (1), Market (2), Opponent boards (3)\n");
            do {
                actionInput = input.nextInt(); //questo input indica quale azione l'utente ha intenzione di fare
            }
            while (actionInput<1 || actionInput>3);
        }

        public void action()
        {
            System.out.println("\nAction: Development grid (1), Market (2), Leader (3), Production (4), Opponent boards (5)\n");
            actionInput = input.nextInt(); //questo input indica quale azione l'utente ha intenzione di fare
            do {
                actionInput = input.nextInt(); //questo input indica quale azione l'utente ha intenzione di fare
            }
            while (actionInput<1 || actionInput>5);
        }

        public void depotAction(PlayerInformation pi)
    {
        printDepot(pi);
        System.out.println("\nAction: Insert (1), Swap (2), Confirm (3)\n");
        do {
            actionInput = input.nextInt(); //questo input indica quale azione l'utente ha intenzione di fare
        }
        while (actionInput < 1 || actionInput > 3);
    }

        public void chooseMarket(Resource[][] m, Resource fm){
            printMarket(m, fm);
            System.out.println("\nChoose a row or column or come back (0)\n");
            do {
                chooseInput = input.nextInt();
            }                       //questo input indica la striscia scelta nel caso di mercato (1..7) o il voler tornare indietro (0)
            while (chooseInput<0 || chooseInput>7);
        }

        public  void chooseInsert(PlayerInformation pi, ArrayList<Resource> gain){
            printGain(gain);
            printDepotInsert(pi);
            System.out.println("\nChoose a gain element\n");
            do {
                chooseInput = input.nextInt();
            }
            while (chooseInput<1 || chooseInput> gain.size());
            System.out.println("\nChoose a row\n");
            do {
                depotInput = input.nextInt();
            }
            while (chooseInput<0||chooseInput>(pi.getLeaderDepots().size()+3));
        }

    private void printGain(ArrayList<Resource> gain) {
            int i = 1;
            for(Resource r: gain)
            {
                System.out.print(r+" ("+i+") ");
                i++;
            }
            System.out.println();
    }

    public void chooseSwap(PlayerInformation pi){
            printDepotSwap(pi);
            System.out.println("\nChoose a cell\n");
            do{
            chooseInput = input.nextInt(); }
            while (chooseInput<0 && chooseInput>9);
            System.out.println("\nChoose another cell\n");
            do{
            depotInput = input.nextInt(); }
            while (chooseInput<0 && chooseInput>9);
        }

        public void chooseDev(ArrayList<DevCard> deck){
            printDevGrid(deck);
            System.out.println("\nChoose a card or come back (0)\n");
            do{
            chooseInput = input.nextInt(); }//è lo stesso inoput di prima, ma in questo caso indica la carta scelta dalla griglia (1..12) o 0
            while (chooseInput<0 || chooseInput>12);
        }

        public void chooseLeader(PlayerInformation pi, String cn) { //usato per scegliere il leader da attivare o scartare
            printPlayerLeader(pi,cn);
            System.out.print("\nChoose a leader or come back (0)");
            do{
            leaderInput = input.nextInt(); }
            while (leaderInput<0 || leaderInput>pi.getLeaderCards().size());
        }

        public void interactLeader()
        {
            System.out.println("\nDiscard (1) or activate (2) the leader\n");
            do{
            chooseInput = input.nextInt();}//l'input indica cosa si intende fare con la carta leader scelta.
            while (chooseInput<1 || chooseInput>2);
        }

        public void marketLeader(int w){ //usato se si hanno 2 leader mercato, il giocatore deve specificare per ogni bianco
            exchangeInput = new int[w];
            System.out.println("\nFor each white resource, choose a leader card: (1) or (2)\n");
            for (int i = 0; i < w; i++) {
                do{
                exchangeInput[i] = input.nextInt()-1;} //questo array di input indica per ciascun bianco quale risorsa si vuole ottenere
                while (exchangeInput[i]<0 || exchangeInput[i]>1);
            }
        }

        public void discountLeader(int q){ //q è passata dal controller e indica quante carte discount ha attivato il giocatore
            switch(q)
            {
                case 1: System.out.println("\nDo you want to use the discount? No (1) Yes (2)\n");
                    do{
                        leaderInput = input.nextInt();}
                    while (leaderInput<1 || leaderInput>2);
                    break;
                case 2: System.out.println("\nWhich discount do you want to use? None (1) First (2) Second (3) Both (4)\n");
                    do{
                        leaderInput = input.nextInt();}
                    while (leaderInput<1 || leaderInput>4);
                    break;
                default:
                    break;
            }

        }

        public void chooseAny(int n)
        {

            if(n == 1)
            {
                System.out.println("\nChoose "+n+" resource to take: COIN (1), SERVANT (2), SHIELD (3), STONE (4), FAITH (5)\n");
                do{
                    anyInput[0] = input.nextInt();}
                while (anyInput[0]<1 || anyInput[0]>5);
                anyInput[1] = -1;
            }
            else
            {
                    System.out.println("\nChoose "+n+" resource to pay: COIN (1), SERVANT (2), SHIELD (3), STONE (4)\n");
                    do{
                    anyInput[0] = input.nextInt();}
                    while (anyInput[0]<1 || anyInput[0]>4);
                    do{
                    anyInput[1] = input.nextInt();}
                    while (anyInput[1]<1 || anyInput[1]>4);
            }
        }


        public void chooseSlot(PlayerInformation pi) {
            printSlot(pi); // printa solo gli slot attivi

            boolean check;
            System.out.println("\nChoose slots, 6 to confirm\n");// 0 base 1-3 normale 4-5 speciale 6 ok

            for (int i = 0; (devSlotInput[i] != 6 && i < 6); i++) {
                do {
                    check = false;
                    devSlotInput[i] = input.nextInt();
                    for (int a = 0; a < i; a++) {
                        if (devSlotInput[i] == devSlotInput[a]) {
                            check = true;
                        }
                    }
                }
                while (devSlotInput[i] < 0 || devSlotInput[i] > (pi.getDevSlots().size() - 1) || check);
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

                System.out.print(" Inactive");
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

        public void printRanking (ArrayList<Integer> rank, ArrayList<String> nicks) //nicks: lista ordinata dei giocatori rank: lista dei punteggi associata a lista nicks
        {
            ArrayList<Integer> a = new ArrayList<Integer>(rank); //a: copia di rank che verrà ordinata in base al punteggio
            Collections.sort(a);
            Collections.reverse(a);
            for (Integer i : a) { //per ogni punteggio di a
                int index = rank.indexOf(i); //trova l'indice in rank
                rank.set(index, -1); //il valore di quell'indice diventa -1, per evitare ripetizioni
                System.out.println(nicks.get(index) + ": " + i); //viene stampato indice corrispondente del nome
            }
        }

        private void printSlot (PlayerInformation pi)
        { //stampa il numero di carte sviluppo, slot base, slot normali attivi e slot leader
            ArrayList<Integer> slot;
            int i = -1;
            for (DevSlot dv : pi.getDevSlots()) {
                i++;
                switch (dv.getType()) {
                    case LEADER:
                        System.out.print(i + ") Leader Slot: any" + " --> any " + dv.getOutputResource());
                        break;

                    case CARD:
                        System.out.print(i + ") Card Slot: ");
                        if(((CardSlot)dv).getDevCards().size()==0)
                        {
                            System.out.print(i + ") no card");
                        }
                        else {
                            printDevCard(((CardSlot) dv).getDevCards().peek()); //mi stampa totalmente la prima in cima
                            for (int j = ((CardSlot) dv).getDevCards().size() - 2; j > -1; j--) { //mi stampa parzialmente le altre
                                printPartlyDevCard(((CardSlot) dv).getDevCards().get(j));
                            }
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


    public void seeBoards(HashMap<String, PlayerInformation> mp, boolean[] popeSPace, String nickClient) {
            for (String nick : mp.keySet())
            {
                if (!nick.equals(nickClient)){
                    printPlayerBoard(mp.get(nick), popeSPace, nickClient);
                }
            }
    }

    public void printExpense(HashMap<Resource, Integer> hm) {
        System.out.print("Price: ");
        for (Resource r : hm.keySet())
        {
            System.out.print(" "+hm.get(r) +" "+ r);
        }
        System.out.println("\n");

    }
}
