package it.polimi.ingsw.client;

import java.util.*;

import it.polimi.ingsw.enumeration.AbilityType;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;

/**
 * Handles the CLI of the client application.
 */
public class Cli{

    /**state**/
    private int actionInput; // player action choice
    private int[] initialInput;
    private int chooseInput; // decisions choice
    private int depotInput; // depot cell choice
    private int leaderInput; // leader choice
    private int positionInput; // slot choice for the acquired development card
    private int[] devSlotInput; // slot to activate choice
    private int[] anyInput; // any resource choice (input in quanto variabile immessa dal giocatore, indica anche le risorse prodotte)
    private int resourcesInput; // resources taken from the depot
    private int[] exchangeInput;  // white resources choice
    private Scanner input = new Scanner(System.in); // input variable

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

    /**
     * Allows the player to select two initial leader cards.
     * @param pi Player information register.
     * @param cn Client nickname.
     */
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

    /**
     * Allows the player to select the initial resource.
     * @param nPlayer Player's index in the game.
     */
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

    /**
     * Allows the player to select the position to insert the DevCard.
     */
    public void position() {
        System.out.println("\nChoose a slot to insert the DevCard: 1 | 2 | 3 \n");
        do {
            positionInput = input.nextInt();
        }
        while (positionInput<1 || positionInput>3);
    }

    /**
     * Allows the player to select the operations to perform while waiting for the own turn.
     */
    public void watch()
    {
        System.out.println("\nWatch: Development grid (1), Market (2), Opponent boards (3), End operation (0)\n");
        do {
            actionInput = input.nextInt(); //this input indicates the action that the player would perform
        }
        while (actionInput<0 || actionInput>3);
    }

    /**
     * Allows the player to select the operations to perform when it is the current player of the turn.
     * @param pi Player information register.
     * @param popeSpace Pope space information register: active / not active.
     * @param cn Client nickname.
     * @param m Informations about all the players.
     */
    public void action(PlayerInformation pi, boolean[] popeSpace, String cn, HashMap<String, PlayerInformation> m)
    {
        printPlayerBoard(pi,popeSpace, cn);
        printFaithTrack(m, popeSpace);
        System.out.println("+++++++++++++++++++++++++++++");
        System.out.println("Now it's your turn");
        System.out.println("+++++++++++++++++++++++++++++");
        System.out.println("\nAction: Development grid (1), Market (2), Leader (3), Production (4), Opponent boards (5), End turn (6)\n");
        do {
            actionInput = input.nextInt(); //this input indicates the action that the player would perform
        }
        while (actionInput<1 || actionInput>6);
    }

    /**
     * Submits actions regarding the depot.
     * @param pi Player information register.
     */
    public void depotAction(PlayerInformation pi)
    {
        printDepot(pi);
        System.out.println("\nAction: Insert (1), Swap (2), Confirm (3)\n");
        do {
            actionInput = input.nextInt(); //this input indicates the action that the player would perform
        }
        while (actionInput < 1 || actionInput > 3);
    }

    /**
     * Selects in the market a row or a column where the player would like to put an resource in.
     * @param m Resource market
     * @param fm Resource that the player puts in
     */
    public void chooseMarket(Resource[][] m, Resource fm){
        printMarket(m, fm);
        System.out.println("\nChoose a row or column or come back (0)\n");
        do {
            chooseInput = input.nextInt();
            // this input indicates the chosen row/column of the market (1-7) or return to the main page(0)
        }
        while (chooseInput<0 || chooseInput>7);
    }


    /**
     * Inserts a resource taken from the market in a chosen row or column if available
     * @param pi Player information register.
     * @param gain Resource that the player takes from the market.
     */
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

    /**
     * Shows the resources in <code>gain</code>.
     * @param gain Resource that the player takes from the market.
     */
    public void printGain(ArrayList<Resource> gain) {
        int i = 1;
        for(Resource r: gain)
        {
            System.out.print(r+" ("+i+") ");
            i++;
        }
        System.out.println();
    }

    /**
     * Selects two cells in the depot and swaps their content.
     * @param pi Player information register.
     * @param matrix Player's depot.
     */
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

    /**
     * Prints the depots of the player.
     * @param matrix Player's depot.
     * @param ld Number of rows of Leader depot.
     * @return
     */
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


    /**
     * Chooses the development card to use.
     * @param deck Development cards' deck.
     */
    public void chooseDev(ArrayList<DevCard> deck){
        printDevGrid(deck);
        System.out.println("\nChoose a card or come back (0)\n");
        do{
            chooseInput = input.nextInt();
            // It's the same input of the previous cases, but in this case it indicates the chosen card (1..12) or 0
        }
        while (chooseInput<0 || chooseInput>12);
    }


