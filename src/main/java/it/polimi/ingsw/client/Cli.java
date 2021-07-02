package it.polimi.ingsw.client;

import java.util.*;

import it.polimi.ingsw.enumeration.AbilityType;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;

public class Cli{

    /**state**/
    private int actionInput; //scelta azione giocatore
    private int[] initialInput;
    private int chooseInput; //scelta decisioni
    private int depotInput; //scelta cella del deposito
    private int leaderInput; //scelta leader
    private int positionInput; //scelta dello slot per la carta sviluppo comprata
    private int[] devSlotInput; //scelta slot da attivare
    private int[] anyInput; //scelta risorse any (input in quanto variabile immessa dal giocatore, indica anche le risorse prodotte)
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
    public Cli() {
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
        while (initialInput[1]<1 || initialInput[1]>4 || initialInput[1]==initialInput[0]);
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

    public void action(PlayerInformation pi, boolean[] popeSpace, String cn, HashMap<String, PlayerInformation> m)
    {
        printPlayerBoard(pi,popeSpace, cn);
        printFaithTrack(m, popeSpace);
        System.out.println("+++++++++++++++++++++++++++++");
        System.out.println("Now it's your turn");
        System.out.println("+++++++++++++++++++++++++++++");
        System.out.println("\nAction: Development grid (1), Market (2), Leader (3), Production (4), Opponent boards (5), End turn (6)\n");
        do {
            actionInput = input.nextInt(); //questo input indica quale azione l'utente ha intenzione di fare
        }
        while (actionInput<1 || actionInput>6);
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
        if(gain.isEmpty())
        {
            System.out.println("You have no resources to insert\n");
            return;
        }
        printDepot(pi);
        System.out.println("\nChoose a element to insert\n");
        printGain(gain);
        do {
            chooseInput = input.nextInt();
        }
        while (chooseInput<1 || chooseInput> gain.size());
        System.out.println("\nChoose a row\n");
        do {
            depotInput = input.nextInt();
        }
        while (depotInput<0||depotInput>(pi.getLeaderDepots().size()+3));
    }

    public void printGain(ArrayList<Resource> gain) {
        int i = 1;
        for(Resource r: gain)
        {
            System.out.print(r+" ("+i+") ");
            i++;
        }
        System.out.println();
    }

    public void chooseSwap(PlayerInformation pi,Resource[][] matrix){
        int k = printMatrix(matrix, pi.getLeaderDepots().size());
        System.out.println("\nChoose a cell\n");
        do{
            chooseInput = input.nextInt(); }
        while (chooseInput<0 || chooseInput>k);
        System.out.println("\nChoose another cell\n");
        do{
            depotInput = input.nextInt(); }
        while (chooseInput<0 || chooseInput>k);
    }

    public int printMatrix(Resource[][] matrix, int ld) {
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < i+1; j++)
            {
                System.out.print(matrix[i][j] +" ("+k+") ");
                k++;
            }
            System.out.println();
        }
        for (int i = 0; i < ld; i++) {
            for (int j = 0; j < 2; j++)
            {
                System.out.print(matrix[i+3][j] +" ("+k+") ");
                k++;
            }
            System.out.println();

        }

        return k-1;
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

    public void marketLeader(int w, PlayerInformation pi){ //usato se si hanno 2 leader mercato, il giocatore deve specificare per ogni bianco
        exchangeInput = new int[w];
        printMarketLeader(pi);
        System.out.println("\nFor each white resource, choose a leader card: (1) or (2)\n");
        for (int i = 0; i < w; i++) {
            do{
                exchangeInput[i] = input.nextInt()-1;} //questo array di input indica per ciascun bianco quale risorsa si vuole ottenere
            while (exchangeInput[i]<0 || exchangeInput[i]>1);
        }
    }

