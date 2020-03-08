package stepbystep.co.wikiofsavior.XP_Simulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import stepbystep.co.wikiofsavior.R;

/**
 * Created by Fraps on 15/05/2016.
 */
public class XP_Results_Activity extends AppCompatActivity {

    private TextView resultsText;

//    Base info
    Integer initialBaseLVL;
    Integer finalBaseLVL;
    Integer initialBaseXP;
    Integer finalBaseXP;
    Float   initialBasePercentage;
    Float   finalBasePercentage;

//    Class info
    Integer initialRank;
    Integer initialClassLVL;
    Integer initialClassXP;
    Float   initialClassPercentage;
    Integer finalClassRank;
    Integer finalClassLVL;
    Integer finalClassXP;
    Float   finalClassPercentage;

//    Cards info
    Integer cardsBaseXP;
    Integer cardsClassXP;

    ArrayList<SpannedString> stringArray = new ArrayList<>();

    static int initialColor;
    static int finalColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultsText = (TextView) findViewById(R.id.xp_result);

        initialColor = ContextCompat.getColor(this, R.color.colorSecondary);
        finalColor = ContextCompat.getColor(this, R.color.colorPrimary);

//        Prepare fields
        Intent dataIntent = getIntent();
//   Base data
        initialBaseLVL = dataIntent.getIntExtra("initialBaseLVL",0);
        finalBaseLVL = dataIntent.getIntExtra("finalBaseLVL",0);
        initialBaseXP = dataIntent.getIntExtra("initialBaseXP",0);
        finalBaseXP = dataIntent.getIntExtra("finalBaseXP",0);
        initialBasePercentage = dataIntent.getFloatExtra("initialBasePercentage",-1f);
        finalBasePercentage = dataIntent.getFloatExtra("finalBasePercentage",-1f);
//   Class data
        initialRank = dataIntent.getIntExtra("initialRank",0);
        initialClassLVL = dataIntent.getIntExtra("initialClassLVL",0);
        initialClassXP  = dataIntent.getIntExtra("initialClassXP",0);
        finalClassRank = dataIntent.getIntExtra("finalClassRank",0);
        finalClassLVL = dataIntent.getIntExtra("finalClassLevel",0);
        finalClassXP = dataIntent.getIntExtra("finalClassXP",0);
        initialClassPercentage = dataIntent.getFloatExtra("initialClassPercentage",-1f);
        finalClassPercentage = dataIntent.getFloatExtra("finalClassPercentage",-1f);
//        Cards data
        cardsBaseXP = dataIntent.getIntExtra("cardsBaseXP",0);
        cardsClassXP = dataIntent.getIntExtra("cardsClassXP",0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTextView();
    }

    private void setTextView()
    {
        prepareBaseInfo();
        stringArray.add(new SpannedString("\n"));
        prepareClassInfo();

        SpannedString finalString = new SpannedString("");
        for (SpannedString element : stringArray) {
            finalString = (SpannedString) TextUtils.concat(finalString,element);
            finalString = (SpannedString) TextUtils.concat(finalString,new SpannableString("\n"));
        }
        resultsText.setText(finalString);
    }

