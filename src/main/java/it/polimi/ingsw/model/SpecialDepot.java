package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contain all the information of special depot, they are extra depots gained after use of a corresponding leader, only one king of resource can be stored here.
 */
public class SpecialDepot implements Serializable {


    Resource res;
    ArrayList<Resource> row;

    public ArrayList<Resource> getRow() {

        return row;
    }

    public void setRow(ArrayList<Resource> row) {
        this.row = new ArrayList<>(row);
    }

    /**
     * constructor
     * @param resource resource of reference
     */
    public SpecialDepot(Resource resource)
    {
        res=resource;
        row= new ArrayList<Resource>();
    }

    public Resource getRes() {
        return res;
    }

    public void removeResource(int i)
    {
        if(isRemovable())
        {
            row.remove(i);
        }
    }

    /**
     * Return true if resource is removable
     * @return a boolean
     */
    private boolean isRemovable()
    {
        return (row.size() > 0);
    }

    /**
     * return amount of resource inside this depot
     * @return
     */
    public int getQuantity()
    {
        return row.size();
    }

    /**
     * return true is there's only one kind of resource stored
     * @return
     */
    public boolean isCorrect()
    {
        if (row.size()>2)
            return false;
        for(Resource r:row)
        {
            if (r!=res)
                return false;
        }
        return true;
    }

}
