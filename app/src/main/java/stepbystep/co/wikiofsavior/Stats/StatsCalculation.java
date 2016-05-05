package stepbystep.co.wikiofsavior.Stats;

import android.support.v4.util.Pair;

import java.util.ArrayList;

/**
 * Created by fraps on 5/5/16.
 */
public class StatsCalculation {

    private static final String mArcher = "Archer";
    private static final String mSwordsman = "Swordsman";
    private static final String mCleric = "Cleric";
    private static final String mWizard = "Wizard";

    private int mSTR;
    private int mCON;
    private int mInt;
    private int mSPR;
    private int mDex;
    private int mLVL;
    private String mClassType;


    public StatsCalculation(int str, int con, int inteligence, int spr, int dex, int lvl,
                            String ClassType)
    {
        mSTR = str;
        mCON = con;
        mInt = inteligence;
        mSPR = spr;
        mDex = dex;
        mLVL = lvl;
        mClassType = ClassType;
    }

    public ArrayList<Pair<String,String>> getArrayOfResults()
    {
        ArrayList<Pair<String,String>> result = new ArrayList<>();
        result.add(new Pair<String, String>("HP",String.valueOf(getHP())));
        result.add(new Pair<String, String>("SP",String.valueOf(getSP())));
        result.add(new Pair<String, String>("HP Recovery",String.valueOf(getHPRecovery())));
        result.add(new Pair<String, String>("SP Recovery",String.valueOf(getSPRecovery())));
        result.add(new Pair<String, String>("Physical Attack",String.valueOf(getPhysicalAttack())));
        result.add(new Pair<String, String>("Magic Attack",String.valueOf(getMagicAttack())));
        result.add(new Pair<String, String>("AOE Attack Ratio",String.valueOf(getAOEAttackRation())));
        result.add(new Pair<String, String>("Accuracy",String.valueOf(getAccuracy())));
        result.add(new Pair<String, String>("Magic Amplification",String.valueOf(getMagicAmplification())));
        result.add(new Pair<String, String>("Block Penetration",String.valueOf(getBlockPenetration())));
        result.add(new Pair<String, String>("Critical Attack",String.valueOf(getCriticalAttack())));
        result.add(new Pair<String, String>("Critical Rate",String.valueOf(getCriticalRate())));
        result.add(new Pair<String, String>("Physical Defense",String.valueOf(getPhysicalAttack())));
        result.add(new Pair<String, String>("Magic Defense",String.valueOf(getMagicDefense())));
        result.add(new Pair<String, String>("AOE Defense Ratio",String.valueOf(getAOEDefenseRation())));
        result.add(new Pair<String, String>("Evasion",String.valueOf(getEvasion())));
        result.add(new Pair<String, String>("Block", String.valueOf(getBlock())));
        result.add(new Pair<String, String>("Critical Resistance",String.valueOf(getCriticalResitance())));
        result.add(new Pair<String, String>("Stamina",String.valueOf(getStamina())));
        result.add(new Pair<String, String>("Weight Limit",String.valueOf(getWeightLimit())));
        return result;
    }

    private Double getClassHPMultiplier()
    {
        switch (mClassType){
            case mArcher:
                return 1.4;
            case mSwordsman:
                return 3.3;
            case mCleric:
                return 1.5;
            case mWizard:
                return 1.1;
            default:
                return 1.0;
        }
    }

    private Double getClassSPMultiplier()
    {
        switch (mClassType){
            case mArcher:
                return 0.9;
            case mSwordsman:
                return 0.8;
            case mCleric:
            case mWizard:
                return 1.2;
            default:
                return 1.0;
        }
    }

    private int getHP()
    {
        int lvlPart = (mLVL - 1) * 17;
        float classHPMultiplier = getClassHPMultiplier().floatValue();
        return Math.round((classHPMultiplier * lvlPart) + (mCON*85));
    }

    private int getSP()
    {
        Double lvlPart = (mLVL - 1) * 6.7;
        float classhSPMultiplier = getClassSPMultiplier().floatValue();
        int result = Math.round((classhSPMultiplier * lvlPart.floatValue()) + (mSPR * 13));
        if (mClassType.equals(mCleric)) {
            return  Math.round(result + (mLVL * (float)1.675));
        } else {
            return result;
        }
    }

    private int getHPRecovery()
    {
        switch (mClassType) {
            case mArcher:
                return Math.round(((float)0.7*mLVL) + mCON);
            case mSwordsman:
                return Math.round(((float)1.65*mLVL) + mCON);
            case mCleric:
                return Math.round(((float)0.75*mLVL) + mCON);
            case mWizard:
                return Math.round(((float)0.55*mLVL) + mCON);
            default:
                return 0;
        }
    }

    private int getSPRecovery()
    {
        switch (mClassType) {
            case mArcher:
                return Math.round(((float)0.45*mLVL) + mSPR);
            case mSwordsman:
                return Math.round(((float)0.4*mLVL) + mSPR);
            case mCleric:
                return Math.round(((float)0.85*mLVL) + mSPR);
            case mWizard:
                return Math.round(((float)0.6*mLVL) + mSPR);
            default:
                return 0;
        }
    }

    private int getPhysicalAttack()
    {
        return mLVL+mSTR;
    }

    private int getMagicAttack()
    {
        return mLVL+mInt;
    }

    private int getAOEAttackRation()
    {
        switch (mClassType) {
            case mSwordsman:
                return 4;
            case mCleric:
            case mWizard:
                return 3;
            default:
                return 0;
        }
    }

    private int getAccuracy()
    {
        int baseAccuracy = mLVL+mDex;
        if (mClassType.equals(mArcher)) {
            return (baseAccuracy + Math.round((mLVL+4)/4));
        } else {
            return baseAccuracy;
        }
    }

    private int getMagicAmplification()
    {
        return 0;
    }

    private int getBlockPenetration()
    {
        return Math.round((mLVL * (float)0.5) + mSPR);
    }

    private int getCriticalAttack()
    {
        return mSTR;
    }

    private int getCriticalRate()
    {
        if (mClassType.equals(mArcher)) {
            return (mDex + Math.round(mLVL/5));
        } else {
            return mDex;
        }
    }

    private int getPhysicalDefense()
    {
        if (mClassType.equals(mSwordsman)) {
            return Math.round(mLVL/2);
        } else {
            return Math.round((mLVL/2) + (mLVL/4));
        }
    }

    private int getMagicDefense()
    {
        if (mClassType.equals(mWizard)) {
            return Math.round((mLVL/2) + (mSPR/5) + (mLVL/4));
        } else {
            return Math.round((mLVL/2) + (mSPR/5));
        }
    }

    private int getAOEDefenseRation()
    {
        return 1;
    }

    private int getEvasion()
    {
        if (mClassType.equals(mArcher)) {
            return Math.round((mLVL + mDex) + (mLVL/8));
        } else {
            return mLVL + mDex;
        }
    }

    private int getBlock()
    {
        return 0;
    }

    private int getCriticalResitance()
    {
        return mCON;
    }

    private int getStamina()
    {
        return 25;
    }

    private int getWeightLimit()
    {
        return (5000 + (mCON * 5) + (mSTR * 5));
    }
}
