package stepbystep.co.wikiofsavior.XP_Simulator;

import java.util.ArrayList;

/**
 * Created by Fraps on 15/05/2016.
 */
public class BaseLevel {

    public Integer level;
    public Integer requiredXP;
    public Integer initialXP;

    public BaseLevel(String row)
    {
        String[] fields = row.split(",");
        level = Integer.parseInt(fields[0]);
        requiredXP = Integer.parseInt(fields[1]);
        initialXP = Integer.parseInt(fields[2]);
    }
}
