package stepbystep.co.wikiofsavior.Tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import stepbystep.co.wikiofsavior.Maps.MapsFragment;
import stepbystep.co.wikiofsavior.Stats.StatsFragment;
import stepbystep.co.wikiofsavior.XP_Simulator.XP_Fragment;

/**
 * Created by Admin on 11-12-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MapsFragment();
            case 1:
                return new XP_Fragment();
            default:
                return new StatsFragment();
        }
        // Which Fragment should be dislpayed by the viewpager for the given position
        // In my case we are showing up only one fragment in all the three tabs so we are
    }

    @Override
    public int getCount() {
        return 3;           // As there are only 3 Tabs
    }

}