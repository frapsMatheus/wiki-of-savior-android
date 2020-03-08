package stepbystep.co.wikiofsavior;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Fraps on 16/05/2016.
 */
public class AdManager {

    InterstitialAd mInterstitialAd;
    Context mContext;
    public int showAddCount=0;
    SharedPreferences sharedPref;
    public AdManager(Context context)
    {
        mContext = context;
        sharedPref = context.getSharedPreferences(
                 context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-1164099775703792/8942569660");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
        showAddCount = sharedPref.getInt(context.getString(R.string.ad_count), 0);
    }

    public void showAd()
    {
        showAddCount++;
        if (showAddCount==5) {
            mInterstitialAd.show();
            showAddCount = 0;
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mContext.getString(R.string.ad_count), showAddCount);
        editor.commit();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("A316CF8DC2983C6DDB7ADD7ABFF0BF19")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
