package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.util.ArrayList;

public class SpecialDepot {


    Resource res;
    ArrayList<Resource> row;

    public ArrayList<Resource> getRow() {

        return row;
    }

    public void setRow(ArrayList<Resource> row) {
        this.row = new ArrayList<>(row);
    }

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

    private boolean isRemovable()
    {
        return (row.size() > 0);
    }

    public int getQuantity()
    {
        return row.size();
    }

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
