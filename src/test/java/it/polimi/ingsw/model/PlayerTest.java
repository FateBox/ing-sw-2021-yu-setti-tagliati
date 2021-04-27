package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
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
        for(Resource r: Resource.values())
        assertEquals(testedPlayer.getResourceQuantity(r),0);

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
                    if( r!= Resource.FAITH && r!= Resource.WHITE)
                    for (int i = 0; i < 10; i++) {
                        testedPlayer.insertStrongBox(r);
                        assertEquals(testedPlayer.getResourceQuantity(r), i);
                    }
                    for(int i=0; i<10; i++) {
                        testedPlayer.drawStrongBox(r);
                        assertEquals(testedPlayer.getResourceQuantity(r), i);
                    }
                }
        }

        //debugging test
    @Test
    public void testDepot() throws Exception {
        setUp();
        testedPlayer.insertDepots(Resource.COIN, 1);
        testedPlayer.insertDepots(Resource.STONE, 3);
        // C / - / S
        assertTrue(testedPlayer.isDepotSwappable(1,3));
        testedPlayer.swapDepots(1,3);
        // S / - / C
        assertThrows(Exception.class, ()->{testedPlayer.insertDepots(Resource.STONE,1);});
        assertThrows(Exception.class, ()->{testedPlayer.insertDepots(Resource.COIN,1);});
        assertThrows(Exception.class, ()->{testedPlayer.insertDepots(Resource.STONE,3);});
        testedPlayer.insertDepots(Resource.COIN, 3);
        // S / - / CC
        testedPlayer.swapDepots(2, 3);
        testedPlayer.swapDepots(3, 2);
        testedPlayer.insertDepots(Resource.COIN,3 );
        assertThrows(Exception.class, ()->{testedPlayer.swapDepots(2, 3);});
        assertFalse(testedPlayer.isDepotSwappable(2,3));
        testedPlayer.insertDepots(Resource.SHIELD,2);
        testedPlayer.insertDepots(Resource.SHIELD,2);
        // St / ShSh / CCC
        assertEquals(testedPlayer.removeDepots(2),Resource.SHIELD);
        testedPlayer.removeDepots(2);
        //
    }



}
