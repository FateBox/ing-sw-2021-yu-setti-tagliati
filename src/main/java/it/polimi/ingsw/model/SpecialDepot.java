package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

import java.util.Stack;

public class SpecialDepot {
    Resource res;
    Stack<Resource> row;
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
        return (resource==res && Quantity <2);
    }
    public boolean isRemovable()
    {
        return (row.size() > 0);
    }
    SpecialDepot(Resource resource)
    {
        res=resource;
        row= new Stack<Resource>();
    }
}
