package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    ArrayList<Player> pg = new ArrayList<Player>(4);

    @Test
    void drawDevCard() {
        pg.add(new Player("nick2"));
        pg.add(new Player("nick1"));
        Game g = new Game(pg);

        for (int j = 0; j<4; j++) {
            for (int i = 0; i < 12; i++) {
                assertNotNull(g.drawDevCard(i));
            }
        }
        for (int i = 0; i < 12; i++) {
            assertNull(g.drawDevCard(i));
        }

    }

    @Test
    void insertRow() {
        pg.add(new Player("nick3"));
        pg.add(new Player("nick4"));
        Game g = new Game(pg);

        for (int j = 0; j<3; j++)
        {
            Resource res = g.getFreeMarble();
            ArrayList<Resource> r = new ArrayList<Resource>(g.getRow(j));
            g.insertRow(j);
            assertSame(r.get(0), g.getFreeMarble());
            for (int i = 1; i<3; i++) {
                assertSame(r.get(i), g.getRow(j).get(i - 1));
            }
            assertSame(res, g.getRow(j).get(3));
        }

    }

    @Test
    void insertCol() {
        pg.add(new Player("nick1"));
        pg.add(new Player("nick2"));
        Game g = new Game(pg);
        for (int j = 0; j<4; j++)
        {
            Resource res = g.getFreeMarble();
        ArrayList<Resource> r = new ArrayList<Resource>(g.getCol(j));
        g.insertCol(j);
        assertSame(r.get(0), g.getFreeMarble());
        for (int i = 1; i<2; i++) {
            assertSame(r.get(i), g.getCol(j).get(i - 1));
        }
            assertSame(res, g.getCol(j).get(2));
        }
    }

    @Test
    void getResources() {
        pg.add(new Player("nick3"));
        pg.add(new Player("nick4"));
        Game g = new Game(pg);

        ArrayList<Resource> r = g.getResources(Resource.WHITE,g.getRow(2));
        for (int i = 0; i<r.size(); i++)
        {
            assertNotEquals(Resource.WHITE ,r.get(i));
        }
    }

    @Test
    void playerPosition() {
        pg.add(new Player("nick1"));
        pg.add(new Player("nick3"));
        pg.add(new Player("nick2"));
        pg.add(new Player("nick4"));
        Game g = new Game(pg);

        assertEquals(0, g.getPositionPlayer(pg.get(0)));
        assertEquals(0, g.getPositionPlayer(pg.get(1)));
        assertEquals(1, g.getPositionPlayer(pg.get(2)));
        assertEquals(1, g.getPositionPlayer(pg.get(3)));

    }

    @Test
    void playerCurrent() {
        pg.add(new Player("nick1"));
        pg.add(new Player("nick3"));
        pg.add(new Player("nick2"));
        pg.add(new Player("nick4"));
        Game g = new Game(pg);
        for(int i = 0; i<4; i++)
        {
            assertEquals(pg.get(i), g.getCurrentP());
            g.nextPlayer();
        }
        assertEquals(pg.get(0), g.getCurrentP());
    }

    @Test
    void forwardPlayer() {
        pg.add(new Player("nick1"));
        pg.add(new Player("nick2"));
        pg.add(new Player("nick3"));
        Game g = new Game(pg);
        assertEquals(true ,g.getPope(0));
        g.forwardPlayer(pg.get(0),3);
        g.forwardPlayer(pg.get(2),5);
        assertEquals(3, g.getPositionPlayer(pg.get(0)));
        assertEquals(6, g.getPositionPlayer(pg.get(2)));
        assertEquals(false, g.getPope(0));
        g.forwardOtherPlayers(pg.get(0),3);
        assertEquals(3, g.getPositionPlayer(pg.get(0)));
        assertEquals(3, g.getPositionPlayer(pg.get(1)));
        assertEquals(9, g.getPositionPlayer(pg.get(2)));

    }


    @Test
    void drawLeaderCard() {
        pg.add(new Player("nick4"));
        Game g = new Game(pg);
        for (int i = 0; i<16; i++)
        {
            assertNotNull(g.drawLeaderCard());
        }
        assertNull(g.drawLeaderCard());
    }
}