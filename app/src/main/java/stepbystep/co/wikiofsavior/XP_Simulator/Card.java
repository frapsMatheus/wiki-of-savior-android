package stepbystep.co.wikiofsavior.XP_Simulator;

/**
 * Created by Fraps on 15/05/2016.
 */
public class Card {

    public Integer level;
    public Integer baseXP;
    public Integer classXP;
    public Integer minLevel;

    public Card(String row)
    {
        String[] fields = row.split(",");
        level = Integer.parseInt(fields[0]);
        baseXP = Integer.parseInt(fields[1]);
        classXP = Integer.parseInt(fields[2]);
        minLevel = Integer.parseInt(fields[3]);
    }

}
