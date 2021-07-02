package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * It contains some generic methods which can be useful in multiple instances.
 */
public class Util {

    /*
    public static void method()
    {
        System.out.println("funziona");
    }
     */


    /**
     * Checks if the elements in the depot are placed in a correct way.
     * @param depot Resources depot
     * @return true if it's correct, false otherwise.
     */
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
        ArrayList<Resource> temp=new ArrayList<>();
        //check first element of each row.
        for(ArrayList<Resource> row: depot)
        {
            if(!row.isEmpty())
            {
                if (temp.isEmpty())
                {
                    temp.add(row.get(0));
                }else if(temp.contains(row.get(0)))
                {
                    return false;

                }
                else
                {
                    temp.add(row.get(0));
                }
            }
        }


        return true;
    }

    /**
     * Initializes the payment operation.
     * @return HashMap of resources and their corresponding prices.
     */
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
