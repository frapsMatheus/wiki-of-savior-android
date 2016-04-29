package stepbystep.co.wikiofsavior;

import android.app.Application;

import com.parse.Parse;
/**
 * Created by fraps on 4/28/16.
 */
public class WikiOfSaviorAPP extends Application
{
    public int showAddCount=0;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "GurnUYv92UjyhN6hkfQibc3HYZgRclyldBP5Wzi2", "rMmmC3EFkJB3s4K7lW0KVdlAXjfjGhFeE1OPo2Fp");
    }
}
