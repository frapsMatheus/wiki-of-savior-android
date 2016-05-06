package stepbystep.co.wikiofsavior.XP_Simulator;

/**
 * Created by Fraps on 15/05/2016.
 */
public class ClassLevel {

    public Integer level;
    public Integer rank;
    public Integer requiredXP;
    public Integer initialXP;

    public ClassLevel(String row)
    {
        String[] fields = row.split(",");
        rank = Integer.parseInt(fields[0]);
        level = Integer.parseInt(fields[1]);
        requiredXP = Integer.parseInt(fields[2]);
        initialXP = Integer.parseInt(fields[3]);
    }

}