    /**
     * Chooses the leader card to use.
     * @param pi Player information register.
     * @param cn Client nickname.
     */
    public void chooseLeader(PlayerInformation pi, String cn) {
        // Uses in choosing the leader to activate or discard
        printPlayerLeader(pi,cn);
        System.out.print("\nChoose a leader or come back (0)");
        do{
            leaderInput = input.nextInt(); }
        while (leaderInput<0 || leaderInput>pi.getLeaderCards().size());
    }


    /**
     * Choose the operation to perform on the leader card: discard or activate
     */
    public void interactLeader()
    {
        System.out.println("\nDiscard (1) or activate (2) the leader\n");
        do{
            chooseInput = input.nextInt();
            // the input indicates what to do with the chosen leader card.
        }
        while (chooseInput<1 || chooseInput>2);
    }

    /**
     * Used when there are two market leaders, the player should indicate what to do for every white resource.
     * @param w Number of white resources.
     * @param pi Player information register.
     */
    public void marketLeader(int w, PlayerInformation pi){
        exchangeInput = new int[w];
        printMarketLeader(pi);
        System.out.println("\nFor each white resource, choose a leader card: (1) or (2)\n");
        for (int i = 0; i < w; i++) {
            do{
                exchangeInput[i] = input.nextInt()-1;
                // this array indicates the resources to obtain for each white resource.
            }
            while (exchangeInput[i]<0 || exchangeInput[i]>1);
        }
    }


