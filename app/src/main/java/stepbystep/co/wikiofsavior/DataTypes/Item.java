package stepbystep.co.wikiofsavior.DataTypes;

import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by Fraps on 20/05/2016.
 */
public class Item {

    public enum ItemType {
        Recipe,
        Item
    };

    public String    mName;
    public ParseFile mImage;
    public String   mAmount;
    public ItemType  mType;

    public Item(ParseObject object,ItemType type)
    {
        mType = type;
        switch (type){
            case Recipe:
                mName = object.getString("recipe");
                mImage = object.getParseFile("recipeImage");
                break;
            default:
                mName = object.getString("item");
                mImage = object.getParseFile("itemImage");
                mAmount = object.getString("amount");
        }


    }
}
