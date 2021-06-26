package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class Util {

    public static void method()
    {
        System.out.println("funziona");
    }

    public static boolean isDepotCorrect(ArrayList<ArrayList<Resource>> depot)
    {
        for (ArrayList<Resource> row: depot)
        {
            if(!(row.isEmpty()))
            {
                Resource temp=row.get(0);
                for(Resource resource: row)
                {
                    if (temp!=resource)
                        return false;
                }
            }
        }

        if(depot.get(0).get(0)==depot.get(1).get(0) || depot.get(0).get(0)==depot.get(2).get(0) || depot.get(1).get(0)==depot.get(2).get(0))
        {
            return false;
        }


        return true;
    }

    public static HashMap<Resource,Integer> createEmptyPaymentHash()
    {
        HashMap<Resource,Integer> temp=new HashMap<>();
        temp.put(Resource.COIN,0);
        temp.put(Resource.SERVANT,0);
        temp.put(Resource.SHIELD,0);
        temp.put(Resource.STONE,0);
        return temp;
    }

}