    private void prepareBaseInfo()
    {
        stringArray.add(new SpannedString("Base information"));
        stringArray.add(new SpannedString("=========================="));

        Spannable stringInitialLVL = new SpannableString(String.valueOf(initialBaseLVL));
        stringInitialLVL.setSpan(new ForegroundColorSpan(initialColor),0,stringInitialLVL.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("Initial level: ",stringInitialLVL));

        Spannable stringInitialXP = new SpannableString(String.valueOf(initialBaseXP));
        stringInitialXP.setSpan(new ForegroundColorSpan(initialColor),0,stringInitialXP.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("Initial total XP: ",stringInitialXP));

        Spannable stringInitialPercentage = new SpannableString(String.format("%.2f",initialBasePercentage) + " %");
        stringInitialPercentage.setSpan(new ForegroundColorSpan(initialColor),0,stringInitialPercentage.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("The initial extra % is: ",stringInitialPercentage));

        Spannable stringCardsXP = new SpannableString(String.valueOf(cardsBaseXP));
        stringCardsXP.setSpan(new ForegroundColorSpan(finalColor),0,stringCardsXP.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("The total XP gained from cards is: ",stringCardsXP));

        Spannable stringFinalLevel = new SpannableString(String.valueOf(finalBaseLVL));
        stringFinalLevel.setSpan(new ForegroundColorSpan(finalColor),0,stringFinalLevel.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("\nThe final level is: ",stringFinalLevel));

        Spannable stringFinalXP = new SpannableString(String.valueOf(finalBaseXP));
        stringFinalXP.setSpan(new ForegroundColorSpan(finalColor),0,stringFinalXP.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("The final total XP is: ",stringFinalXP));

        if (finalBasePercentage > -1) {
            Spannable stringFinalPercentage = new SpannableString(String.format("%.2f",finalBasePercentage) + " %");
            stringFinalPercentage.setSpan(new ForegroundColorSpan(finalColor),0,stringFinalPercentage.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringArray.add((SpannedString) TextUtils.concat("The final extra % is: ",stringFinalPercentage));
        }
    }

    private void prepareClassInfo()
    {
        stringArray.add(new SpannedString("Class information"));
        stringArray.add(new SpannedString("=========================="));

        Spannable stringInitialRank = new SpannableString(String.valueOf(initialRank));
        stringInitialRank.setSpan(new ForegroundColorSpan(initialColor),0,stringInitialRank.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("Initial rank: ",stringInitialRank));

        Spannable stringInitialLVL = new SpannableString(String.valueOf(initialClassLVL));
        stringInitialLVL.setSpan(new ForegroundColorSpan(initialColor),0,stringInitialLVL.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("Initial level: ",stringInitialLVL));

        Spannable stringInitialXP = new SpannableString(String.valueOf(initialClassXP));
        stringInitialXP.setSpan(new ForegroundColorSpan(initialColor),0,stringInitialXP.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("Initial total XP: ",stringInitialXP));

        Spannable stringInitialPercentage = new SpannableString(String.format("%.2f",initialClassPercentage) + " %");
        stringInitialPercentage.setSpan(new ForegroundColorSpan(initialColor),0,stringInitialPercentage.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("The initial extra % is: ",stringInitialPercentage));

        Spannable stringCardsXP = new SpannableString(String.valueOf(cardsClassXP));
        stringCardsXP.setSpan(new ForegroundColorSpan(finalColor),0,stringCardsXP.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("The total XP gained from cards is: ",stringCardsXP));

        Spannable stringFinalRank = new SpannableString(String.valueOf(finalClassRank));
        stringFinalRank.setSpan(new ForegroundColorSpan(finalColor),0,stringFinalRank.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("\nFinal rank: ",stringFinalRank));

        Spannable stringFinalLevel = new SpannableString(String.valueOf(finalClassLVL));
        stringFinalLevel.setSpan(new ForegroundColorSpan(finalColor),0,stringFinalLevel.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("The final level is: ",stringFinalLevel));

        Spannable stringFinalXP = new SpannableString(String.valueOf(finalClassXP));
        stringFinalXP.setSpan(new ForegroundColorSpan(finalColor),0,stringFinalXP.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringArray.add((SpannedString) TextUtils.concat("The final total XP is: ",stringFinalXP));

        if (finalClassPercentage > -1) {
            Spannable stringFinalPercentage = new SpannableString(String.format("%.2f",finalClassPercentage) + " %");
            stringFinalPercentage.setSpan(new ForegroundColorSpan(finalColor),0,stringFinalPercentage.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringArray.add((SpannedString) TextUtils.concat("The final extra % is: ",stringFinalPercentage));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
