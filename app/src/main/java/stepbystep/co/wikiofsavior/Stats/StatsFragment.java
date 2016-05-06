package stepbystep.co.wikiofsavior.Stats;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.revmob.RevMob;

import java.util.ArrayList;

import stepbystep.co.wikiofsavior.MainActivity;
import stepbystep.co.wikiofsavior.R;
import stepbystep.co.wikiofsavior.WikiOfSaviorAPP;


/**
 * Created by fraps on 5/5/16.
 */
public class StatsFragment extends Fragment {

    private EditText mLevelField;
    private EditText mSTRField;
    private EditText mCONField;
    private EditText mINTField;
    private EditText mSPRField;
    private EditText mDEXField;
    private Spinner  mClassField;
    private Button mCalculate;
    private StatsRecyclerAdapter mAdapter;

    private RecyclerView mResultsView;
    private ArrayList<Pair<String,String>> mResults;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_stats, container, false);

//      Prepare fields
        mLevelField = (EditText)v.findViewById(R.id.stats_Level);
        mSTRField = (EditText)v.findViewById(R.id.stats_STR);
        mCONField = (EditText)v.findViewById(R.id.stats_CON);
        mINTField = (EditText)v.findViewById(R.id.stats_INT);
        mSPRField = (EditText)v.findViewById(R.id.stats_SPR);
        mDEXField = (EditText)v.findViewById(R.id.stats_AGI);
        mClassField = (Spinner)v.findViewById(R.id.stats_Class);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        mResultsView = (RecyclerView)v.findViewById(R.id.stats_resultView);
        mResultsView.setLayoutManager(layout);
        mCalculate = (Button)v.findViewById(R.id.stats_Calculate);
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WikiOfSaviorAPP app = (WikiOfSaviorAPP)getActivity().getApplication();
                app.adManager.showAd();
                mResults = calculateStats();
                if (mResults != null) {
                    mAdapter = new StatsRecyclerAdapter(mResults);
                    mResultsView.setAdapter(mAdapter);
                }
            }
        });
        return v;
    }

    private ArrayList<Pair<String,String>> calculateStats(){

        int level = 0;
        int str = 0;
        int con = 0;
        int inteligence = 0;
        int spr = 0;
        int dex = 0;
        try{
            level = Integer.valueOf(mLevelField.getText().toString());
        }catch (NumberFormatException e){
            showError("Level field must a number higher then 0");
            return null;
        }
        try {
            str = Integer.valueOf(mSTRField.getText().toString());
        } catch (NumberFormatException e) {
            showError("STR field must a number higher then 0");
            return null;
        }
        try {
            con = Integer.valueOf(mCONField.getText().toString());
        } catch (NumberFormatException e) {
            showError("CON field must a number higher then 0");
            return null;
        }
        try {
            inteligence = Integer.valueOf(mINTField.getText().toString());
        } catch (NumberFormatException e) {
            showError("INT field must a number higher then 0");
            return null;
        }
        try {
            spr = Integer.valueOf(mSPRField.getText().toString());
        } catch (NumberFormatException e) {
            showError("SPR field must a number higher then 0");
            return null;
        }
        try {
            dex = Integer.valueOf(mDEXField.getText().toString());
        } catch (NumberFormatException e) {
            showError("DEX field must a number higher then 0");
            return null;
        }
        String classType = String.valueOf(mClassField.getSelectedItem());
        StatsCalculation calculation = new StatsCalculation(str,con,inteligence,spr,dex,level,classType);
        return calculation.getArrayOfResults();
    }

    private void showError(String error)
    {
        new AlertDialog.Builder(getActivity())
                .setTitle("Error")
                .setMessage(error)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
