package stepbystep.co.wikiofsavior.Maps;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import stepbystep.co.wikiofsavior.R;

/**
 * Created by fraps on 4/28/16.
 */
public class MapsHeaderCell extends RecyclerView.ViewHolder {

    private TextView mLevel;

    public MapsHeaderCell(View itemView) {
        super(itemView);
        loadChild(itemView);
    }

    private void loadChild(View v){
        mLevel = (TextView)v.findViewById(R.id.map_lvl);
    }

    public void setHeader(Integer lvl){
        mLevel.setText("Level " + String.valueOf(lvl));
    }

    public void setHeader(String name)
    {
        mLevel.setText(name);
    }
}
