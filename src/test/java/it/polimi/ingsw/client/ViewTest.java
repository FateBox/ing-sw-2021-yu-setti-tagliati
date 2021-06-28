package it.polimi.ingsw.client;

import it.polimi.ingsw.connection.Server;
import it.polimi.ingsw.enumeration.Color;
import it.polimi.ingsw.enumeration.Level;
import it.polimi.ingsw.enumeration.Resource;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {
    View v1 = new View();
    View v2 = new View();
    View v3 = new View();
    ArrayList<String> nl = new ArrayList<>(4);

    @Test
    void initializationPlayer()
    {
        nl.add("Carlo");
        nl.add("Fede");
        nl.add("Mario");
        v1.setPlayerView(nl.get(0),nl);
        v2.setPlayerView(nl.get(1),nl);
        v3.setPlayerView(nl.get(2),nl);
        assertSame(v1.getCurrentPlayer(), v1.getNickClient());
        assertSame(v2.getCurrentPlayer(),v1.getCurrentPlayer());
        assertSame(v3.getCurrentPlayer(),v2.getNickList().get(0));
        assertNotNull(v1.getPlayer().get("Fede"));
        assertNull(v2.getPlayer().get("Andrea"));
    }

    @Test
    void NullDepotTest()
    {
        nl.add("Fede");
        v1.setPlayerView("Fede", nl);
        v1.getP().getLeaderDepots().add(new SpecialDepot(Resource.COIN));
        v1.addNullDepot();
        v1.removeNullDepot();
    }


    @Test
    void productionExpense()
    {
        nl.add("Fede");
        v1.setPlayerView("Fede", nl);
        v1.getP().getDevSlots().add(new CardSlot());
        v1.getP().getDevSlots().add(new CardSlot());
        v1.getP().getDevSlots().add(new CardSlot());
        v1.getP().getDevSlots().add(new CardSlot());
        v1.getP().getDevSlots().get(0).getInputResource().add(Resource.SERVANT);
        System.out.println(Collections.frequency(v1.getP().getDevSlots().get(0).getInputResource(), Resource.SERVANT)+" "+"\n");
        v1.getP().getDevSlots().get(1).getInputResource().add(Resource.SERVANT);
        v1.getP().getDevSlots().get(2).getInputResource().add(Resource.STONE);
        v1.getP().getDevSlots().get(3).getInputResource().add(Resource.STONE);
        ArrayList<Resource> any = new ArrayList<>();
        any.add(Resource.SHIELD);
        v1.productionExpense(any);
    }

}