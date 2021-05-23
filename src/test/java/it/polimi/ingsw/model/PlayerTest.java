package it.polimi.ingsw.model;

import static it.polimi.ingsw.enumeration.Resource.*;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.enumeration.AbilityType;
import it.polimi.ingsw.enumeration.Color;
import it.polimi.ingsw.enumeration.Level;
import it.polimi.ingsw.enumeration.Resource;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    Player testedPlayer;
    public void setUp() {
               testedPlayer = new Player("nickname");
    }

    @Test
    public void testInitialize()
    {
        setUp();

    }
    @Test
    public void testStrongBox () throws Exception
    {
        setUp();
        for(Resource r: Resource.values())
        {
            assertThrows(Exception.class, ()->{testedPlayer.drawStrongBox(r);});
        }

                for (Resource r : Resource.values()) {
                    if( r!= Resource.FAITH && r!= Resource.WHITE && r !=Resource.ANY) {
                        for (int i = 1; i < 10; i++) {
                            testedPlayer.insertStrongBox(r);
                            assertEquals(testedPlayer.getQuantityStrongBox(r), i);
                        }
                        for (int i = 1; i < 10; i++) {
                            testedPlayer.drawStrongBox(r);
                            assertEquals(testedPlayer.getQuantityStrongBox(r), 9 - i);
                        }
                    }
                }
        }

    /**@Test
    public void testDepot() throws Exception {

        setUp();
        testedPlayer.insertDepots(COIN, 1);
        testedPlayer.insertDepots(Resource.STONE, 3);
        // C / - / S
        assertTrue(testedPlayer.isDepotSwappable(1,3));
        testedPlayer.swapDepots(1,3);
        // S / - / C
        assertThrows(Exception.class, ()->{testedPlayer.insertDepots(Resource.STONE,1);});
        assertThrows(Exception.class, ()->{testedPlayer.insertDepots(COIN,1);});
        assertThrows(Exception.class, ()->{testedPlayer.insertDepots(Resource.STONE,3);});
        testedPlayer.insertDepots(COIN, 3);
        // S / - / CC
        testedPlayer.swapDepots(2, 3);
        testedPlayer.swapDepots(3, 2);
        testedPlayer.insertDepots(COIN,3 );
        assertThrows(Exception.class, ()->{testedPlayer.swapDepots(2, 3);});
        assertFalse(testedPlayer.isDepotSwappable(2,3));
        testedPlayer.insertDepots(Resource.SHIELD,2);
        testedPlayer.insertDepots(Resource.SHIELD,2);
        // St / ShSh / CCC
        assertEquals(testedPlayer.removeDepots(2),Resource.SHIELD);
        try{
        assertThrows(Exception.class, ()->{testedPlayer.swapDepots(1,4);});
        assertThrows(Exception.class, ()->{testedPlayer.swapDepots(3,5);});
        testedPlayer.removeDepots(2);
        //
        testedPlayer.removeDepots(3);
        testedPlayer.addSpecialDepot(COIN);
        testedPlayer.insertDepots(COIN, 4);
        testedPlayer.isDepotSwappable(3,4);
        assertThrows(Exception.class, ()->{testedPlayer.insertDepots(SHIELD, 4);});
        testedPlayer.insertDepots(COIN, 4);
        assertFalse(testedPlayer.isDepotSwappable(2,4));
        assertFalse(testedPlayer.isDepotSwappable(1,4));
        testedPlayer.removeDepots(3);
        testedPlayer.removeDepots(3);
        testedPlayer.removeDepots(4);
        //try{
            testedPlayer.insertDepots(SERVANT,3);
            testedPlayer.swapDepots(1,4);
        }
        catch (Exception e) {
        }


    }

    @Test
    public void testDevSlots() throws Exception
    {
        setUp();
        DevCard l1p1DevCard = new DevCard(1, Level.LV1, Color.BLUE,1, COIN);
        l1p1DevCard.setProductInputList(Resource.STONE);
        l1p1DevCard.setProductOutputList(Resource.FAITH);
        DevCard l2p3DevCard = new DevCard(2,Level.LV2,Color.GREEN,3, Resource.SHIELD, COIN);
        l2p3DevCard.setProductOutputList(COIN);
        l2p3DevCard.setProductInputList(Resource.STONE,Resource.STONE);
        DevCard l3p5DevCard = new DevCard(3,Level.LV3,Color.GREEN, 5, COIN, Resource.SERVANT, Resource.STONE);
        l3p5DevCard.setProductInputList(Resource.STONE);
        l3p5DevCard.setProductOutputList(COIN, COIN);
        for(int i=0; i<3; i++)
        {

            DevSlot_Old_old devslot = testedPlayer.getDevSlot(i);
            assertEquals(devslot.getCostList(),null);
            assertEquals(devslot.getProductList(),null);
            assertEquals(devslot.getVictoryPoint(), 0);
            assertThrows(Exception.class, ()->devslot.insertCard(l2p3DevCard));
            assertThrows(Exception.class, ()->devslot.insertCard(l3p5DevCard));
            devslot.insertCard(l1p1DevCard);
            assertEquals(devslot.getVictoryPoint(),1);
            assertThrows(Exception.class, ()->devslot.insertCard(l3p5DevCard));
            devslot.insertCard(l2p3DevCard);
            assertEquals(devslot.getVictoryPoint(),4);
            assertThrows(Exception.class, ()->devslot.insertCard(l1p1DevCard));
            devslot.insertCard(l3p5DevCard);
            assertEquals(devslot.getVictoryPoint(),9);
            assertThrows(Exception.class, ()-> devslot.insertCard(l3p5DevCard));
            assertEquals(devslot.getVictoryPoint(),9);

        }


    }

    @Test
    void testLeaderCard() throws Exception
    {
        setUp();
        LeaderCard depotLeader = new LeaderCard(1,2, AbilityType.DEPOT, COIN)
                .setOwner(testedPlayer)
                .setResourcesRequirements(COIN,5)
                .setResourcesRequirements(Resource.STONE, 3);

        LeaderCard discountLeader = new LeaderCard(2, 3, AbilityType.DISCOUNT, Resource.STONE)
                .setOwner(testedPlayer)
                .setDevRequirements(Color.BLUE, 2);

        LeaderCard productionLeader = new LeaderCard(3, 2, AbilityType.PRODUCTION, Resource.SERVANT);
        productionLeader.setOwner(testedPlayer)
                .setDevRequirements(Color.GREEN)
                .setDevLevelRequirements(Color.GREEN , Level.LV3);

        LeaderCard resourceLeader = new LeaderCard(4, 3, AbilityType.RESOURCE, Resource.SHIELD);
        resourceLeader.setOwner(testedPlayer)
                .setDevRequirements(Color.GREEN, 2)
                .setDevLevelRequirements(Color.PURPLE , Level.LV3);

        testedPlayer.addLeader(resourceLeader)
                .addLeader(productionLeader)
                .addLeader(discountLeader)
                .addLeader(depotLeader);
        for(LeaderCard l : testedPlayer.getLeader())
        {
            assertFalse(l.use(testedPlayer));
        }
        testedPlayer.insertDepots(Resource.SHIELD, 1)
                .insertDepots(Resource.STONE,2)
                .insertDepots(Resource.STONE,2)
                .insertDepots(COIN, 3)
                .insertDepots(COIN, 3)
                .insertDepots(COIN, 3);
                //.insertStrongBox(Resource.SHIELD,Resource.SHIELD,Resource.SERVANT,Resource.SERVANT);
        assertFalse(depotLeader.use(testedPlayer));
        //testedPlayer.insertStrongBox(Resource.STONE, COIN, COIN);
        assertTrue(depotLeader.use(testedPlayer));


        DevCard l1blueDevCard = new DevCard(1, Level.LV1, Color.BLUE,1, COIN);
            l1blueDevCard.setProductInputList(Resource.STONE);
            l1blueDevCard.setProductOutputList(Resource.FAITH);
        DevCard l1purpleDevCard = new DevCard(1, Level.LV1, Color.PURPLE,1, COIN);
            l1purpleDevCard.setProductInputList(Resource.STONE);
            l1purpleDevCard.setProductOutputList(Resource.FAITH);
        DevCard l2greenDevCard = new DevCard(2,Level.LV2,Color.GREEN,3, Resource.SHIELD, COIN);
            l2greenDevCard.setProductOutputList(COIN);
            l2greenDevCard.setProductInputList(Resource.STONE,Resource.STONE);
        DevCard l3greenDevCard = new DevCard(3,Level.LV3,Color.GREEN, 5, COIN, Resource.SERVANT, Resource.STONE);
            l3greenDevCard.setProductInputList(Resource.STONE);
            l3greenDevCard.setProductOutputList(COIN, COIN);
        DevCard l3purpleDevCard = new DevCard(3,Level.LV3,Color.PURPLE, 5, COIN, Resource.SERVANT, Resource.STONE);
            l3purpleDevCard.setProductInputList(Resource.STONE);
            l3purpleDevCard.setProductOutputList(COIN, COIN);
        testedPlayer.removeLeader(depotLeader);
        testedPlayer.getDevSlot(0).insertCard(l1blueDevCard);
        testedPlayer.getDevSlot(0).insertCard(l2greenDevCard);
        testedPlayer.getDevSlot(1).insertCard(l1purpleDevCard);
        for(LeaderCard l : testedPlayer.getLeader())
        {
            assertFalse(l.isPlayable(testedPlayer));
        }
        //discount: blu blu
        //product: green green_3
        //resource: green green purple_3
        //
        testedPlayer.getDevSlot(0).insertCard(l3greenDevCard);
        testedPlayer.getDevSlot(2).insertCard(l1blueDevCard);
        assertTrue(discountLeader.isPlayable());
        assertTrue(productionLeader.isPlayable());
        assertFalse(resourceLeader.isPlayable());
        testedPlayer.getDevSlot(1).insertCard(l2greenDevCard).insertCard(l3purpleDevCard);
        assertTrue(resourceLeader.use(testedPlayer));
        discountLeader.use(testedPlayer);
        productionLeader.use(testedPlayer);
        discountLeader.use(testedPlayer);
        productionLeader.use(testedPlayer);

    }
    **/
}
