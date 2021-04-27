package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.Resource;

public class SpecialDepot {
    Resource res;
    int Quantity;
    public void insertResource(Resource resource)
    {
        if (isInsertable(resource))
            Quantity++;
    }
    public Resource removeResource() throws Exception
    {
        if(isRemovable())
        {
            Quantity--;
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
        return (Quantity > 0);
    }
    SpecialDepot(Resource resource)
    {
        res=resource;
        Quantity=0;
    }
}