    public void discountLeader(PlayerInformation pi){ //q è passata dal controller e indica quante carte discount ha attivato il giocatore
        int q = pi.getLeaderDiscount().size();
        for (Resource r : pi.getLeaderDiscount())
        {
            System.out.println("Leader: -1 "+ r+"\n");
        }
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

    public void chooseAny(int n, boolean b) {
        anyInput = new int[n];
        if (b) {
            System.out.println("\nChoose " + n + " resource to take: COIN (1), SERVANT (2), SHIELD (3), STONE (4), FAITH (5)\n");
        } else {
            System.out.println("\nChoose " + n + " resource to pay: COIN (1), SERVANT (2), SHIELD (3), STONE (4)\n");
        }                for (int i = 0; i < n; i++) {
            do {
                anyInput[i] = input.nextInt();
            }
            while (anyInput[i] < 1 || anyInput[i] > 5);
        }
    }


    public void chooseSlot(PlayerInformation pi) {
        printSlot(pi); // printa solo gli slot attivi
        devSlotInput = new int[pi.getDevSlots().size()];
        for (int j = 0;j<devSlotInput.length; j++) {
            devSlotInput[j] = -1;
        }
        boolean check; //controlla che un giocatore non inserisca nuovamente lo stesso slot
        System.out.println("\nChoose slots, 6 to confirm\n");// 0 base 1-3 normale 4-5 speciale 6 ok
        int num;
        for (int i = 0; i < pi.getDevSlots().size(); i++) {
            do {
                check = false;

                num=input.nextInt();
                if(num==6){
                    return;
                }
                else{
                    if(!contains(devSlotInput,num))
                    {
                        devSlotInput[i]=num;
                    }
                    else
                    {
                        check=true;
                    }
                }
            }
            while (num< 0 || (num > (pi.getDevSlots().size())-1) || check);
            System.out.println("accepted value\n");
        }
    }
    private boolean contains(int[] vet,int num)
    {
        for (int a = 0; a < vet.length; a++) {
            if (num == vet[a]) {
                return true;
            }
        }
        return false;
    }

    public void depotTaking(Resource r, String s){
        System.out.println("\nIndicate how many "+r+" you want to withdraw from"+s+" depot\n");
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
            System.out.println("Next Pope Space: 8/24\n");
        else if(popeSpace[1])
            System.out.println("Next Pope Space: 16/24\n");
        else if(popeSpace[2])
            System.out.println("Next Pope Space: 24/24\n");
        else {
            System.out.println("all Pope Space have been activated\n");
        }
    }

    public void printPlayerBoard(PlayerInformation pi, boolean[] popeSpace, String cn) {

        if (pi.isInkWell())
        {
            System.out.println(pi.getNick()+" (Inkwell)\n");
        }
        else {
            System.out.println(pi.getNick()+"\n");
        }
        printDepot(pi);
        printStrongBox(pi);
        printSlot(pi);
        printPlayerLeader(pi,cn);
        printPopeFavor(pi, popeSpace);
    }

    public void printPlayerLeader(PlayerInformation pi, String cn) {
        int i =0;
        if (pi.getNick().equals(cn)) {
            System.out.println(123+""+pi.getNick());
            for (LeaderCard lc : pi.getLeaderCards()) {
                i++;
                System.out.print(i+")");
                printLeader(lc);
            }
        }
        else{
            for (LeaderCard lc : pi.getLeaderCards()) {
                i++;
                System.out.print(i+")");
                printPartlyLeader(lc);
            }
        }
    }

    public void printPopeFavor(PlayerInformation pi, boolean[] popeSpace) {
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
        System.out.println("\nPope Favor \n 2 PV (5-8):" + s[0] + ", 3 PV (12-16): " + s[1] + ", 4 PV (19-24): " + s[2] + "\n"); //inserire variabili
    }

    public void printStrongBox(PlayerInformation pi) {
        System.out.println("\nStrongBox\n Coin: " + pi.getStrongBox().get(Resource.COIN) + "\n Servant:" + pi.getStrongBox().get(Resource.SERVANT) + " \n Shield: " + pi.getStrongBox().get(Resource.SHIELD) + "\n Stone: " + pi.getStrongBox().get(Resource.STONE) + "\n"); //inserire variabili
    }

    public void printDepot (PlayerInformation pi) { //Stampa del deposito normale
        int j = 1;
        System.out.println("Depot:\n");
        for (int i = 0; i<3; i++) {
            System.out.println(j+")"+pi.getDepot().get(i));
            j++;
        }
        if (pi.getLeaderDepots().size()>0) {
            System.out.println("\nSpecial depot:\n");
            for (int i = 0; i < pi.getLeaderDepots().size(); i++) {
                System.out.println(j + ")" + pi.getLeaderDepots().get(i).getRow() + " (" + pi.getLeaderDepots().get(i).getRes() + ")"); //j può essere 4 o 5
                j++;
            }
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


    public void printMarket (Resource[][] market, Resource freeMarble){
        for (int i = 0; i < 5; i++) {
            if(i<3) {
                for (int j = 0; j < 4; j++) {
                    System.out.format("%1$-10s",market[i][j]);
                }
                System.out.println("< " + (i + 1) + "\n");
            } else if(i==4){
                for(int j=0;j<4;j++) {
                    System.out.format("%1$-10s","  ^  ");
                }
                System.out.println();
            } else {
                for(int j=4;j<8;j++) {
                    System.out.format("%1$-10s","  " + j);
                }
                System.out.println();
            }

        }
        System.out.println("Free: " + freeMarble + "\n");
    }


    public void printDevCard (DevCard dc){
        System.out.println(dc.getLevel() + " | " + dc.getColor() + " | " + dc.getVictoryPoint() + " | Cost:  " + dc.getCostList() + " | Production: " + dc.getProductInputList() + " --> " + dc.getProductOutputList());
    }

    public void printPartlyDevCard (DevCard dc){
        System.out.println(dc.getLevel() + " | " + dc.getColor() + " | " + dc.getVictoryPoint());
    }

    public void printDevGrid (ArrayList<DevCard> deck)
    {
        for (int i = 0; i < 12; i++) {
            System.out.print(i+1 + ") ");
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
        System.out.print("Leader Card " +lc.getRes()+" "+lc.getType()+": "+lc.getVictoryPoint()+" VP | ");
        if (lc.isActive())
        {
            System.out.println("Active");
        }
        else //è inattiva
        {

            System.out.print(" Inactive:");
            if (lc.getType() == AbilityType.DEPOT) {
                System.out.println(" (5 "+lc.getResourcesRequirements().get(0)+" are required)");
            }
            else if (lc.getType() == AbilityType.DISCOUNT) {
                System.out.println(" (a "+lc.getDevColorRequirements().get(0)+" and a "+lc.getDevColorRequirements().get(1)+" DevCards are required)");
            }
            else if (lc.getType() == AbilityType.PRODUCTION) {
                System.out.println(" (a second level "+lc.getDevColorRequirements().get(0)+" is required)");
            }
            else if (lc.getType() == AbilityType.RESOURCE){               //AbilityType.RESOURCE:
                System.out.println(" (two "+lc.getDevColorRequirements().get(0)+" cards and a "+lc.getDevColorRequirements().get(1)+" card are required)");
            }
        }

    }

    public void printMarketLeader(PlayerInformation pi){
        for(Resource r : pi.getLeaderMarket())
        {
            System.out.println("Leader Market: "+r+"\n");
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

    public void printSlot (PlayerInformation pi)
    { //stampa il numero di carte sviluppo, slot base, slot normali attivi e slot leader
        ArrayList<Integer> slot;
        int i = -1;
        for (DevSlot dv : pi.getDevSlots()) {
            i++;
            switch (dv.getType()) {
                case LEADER:
                    System.out.println(i + ") Leader Slot: "+dv.getInputResource() + " --> [ANY, FAITH]" );
                    break;

                case CARD:
                    System.out.print(i + ") Card Slot: ");
                    if(((CardSlot)dv).getDevCards().size()==0)
                    {
                        System.out.println("no card");
                    }
                    else {
                        printDevCard(((CardSlot) dv).getDevCards().peek()); //mi stampa totalmente la prima in cima
                        for (int j = ((CardSlot) dv).getDevCards().size() - 2; j > -1; j--) { //mi stampa parzialmente le altre
                            printPartlyDevCard(((CardSlot) dv).getDevCards().get(j));
                        }
                    }
                    break;

                case BASIC:
                    System.out.println(i + ") Basic Slot: [ANY, ANY] --> [ANY]");
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
            if(hm.get(r)>0)
                System.out.print(" "+hm.get(r) +" "+ r);
        }
        System.out.println("\n");
    }


    public String askIp()
    {
        System.out.println("Server IP address:");
        return input.nextLine();
    }
    public String askPort()
    {
        System.out.println("Server Port:");
        return input.nextLine();
    }
    public String printAndReply(String text)
    {
        System.out.println("[Server]: "+ text);
        return input.nextLine();
    }

    public void printText(String text)
    {
        System.out.println("[Server]: "+ text);
    }
}
