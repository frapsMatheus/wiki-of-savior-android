package stepbystep.co.wikiofsavior.Stats;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import stepbystep.co.wikiofsavior.R;

/**
 * Created by fraps on 5/5/16.
 */
public class StatsRecyclerAdapter extends RecyclerView.Adapter<StatsCell> {

    private ArrayList<Pair<String,String>> mResultsArray;


    public StatsRecyclerAdapter (ArrayList<Pair<String,String>> results) {
        mResultsArray = results;
    }

    @Override
    public StatsCell onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.results_cell, parent, false);
        StatsCell cell = new StatsCell(view);
        return cell;
    }

    @Override
    public void onBindViewHolder(StatsCell holder, int position) {
        holder.setCell(mResultsArray.get(position));
    }

    @Override
    public int getItemCount() {
        return mResultsArray.size();
    }
}
