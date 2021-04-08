package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceSpace {
    private int[] strongbox;
    private int[][] depots;
    public ResourceSpace(){
        strongbox= new int[]{0, 0, 0, 0};
        depots=new int[3][3];
    }

    private boolean checkDepot(int[][] depots){
        for(int i=1;i<3;i++)
        {
            for(int j=0;j<=i;j++)
            {
                if(depots[i][j]!=depots[i][j+1] && depots[i][j]!=-1 && depots[i][j+1]!=-1)
                {
                    return false;
                }
            }
            if(depots[i][0]==depots[i-1][0])
            {
                return false;
            }
        }
        return true;
    }

}