    /**
     * Allows the player to choose the discounts to use.
     * @param pi Player information register.
     */
    public void discountLeader(PlayerInformation pi){
        // q is passed from the controller and indicates how many discount cards the players has activated.
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

    /**
     * Allows the player to select any kind of resource for each <code>AnyResource</code>.
     * @param n Number of resources to take / pay.
     * @param b Indicates if it's input resource or output resource: true for input and false for output.
     */
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


    /**
     * Chooses the slots to activate.
     * @param pi Player information register.
     */
    public void chooseSlot(PlayerInformation pi) {
        printSlot(pi); // prints only the active slots
        devSlotInput = new int[pi.getDevSlots().size()];
        for (int j = 0;j<devSlotInput.length; j++) {
            devSlotInput[j] = -1;
        }
        boolean check; // checks that a player doesn't input the same slots
        System.out.println("\nChoose slots, 6 to confirm\n");// 0 base 1-3 normal 4-5 special 6 ok
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

    /**
     * Checks if an integer <code>num</code> is contained into an array of integers <code>vet</code>.
     * @param vet Array of integers.
     * @param num Integer to check.
     * @return
     */
    private boolean contains(int[] vet,int num)
    {
        for (int a = 0; a < vet.length; a++) {
            if (num == vet[a]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes the resources from the depot.
     * @param r Type of resource to take.
     * @param s Type of depot.
     */
    public void depotTaking(Resource r, String s){
        System.out.println("\nIndicate how many "+r+" you want to withdraw from"+s+" depot\n");
        resourcesInput = input.nextInt();
        // this input indicates for each resource type how many resources the player want to take from the depot
    }


    public void specialDepotTaking(Resource r){
        System.out.println("\nIndicate how many "+r+" you want to withdraw from special depot\n");
        resourcesInput = input.nextInt(); //questo input indica per ogni risorsa passata, quante risorse il giocatore vuole prelevare dal deposito
    }


    /**
     * Prints the faith track information.
     * @param hm All players' informations.
     * @param popeSpace Pope space status.
     */
    //methods for the user information display. Some of them require specific informations of the player.
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

    /**
     * Prints the player board.
     * @param pi Player information register.
     * @param popeSpace Pope space status.
     * @param cn Client nickname.
     */
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

    /**
     * Prints the leader cards of the player.
     * @param pi Player information register.
     * @param cn Client nickname.
     */
    public void printPlayerLeader(PlayerInformation pi, String cn) {
        int i =0;
        if (pi.getNick().equals(cn)) {
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

    /**
     * Prints the pope favor information.
     * @param pi Player information register.
     * @param popeSpace Pope space status.
     */
    public void printPopeFavor(PlayerInformation pi, boolean[] popeSpace) {
        String[] s = new String[3];
        for (int i = 0; i < 3; i++) {
            if (pi.getPopeFavor()[i]) {
                s[i] = "taken";
            } else if (popeSpace[i]) {
                // in this case the pope favor is not owned (false), but the pope space is still activable (true)
                s[i] = "inactive";
            } else {
                s[i] = "missed";
            }
        }
        System.out.println("\nPope Favor \n 2 PV (5-8):" + s[0] + ", 3 PV (12-16): " + s[1] + ", 4 PV (19-24): " + s[2] + "\n"); //inserire variabili
    }

    /**
     * Prints the strongbox.
     * @param pi Player information register.
     */
    public void printStrongBox(PlayerInformation pi) {
        System.out.println("\nStrongBox\n Coin: " + pi.getStrongBox().get(Resource.COIN) + "\n Servant:" + pi.getStrongBox().get(Resource.SERVANT) + " \n Shield: " + pi.getStrongBox().get(Resource.SHIELD) + "\n Stone: " + pi.getStrongBox().get(Resource.STONE) + "\n"); //inserire variabili
    }

    /**
     * Prints the normal depot.
     * @param pi Player information register.
     */
    public void printDepot (PlayerInformation pi) {
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


    /**
     * Prints the market.
     * @param market Market of resources.
     * @param freeMarble The free resource to put in the market.
     */
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


    /**
     * Prints the development cards.
     * @param dc Development card.
     */
    public void printDevCard (DevCard dc){
        System.out.println(dc.getLevel() + " | " + dc.getColor() + " | " + dc.getVictoryPoint() + " | Cost:  " + dc.getCostList() + " | Production: " + dc.getProductInputList() + " --> " + dc.getProductOutputList());
    }

    /**
     * Prints the development cards for other players in watching.
     * @param dc Development card.
     */
    public void printPartlyDevCard (DevCard dc){
        System.out.println(dc.getLevel() + " | " + dc.getColor() + " | " + dc.getVictoryPoint());
    }

    /**
     * Prints the development grid.
     * @param deck Deck of development cards.
     */
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

    /**
     * Prints the leader cards for other players in watching.
     * @param lc Leader card.
     */
    public void printPartlyLeader(LeaderCard lc) // used to print other players' leader cards
    {
        if (lc.isActive())
        {
            System.out.print("Leader Card "+lc.getType()+" "+lc.getRes()+": "+lc.getVictoryPoint()+" VP");
        }
        else // is inactive
        {
            System.out.println("Covered Leader Card");
        }
    }


    /**
     * Prints the leader cards.
     * @param lc Leader card.
     */
    public void printLeader (LeaderCard lc) {
        System.out.print("Leader Card " +lc.getRes()+" "+lc.getType()+": "+lc.getVictoryPoint()+" VP | ");
        if (lc.isActive())
        {
            System.out.println("Active");
        }
        else // is inactive
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


    /**
     * Prints the leader markets.
     * @param pi Player information register.
     */
    public void printMarketLeader(PlayerInformation pi){
        for(Resource r : pi.getLeaderMarket())
        {
            System.out.println("Leader Market: "+r+"\n");
        }
    }


    /**
     * Prints the players ranking.
     * @param rank Points list of the players.
     * @param nicks Nickname list.
     */
    public void printRanking (ArrayList<Integer> rank, ArrayList<String> nicks)
    // nicks: ordered list of players, rank: list of points associated to nicks
    {
        ArrayList<Integer> a = new ArrayList<Integer>(rank); // a: copy of rank which will be ordered according to the points
        Collections.sort(a);
        Collections.reverse(a);
        for (Integer i : a) { // for each grade in a
            int index = rank.indexOf(i); // finds the index in rank
            rank.set(index, -1); // the value corresponding to the index becomes -1 in order to avoid repetitions
            System.out.println(nicks.get(index) + ": " + i); // prints the index that corresponds to the name
        }
    }

    /**
     * Prints the number of development cards, base slots, active normal slots and leader slots.
     * @param pi Player information register.
     */
    public void printSlot (PlayerInformation pi)
    {
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
                        printDevCard(((CardSlot) dv).getDevCards().peek()); // prints totally the first in peek
                        for (int j = ((CardSlot) dv).getDevCards().size() - 2; j > -1; j--) {
                            // prints partially the other ones
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


    /**
     * Prints the boards of other players.
     * @param mp All players' informations.
     * @param popeSPace Pope space status.
     * @param nickClient Client nickname.
     */
    public void seeBoards(HashMap<String, PlayerInformation> mp, boolean[] popeSPace, String nickClient) {
        for (String nick : mp.keySet())
        {
            if (!nick.equals(nickClient)){
                printPlayerBoard(mp.get(nick), popeSPace, nickClient);
            }
        }
    }

    /**
     * Prints the expense.
     * @param hm HashMap of resources and their corresponding prices.
     */
    public void printExpense(HashMap<Resource, Integer> hm) {
        System.out.print("Price: ");
        for (Resource r : hm.keySet())
        {
            if(hm.get(r)>0)
                System.out.print(" "+hm.get(r) +" "+ r);
        }
        System.out.println("\n");
    }


    /**
     * Asks the server IP address.
     * @return Server IP address.
     */
    public String askIp()
    {
        System.out.println("Server IP address:");
        return input.nextLine();
    }

    /**
     * Asks the server port.
     * @return Server port.
     */
    public String askPort()
    {
        System.out.println("Server Port:");
        return input.nextLine();
    }

    /**
     * Prints the text message from server and waits for a reply.
     * @param text Server text message.
     * @return Reply of the client.
     */
    public String printAndReply(String text)
    {
        System.out.println("[Server]: "+ text);
        return input.nextLine();
    }

    /**
     * Prints the text message from server.
     * @param text Server text message.
     */
    public void printText(String text)
    {
        System.out.println("[Server]: "+ text);
    }
}
