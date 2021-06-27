package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Color;
import it.polimi.ingsw.enumeration.LorenzoType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoCardTest {

    @Test
    void testLorenzo() {
        LorenzoCard lz=new LorenzoCard(LorenzoType.SHUFFLE);
        lz.setType(LorenzoType.DISCARD);
        lz.setColor(Color.YELLOW);
        assertEquals(lz.getType(),LorenzoType.DISCARD);
        assertEquals(lz.getColor(),Color.YELLOW);
    }
}