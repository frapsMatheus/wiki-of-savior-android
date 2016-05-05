package stepbystep.co.wikiofsavior.Stats;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import stepbystep.co.wikiofsavior.R;


/**
 * Created by fraps on 5/5/16.
 */
public class StatsCell extends RecyclerView.ViewHolder {

    private TextView mResultName;
    private TextView mResultValue;

    public StatsCell(View itemView) {
        super(itemView);
        loadChild(itemView);

    }

    private void loadChild(View v){
        mResultName = (TextView)v.findViewById(R.id.statsResult_name);
        mResultValue = (TextView)v.findViewById(R.id.statsResult_Value);
    }

    public void setCell(Pair<String,String> result){
        mResultName.setText(result.first);
        mResultValue.setText(result.second);
    }

}

