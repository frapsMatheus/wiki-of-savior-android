package stepbystep.co.wikiofsavior.DataTypes;

import com.parse.ParseObject;

/**
 * Created by fraps on 4/28/16.
 */
public class Map {
    public String   mName;
    public String   mType;
    public Integer  mLevel;
    public Map(ParseObject object)
    {
        mName = object.getString("Name");
        mType = object.getString("Type");
        mLevel = object.getInt("Lv");
    }
}
