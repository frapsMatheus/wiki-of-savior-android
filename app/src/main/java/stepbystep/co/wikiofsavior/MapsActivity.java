package stepbystep.co.wikiofsavior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import stepbystep.co.wikiofsavior.DataTypes.Map;
import stepbystep.co.wikiofsavior.Maps.MapsRecyclerAdapter;

public class MapsActivity extends AppCompatActivity {

    private RecyclerView mMapsRecycler;
    private MapsRecyclerAdapter mMapsAdapter;
    private LinearLayoutManager mRecyclerLayout;
    private LinkedHashMap<Integer,Integer> mLevelsMap;
    private LinkedHashMap<Integer,Integer> mPositionsLevelsMap;
    private LinkedHashMap<Integer,Map> mMapsDictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//      Start ui fields
        mMapsRecycler = (RecyclerView)findViewById(R.id.maps_recyclerView);
        mRecyclerLayout = new LinearLayoutManager(getApplication());
        mMapsRecycler.setLayoutManager(mRecyclerLayout);
        donwloadMaps();
    }

    private void donwloadMaps()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Maps");
        query.orderByAscending("Lv");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {
                if (e == null) {
                    mLevelsMap = new LinkedHashMap<Integer,Integer>();
                    mPositionsLevelsMap = new LinkedHashMap<Integer,Integer>();
                    mMapsDictionary = new LinkedHashMap<Integer, Map>();
                    int currentLevel = -1;
                    int currentPosition = 0;
                    ArrayList<Map> mMapsArray = new ArrayList<Map>();
                    for (ParseObject obj : objects) {
                        Map map = new Map(obj);
                        if (currentLevel != map.mLevel) {
                            mLevelsMap.put(currentPosition,map.mLevel);
                            mPositionsLevelsMap.put(map.mLevel,currentPosition);
                            currentPosition++;
                            currentLevel = map.mLevel;
                        }
                        mMapsDictionary.put(currentPosition,map);
                        currentPosition++;
                    }
                    mMapsAdapter = new MapsRecyclerAdapter(mMapsDictionary,mLevelsMap,mPositionsLevelsMap,MapsActivity.this);
                    mMapsRecycler.setAdapter(mMapsAdapter);
                }
            }
        });
    }
}
