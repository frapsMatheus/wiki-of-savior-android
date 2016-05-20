package stepbystep.co.wikiofsavior.XP_Simulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import stepbystep.co.wikiofsavior.R;

/**
 * Created by Fraps on 15/05/2016.
 */
public class XP_Fragment extends Fragment {

    private EditText mBaseLevel;
    private EditText mBasePercentage;
    private EditText mClassRank;
    private EditText mClassLevel;
    private EditText mClassPercentage;

    private ArrayList<EditText> mCardFields = new ArrayList<>();
    private Button  mCalculate;

    private LinkedHashMap<Integer,BaseLevel> mLevels;
    private LinkedHashMap<Integer,LinkedHashMap<Integer,ClassLevel>> mClasses;
    private LinkedHashMap<Integer,Card> mCards;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_xp_simulator, container, false);

//        Prepare fields
//        Base
        mBaseLevel = (EditText)v.findViewById(R.id.xp_level);
        mBasePercentage = (EditText)v.findViewById(R.id.xp_percentage);
//        Class
        mClassRank = (EditText)v.findViewById(R.id.xp_class_rank);
        mClassLevel = (EditText)v.findViewById(R.id.xp_class_level);
        mClassPercentage = (EditText)v.findViewById(R.id.xp_class_percentage);
//     Cards
        mCardFields.add((EditText)v.findViewById(R.id.xp_card1));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card2));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card3));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card4));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card5));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card6));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card7));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card8));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card9));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card10));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card11));
        mCardFields.add((EditText)v.findViewById(R.id.xp_card12));

        mCalculate = (Button)v.findViewById(R.id.xp_calculate);
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

