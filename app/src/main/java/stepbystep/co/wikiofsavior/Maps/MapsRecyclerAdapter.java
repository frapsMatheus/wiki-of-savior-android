package stepbystep.co.wikiofsavior.Maps;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonicartos.superslim.GridSLM;
import java.util.LinkedHashMap;

import stepbystep.co.wikiofsavior.DataTypes.Map;
import stepbystep.co.wikiofsavior.R;

/**
 * Created by fraps on 4/28/16.
 */
public class MapsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int HEADER_TYPE = 1;
    int CELL_TYPE = 2;

    LinkedHashMap<Integer,Map> mMapsDict;
    LinkedHashMap<Integer,Integer> mLevelsHash;
    LinkedHashMap<Integer,Integer> mPositionsHash;
    Activity mContext;

    public MapsRecyclerAdapter(LinkedHashMap<Integer,Map> mapsDict,LinkedHashMap<Integer,Integer> levels,LinkedHashMap<Integer,Integer> positionsHash, Activity context)
    {
        mLevelsHash = levels;
        mPositionsHash = positionsHash;
        mMapsDict = mapsDict;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == CELL_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_cell, parent, false);
            MapsCell cell = new MapsCell(view);
            return cell;
        } else if (viewType == HEADER_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_header_cell, parent, false);
            MapsHeaderCell cell = new MapsHeaderCell(view);
            return cell;
        }
        return null;
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
        return mLevelsHash.containsKey(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        final View itemView = holder.itemView;
        final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
//        TODO: Fix stick header
        if (holder instanceof MapsHeaderCell) {
            ((MapsHeaderCell) holder).setHeader(mLevelsHash.get(position));
            lp.setFirstPosition(position);
        } else if (holder instanceof MapsCell) {
            Map map = mMapsDict.get(position);
            ((MapsCell)holder).setMap(map);
            lp.setFirstPosition(mPositionsHash.get(map.mLevel));
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
//                TODO: Show image on click
                }
            });
        }
        itemView.setLayoutParams(lp);
    }

    @Override
    public int getItemCount()
    {
        return mMapsDict.size() + mLevelsHash.size();
    }
}
