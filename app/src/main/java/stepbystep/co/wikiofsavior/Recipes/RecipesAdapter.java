package stepbystep.co.wikiofsavior.Recipes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonicartos.superslim.GridSLM;

import java.util.ArrayList;

import stepbystep.co.wikiofsavior.DataTypes.Item;
import stepbystep.co.wikiofsavior.Maps.MapsHeaderCell;
import stepbystep.co.wikiofsavior.R;

/**
 * Created by Fraps on 20/05/2016.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    int HEADER_TYPE = 1;
    int CELL_TYPE = 2;

    private ArrayList<Item> mResultItems;
    private Item mMainItem;
    private Item.ItemType mType;

    public RecipesAdapter(ArrayList<Item> items, Item mainItem, Item.ItemType type)
    {
        mResultItems = items;
        mMainItem = mainItem;
        mType = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType == HEADER_TYPE) {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_header_cell, parent, false);
           MapsHeaderCell cell = new MapsHeaderCell(view);
           return cell;
       } else {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
           ItemCell cell = new ItemCell(view);
           return cell;
       }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final View itemView = holder.itemView;
        final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
        if (holder instanceof MapsHeaderCell) {
            if (mType == Item.ItemType.Recipe) {
                switch (position) {
                    case 0:
                        ((MapsHeaderCell) holder).setHeader("Recipe");
                        break;
                    default:
                        ((MapsHeaderCell) holder).setHeader("Items");
                }
            } else {
                switch (position) {
                    case 0:
                        ((MapsHeaderCell) holder).setHeader("Item");
                        break;
                    default:
                        ((MapsHeaderCell) holder).setHeader("Recipes");
                }
            }
            lp.setFirstPosition(position);
        } else {
            if(position==1) {
                lp.setFirstPosition(0);
                ((ItemCell)holder).setCell(mMainItem,mType);
            } else {
                lp.setFirstPosition(2);
                ((ItemCell)holder).setCell(mResultItems.get(position-3),mType);
            }
        }
        itemView.setLayoutParams(lp);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isPositionHeader(position)) {
            return HEADER_TYPE;
        } else {
            return CELL_TYPE;
        }
    }

    private boolean isPositionHeader(int position)
    {
        switch (position) {
            case 0:
            case 2:
                return true;
            default:
                return false;
        }
    }

    @Override
    public int getItemCount() {
        return (3 + mResultItems.size());
    }
}