//        Read CSV
        mLevels = readLVLsCSV();
        mClasses = readClassCSV();
        mCards = readCardsCSV();

        return v;
    }

    private LinkedHashMap<Integer,BaseLevel> readLVLsCSV()
    {
        LinkedHashMap<Integer,BaseLevel> result = new LinkedHashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("ExpTable.csv"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                BaseLevel newBaseLevel = new BaseLevel(mLine);
                result.put(newBaseLevel.level,newBaseLevel);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return result;
    }

    private  LinkedHashMap<Integer,LinkedHashMap<Integer,ClassLevel>> readClassCSV()
    {
        LinkedHashMap<Integer,LinkedHashMap<Integer,ClassLevel>> result = new LinkedHashMap<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("ClassExpTable.csv"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            LinkedHashMap<Integer,ClassLevel> rankHashMap = new LinkedHashMap<>();
            Integer currentRank = 0;
            while ((mLine = reader.readLine()) != null) {
                //process line
                ClassLevel newClassLevel = new ClassLevel(mLine);
                if (currentRank != newClassLevel.rank) {
                    if (currentRank != 0) {
                        result.put(currentRank,rankHashMap);
                    }
                    rankHashMap = new LinkedHashMap<>();
                    currentRank = newClassLevel.rank;
                }
                rankHashMap.put(newClassLevel.level,newClassLevel);
            }
            result.put(currentRank,rankHashMap);
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return result;
    }

    private LinkedHashMap<Integer,Card> readCardsCSV()
    {
        LinkedHashMap<Integer,Card> result = new LinkedHashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("CardsXP.csv"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                Card newCard = new Card(mLine);
                result.put(newCard.level,newCard);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return result;
    }

    private void calculate ()
    {
        if (checkFields()) {
            return;
        }

        Intent resultsIntent = new Intent(getActivity(), XP_Results_Activity.class);

//        Initial values
        Integer initialBaseXP = getInitialXP();
        Integer initialClassXP = getInitialClassXP();
        HashMap<String,Integer> cardsSum = getCardsSum();
//        XP results
        Integer finalBaseXP = cardsSum.get("baseXP") + initialBaseXP;
        Integer finalClassXP = cardsSum.get("classXP") + initialClassXP;

//        Base results
        resultsIntent.putExtra("initialBaseLVL",Integer.parseInt(mBaseLevel.getText().toString()));
        resultsIntent.putExtra("initialBasePercentage",Float.parseFloat(mBasePercentage.getText().toString()));
        resultsIntent.putExtra("initialBaseXP",initialBaseXP);
        resultsIntent.putExtra("cardsBaseXP", cardsSum.get("baseXP"));

        Integer finalBaseLevel = searchForBaseLVL(finalBaseXP);
        resultsIntent.putExtra("finalBaseLVL",finalBaseLevel);

        BaseLevel finalBaseObj = mLevels.get(finalBaseLevel);
        resultsIntent.putExtra("finalBaseXP",finalBaseObj.initialXP);

        if (finalBaseLevel != 302) {
            BaseLevel nextBaseObj = mLevels.get(finalBaseLevel+1);
            Float finalPercentage = ((((float) finalBaseXP) - ((float)finalBaseObj.initialXP))*100)/((float)nextBaseObj.requiredXP);
            resultsIntent.putExtra("finalBasePercentage",finalPercentage);
        }

//        Class results
        resultsIntent.putExtra("initialRank",Integer.parseInt(mClassRank.getText().toString()));
        resultsIntent.putExtra("initialClassLVL",Integer.parseInt(mClassLevel.getText().toString()));
        resultsIntent.putExtra("initialClassPercentage",Float.parseFloat(mClassPercentage.getText().toString()));
        resultsIntent.putExtra("initialClassXP", initialClassXP);
        resultsIntent.putExtra("cardsClassXP",cardsSum.get("classXP"));

        Integer initialRank = Integer.parseInt(mClassRank.getText().toString());
        HashMap<String,Integer> finalClassResults = searchForClassLevel(initialRank,finalClassXP);

        Integer finalRank = finalClassResults.get("rank");
        resultsIntent.putExtra("finalClassRank",finalRank);
        Integer finalClassLevel = finalClassResults.get("level");
        resultsIntent.putExtra("finalClassLevel",finalClassLevel);

        ClassLevel finalClassOBJ = searchClassLevel(finalRank,finalClassLevel);
        resultsIntent.putExtra("finalClassXP",finalClassOBJ.initialXP);

        if (finalClassOBJ.level != 15) {
            ClassLevel nextClassOBJ = searchClassLevel(finalRank,finalClassLevel+1);
            Float finalPercentage = (((float) finalClassResults.get("remainingXP"))*100)/((float)nextClassOBJ.requiredXP);
            resultsIntent.putExtra("finalClassPercentage",finalPercentage);
        }
        Answers.getInstance().logCustom(new CustomEvent("XP Simulation"));
        getActivity().startActivityForResult(resultsIntent,20);
        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

//  True if there is an error;
    private boolean checkFields ()
    {

        if (verifyEmptyFields()) {
            return true;
        }

        boolean error = false;
//      Check base info
        Integer level = Integer.parseInt(mBaseLevel.getText().toString());
        if (level > 303 || level < 1) {
            mBaseLevel.setError("Level must be between 1 and 302");
            error = true;
        }
        Float percentage = Float.parseFloat(mBasePercentage.getText().toString());
        if (percentage > 100 || percentage < 0) {
            mBasePercentage.setError("% must be between 0 and 100");
            error = true;
        }
//      Check class info
        Integer classRank = Integer.parseInt(mClassRank.getText().toString());
        if (classRank > 7 || classRank < 1) {
            mClassRank.setError("rank must be between 1 and 7");
            error = true;
        }
        Integer classLevel = Integer.parseInt(mClassLevel.getText().toString());
        if (classLevel > 15 || classLevel < 1) {
            mClassLevel.setError("Level must be between 1 and 15");
            error = true;
        }
        Float classPercentage = Float.parseFloat(mClassPercentage.getText().toString());
        if (classPercentage > 100 || classPercentage < 0) {
            mClassPercentage.setError("% must be between 0 and 100");
            error = true;
        }
        return error;
    }

//    Returns true if there is an error
    private boolean verifyEmptyFields ()
    {
        boolean error = false;
        if (mBaseLevel.getText().toString().matches("")) {
            mBaseLevel.setError("Field can't be empty");
            error = true;
        } else if (mBasePercentage.getText().toString().matches("")) {
            mBasePercentage.setError("Field can't be empty");
            error = true;
        } else if (mClassRank.getText().toString().matches("")) {
            mClassRank.setError("Field can't be empty");
            error = true;
        } else if (mClassLevel.getText().toString().matches("")) {
            mClassLevel.setError("Field can't be empty");
            error = true;
        } else if (mClassPercentage.getText().toString().matches("")) {
            mClassPercentage.setError("Field can't be empty");
            error = true;
        }
        return error;
    }

    private Integer getInitialXP ()
    {
        Integer result;
        Integer currentLevel = Integer.parseInt(mBaseLevel.getText().toString());
        Float currentPercentage = (Float.parseFloat(mBasePercentage.getText().toString())) / 100;
        BaseLevel level = mLevels.get(currentLevel);
        if (level.level == 302) {
            return level.initialXP;
        } else {
            BaseLevel nextLevel = mLevels.get(currentLevel+1);
            result = Math.round(level.initialXP + nextLevel.requiredXP * currentPercentage);
        }
        return  result;
    }

    private Integer getInitialClassXP()
    {
        Integer result = 0;
        Integer currentRank = Integer.parseInt(mClassRank.getText().toString());
        Integer currentLevel = Integer.parseInt(mClassLevel.getText().toString());
        Float currentPercentage = Float.parseFloat(mClassPercentage.getText().toString()) / 100;
        ClassLevel classLevel = searchClassLevel(currentRank,currentLevel);
        if (currentLevel == 15) {
            return classLevel.initialXP;
        } else {
            ClassLevel nextClassLevel = searchClassLevel(currentRank,currentLevel + 1);
            result = Math.round(classLevel.initialXP + (nextClassLevel.requiredXP * currentPercentage));
        }
        return result;
    }

    private ClassLevel searchClassLevel(Integer rank, Integer level)
    {
        LinkedHashMap<Integer,ClassLevel> rankHashMap = mClasses.get(rank);
        return rankHashMap.get(level);
    }

    private HashMap<String,Integer> getCardsSum()
    {
        HashMap<String,Integer> result = new HashMap<>();
        Integer baseXP = 0;
        Integer classXP = 0;
        for (int i=0; i<12; i++) {
            EditText cardField = mCardFields.get(i);
            if (!cardField.getText().toString().matches("")) {
                Integer numberOfCards = Integer.parseInt(cardField.getText().toString());
                Card currentCard = mCards.get(i+1);
                baseXP += numberOfCards*currentCard.baseXP;
                classXP = numberOfCards*currentCard.classXP;
            }
        }
        result.put("baseXP",baseXP);
        result.put("classXP",classXP);
        return  result;
    }

    private Integer searchForBaseLVL(Integer xp)
    {
        for (int i=1;i<303;i++) {
            BaseLevel baseLevel = mLevels.get(i);
            if (baseLevel.initialXP > xp) {
                return i-1;
            } else if (baseLevel.initialXP == xp) {
                return i;
            }
        }
        return 302;
    }

    private HashMap<String,Integer> searchForClassLevel(Integer rank,Integer xp)
    {
        Integer currentXP = xp;
        HashMap<String,Integer> result = new HashMap<>();
        for (int currentRank = rank ; currentRank < 8; currentRank++) {
            HashMap<Integer,ClassLevel> rankHashMap = mClasses.get(rank);
            for (int level=1;level<16;level++) {
                ClassLevel currentClassOBJ = rankHashMap.get(level);
                if (currentClassOBJ.initialXP > currentXP) {
                    ClassLevel oldClassOBJ = rankHashMap.get(level-1);
                    result.put("rank",currentRank);
                    result.put("level",level-1);
                    result.put("remainingXP",currentXP-oldClassOBJ.initialXP);
                    return result;
                } else if (currentClassOBJ.initialXP == currentXP) {
                    result.put("rank",currentRank);
                    result.put("level",level);
                    result.put("remainingXP",0);
                    return result;
                }
            }
            ClassLevel finalClassOBJ = rankHashMap.get(15);
            currentXP -= finalClassOBJ.initialXP;
        }
        result.put("rank",7);
        result.put("level",15);
        result.put("remainingXP",currentXP);
        return result;
    }

}
