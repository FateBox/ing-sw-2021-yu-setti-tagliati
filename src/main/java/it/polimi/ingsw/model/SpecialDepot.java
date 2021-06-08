package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.util.Stack;

public class SpecialDepot {
    public Resource getRes() {
        return res;
    }

    Resource res;
    Stack<Resource> row;

    public SpecialDepot(Resource resource)
    {
        res=resource;
        row= new Stack<Resource>();
    }

    public void insertResource(Resource resource)
    {
        if (isInsertable(resource))
            row.push(resource);
    }
    public Resource removeResource() throws Exception
    {
        if(isRemovable())
        {
            row.pop();
            return res;
        }
        else
            throw new Exception();
    }
    public boolean isInsertable(Resource resource)
    {
        return (resource==res && row.size() <2);
    }

    public boolean isRemovable()
    {
        return (row.size() > 0);
    }

    public int getQuantity()
    {
        return row.size();
    }
}
