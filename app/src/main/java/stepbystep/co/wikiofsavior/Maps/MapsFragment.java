package stepbystep.co.wikiofsavior.Maps;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.tonicartos.superslim.LayoutManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import stepbystep.co.wikiofsavior.DataTypes.Map;
import stepbystep.co.wikiofsavior.MainActivity;
import stepbystep.co.wikiofsavior.R;

public class MapsFragment extends Fragment {

    private RecyclerView mMapsRecycler;
    private MapsRecyclerAdapter mMapsAdapter;
    private LayoutManager mRecyclerLayout;
    private ArrayList<Map> mMapsArray;
    private LinkedHashMap<Integer,Integer> mLevelsMap;
    private LinkedHashMap<Integer,Integer> mPositionsLevelsMap;
    private LinkedHashMap<Integer,Map> mMapsDictionary;
    private Dialog mImageDialog;
    private ProgressBar mProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_maps, container, false);
        setHasOptionsMenu(true);
//      Start ui fields
        mMapsRecycler = (RecyclerView) v.findViewById(R.id.maps_recyclerView);
        mProgress = (ProgressBar) v.findViewById(R.id.map_progress);
        mProgress.setVisibility(View.VISIBLE);
        mRecyclerLayout = new LayoutManager(getActivity().getApplication());
        mMapsRecycler.setLayoutManager(mRecyclerLayout);
//       Prepare imageDialog
        mImageDialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
        mImageDialog.setContentView(R.layout.fullscreen_image);
        mImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        donwloadMaps();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager manager =
                (SearchManager) getActivity().getApplication().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = (MenuItem) menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(
                manager.getSearchableInfo(getActivity().getComponentName()));
        if (searchView != null) {
            searchView.setVisibility(View.VISIBLE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchList(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchList(newText);
                    return false;
                }
            });
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    searchList("");
                    return false;
                }
            });
        }
    }

    private void searchList(String text)
    {
        mLevelsMap = new LinkedHashMap<Integer,Integer>();
        mPositionsLevelsMap = new LinkedHashMap<Integer,Integer>();
        mMapsDictionary = new LinkedHashMap<Integer, Map>();
        int currentLevel = -1;
        int currentPosition = 0;
        for (Map map : mMapsArray) {
            if(isValidMap(map,text)) {
                if (currentLevel != map.mLevel) {
                    mLevelsMap.put(currentPosition, map.mLevel);
                    mPositionsLevelsMap.put(map.mLevel, currentPosition);
                    currentPosition++;
                    currentLevel = map.mLevel;
                }
                mMapsDictionary.put(currentPosition, map);
                currentPosition++;
            }
        }
        mMapsAdapter = new MapsRecyclerAdapter(mImageDialog,mMapsDictionary,mLevelsMap,
                                                mPositionsLevelsMap,getActivity());
        mMapsRecycler.setAdapter(mMapsAdapter);
    }

    private boolean isValidMap(Map map, String text)
    {
        if (map.mName.toLowerCase().contains(text.toLowerCase())) {
            return true;
        }
        String level = "Level " + String.valueOf(map.mLevel);
        if (level.toLowerCase().contains(text.toLowerCase())) {
            return true;
        }
        return false;
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
                    mProgress.setVisibility(View.GONE);
                    mMapsArray = new ArrayList<Map>();
                    for (ParseObject obj : objects) {
                        Map map = new Map(obj);
                        mMapsArray.add(map);
                    }
                    searchList("");
                }
            }
        });
    }
}
