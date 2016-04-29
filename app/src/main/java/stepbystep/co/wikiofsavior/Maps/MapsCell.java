package stepbystep.co.wikiofsavior.Maps;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import stepbystep.co.wikiofsavior.DataTypes.Map;
import stepbystep.co.wikiofsavior.R;

/**
 * Created by fraps on 4/28/16.
 */
public class MapsCell extends RecyclerView.ViewHolder {

    private TextView mName;
    private TextView mType;

    public MapsCell(View itemView) {
        super(itemView);
        loadChild(itemView);
    }

    private void loadChild(View v){
        mName = (TextView)v.findViewById(R.id.map_name);
        mType = (TextView)v.findViewById(R.id.map_type);
    }

    public void setMap(Map map){
        mName.setText(map.mName);
        mType.setText(map.mType);
    }
}
