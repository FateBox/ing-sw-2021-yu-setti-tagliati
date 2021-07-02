package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.AbilityType;
import it.polimi.ingsw.enumeration.Color;
import it.polimi.ingsw.enumeration.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardTest {
    @Test
    void cardTest()
    {
        LeaderCard lcDepot=new LeaderCard(12, 3, AbilityType.DEPOT, Resource.SHIELD);
        LeaderCard lcDisc=new LeaderCard(0, 2, AbilityType.DISCOUNT, Resource.SHIELD);
        LeaderCard lcProd=new LeaderCard(8, 4, AbilityType.PRODUCTION, Resource.SHIELD);
        LeaderCard lcRes=new LeaderCard(4, 5, AbilityType.RESOURCE, Resource.SHIELD );
        lcDisc.setDisResLeader(Color.BLUE, Color.PURPLE);
        lcRes.setDisResLeader(Color.GREEN, Color.PURPLE);
        lcProd.setDevLeader(Color.YELLOW);
        lcDepot.setDepLeader(Resource.SERVANT);
        Player p1 = new Player("pippo");
        Player p2 = new Player("peppo");
        Player p3 = new Player("nenno");

        ArrayList<ArrayList<Resource>> depot=new ArrayList<>();
        ArrayList<SpecialDepot> sp=new ArrayList<>();
        sp.add(new SpecialDepot(Resource.SERVANT));
        ArrayList<Resource> row1=new ArrayList<>();
        ArrayList<Resource> row2=new ArrayList<>();
        ArrayList<Resource> row3=new ArrayList<>();
        ArrayList<Resource> spRow1=new ArrayList<>();
        ArrayList<Resource> spRow2=new ArrayList<>();

        row3.add(Resource.SERVANT);
        row3.add(Resource.SERVANT);
        row3.add(Resource.SERVANT);
        spRow1.add(Resource.SERVANT);
        depot.add(row1);
        depot.add(row2);
        p2.setDepots(depot);//p2 not enough depot
        depot.add(row3);
        p1.setDepots(depot);//p1 enough depot

        assertEquals(p1.getDepots().get(0).size(),0);
        assertEquals(p1.getDepots().get(1).size(),0);
        assertEquals(p1.getDepots().get(2).size(),3);

        p1.setSpecialDepots(sp);// p1 not enough sp
        assertFalse(lcDepot.isPlayable(p1));
        spRow1.add(Resource.SERVANT);
        sp.get(0).setRow(spRow1);
        p1.setSpecialDepots(sp); //p1 enough
        System.out.println(lcDepot.getResourcesRequirements());

        printDepot(p1);
        assertTrue(lcDepot.isPlayable(p1));
        spRow1.add(Resource.SERVANT);
        sp.get(0).setRow(spRow1);


        spRow1.add(Resource.SERVANT);
        sp.get(0).setRow(spRow1);
        assertFalse(lcDepot.isPlayable(p2));
        p2.setSpecialDepots(sp);
        p2.setDepots(depot);
        assertTrue(lcDepot.isPlayable(p2));


        assertEquals(lcDepot.getID(),12);
        assertEquals(lcDepot.isActive(),false);
        lcDepot.setActive(true);
        assertEquals(lcDepot.isActive(),true);
        assertEquals(lcDepot.getID(),12);
        assertEquals(lcDepot.getType(),AbilityType.DEPOT);
        assertEquals(lcDepot.getVictoryPoint(),3);
        assertEquals(lcDisc.getVictoryPoint(),2);
        assertEquals(lcDepot.getRes(),Resource.SHIELD);
        assertEquals(lcProd.getRes(),Resource.SHIELD);
        assertEquals(lcDepot.getDevColorRequirements().size(),0);
        assertEquals(lcDepot.getResourcesRequirements().size(),5);


        p3.insertStrongBox(Resource.SERVANT);
        p3.insertStrongBox(Resource.SERVANT);
        p3.insertStrongBox(Resource.SERVANT);
        p3.insertStrongBox(Resource.SERVANT);
        assertFalse(lcDepot.isPlayable(p3));
        p3.insertStrongBox(Resource.SERVANT);
        assertTrue(lcDepot.isPlayable(p3));
    }
    private ArrayList<ArrayList<Resource>> depot1()
    {
        ArrayList<ArrayList<Resource>> depot=new ArrayList<>();
        return depot;
    }

    private void printDepot(Player p)
    {
        System.out.println(p.getDepots());
        for (SpecialDepot sp:p.getSpecialDepots())
        {
            System.out.println(sp.getRow());
        }

    }
}