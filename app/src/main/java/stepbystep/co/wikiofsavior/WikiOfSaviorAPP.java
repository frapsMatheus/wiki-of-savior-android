package stepbystep.co.wikiofsavior;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.parse.Parse;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
/**
 * Created by fraps on 4/28/16.
 */
public class WikiOfSaviorAPP extends Application
{

    public AdManager adManager;
    @Override
    public void onCreate()
    {
        super.onCreate();
        adManager = new AdManager(this);
        Fabric.with(this, new Crashlytics());

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "GurnUYv92UjyhN6hkfQibc3HYZgRclyldBP5Wzi2", "rMmmC3EFkJB3s4K7lW0KVdlAXjfjGhFeE1OPo2Fp");
//        AdBuddiz.setPublisherKey("97c7d2d1-3fce-4ea5-8f29-21df104c1dcd");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
