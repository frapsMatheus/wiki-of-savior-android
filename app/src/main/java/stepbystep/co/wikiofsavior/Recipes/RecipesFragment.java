package stepbystep.co.wikiofsavior.Recipes;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.kyleduo.switchbutton.SwitchButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tonicartos.superslim.LayoutManager;

import java.util.ArrayList;
import java.util.List;

import stepbystep.co.wikiofsavior.DataTypes.Item;
import stepbystep.co.wikiofsavior.R;
import stepbystep.co.wikiofsavior.WikiOfSaviorAPP;

/**
 * Created by Fraps on 20/05/2016.
 */
public class RecipesFragment extends Fragment {

    private SwitchButton mSwitch;
    private TextView mRecipeTag;
    private TextView mItemTag;
    private EditText mEditField;
    private RecyclerView mRecyclerView;
    private Button       mSearchButton;
    private LayoutManager mRecyclerLayout;
    private TextView mMainText;

    private ArrayList<Item> mItems;
    private Item mMainItem;
    private RecipesAdapter mItemsAdapter;
    private ProgressBar mProgress;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.recipes_fragment, container, false);

//        Set fields
        mSwitch = (SwitchButton)v.findViewById(R.id.recipes_Switch);
        mRecipeTag = (TextView)v.findViewById(R.id.recipes_RecipeTag);
        mItemTag = (TextView)v.findViewById(R.id.recipes_ItemTag);
        mEditField = (EditText)v.findViewById(R.id.recipes_EditText);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recipes_RecyclerView);
        mSearchButton = (Button)v.findViewById(R.id.recipes_SearchButton);
        mProgress = (ProgressBar) v.findViewById(R.id.map_progress);
        mMainText = (TextView)v.findViewById(R.id.recipe_MainText);
        mProgress.setVisibility(View.GONE);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mItemTag.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                    mRecipeTag.setTextColor(Color.LTGRAY);
                    mEditField.setHint(R.string.item_hint);
                    mMainText.setText(getString(R.string.item_message));
                } else {
                    mItemTag.setTextColor(Color.LTGRAY);
                    mRecipeTag.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                    mEditField.setHint(R.string.recipes_hint);
                    mMainText.setText(getString(R.string.recipe_message));
                }
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(getActivity());
                if (mEditField.getText().toString().trim().length() != 0) {
                    Answers.getInstance().logCustom(new CustomEvent("Recipe search"));
                    mProgress.setVisibility(View.VISIBLE);
                    mRecyclerView.setAdapter(null);
                    if (mSwitch.isChecked()) {
                        getRecipesForItem();
                    } else {
                        getItemsForRecipe();
                    }
                }
            }
        });

        mRecyclerLayout = new LayoutManager(getActivity().getApplication());
        mRecyclerView.setLayoutManager(mRecyclerLayout);

        return v;
    }

    private void getRecipesForItem()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("RecipeItem");
        final String recipeName = toTitleCase(mEditField.getText().toString());
        query.whereEqualTo("item",recipeName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                mProgress.setVisibility(View.GONE);
                if (e == null && objects.size() > 0) {
                    mMainItem = null;
                    mItems = new ArrayList<>();
                    for (ParseObject obj : objects) {
                        if (mMainItem == null) {
                            mMainItem = new Item(obj, Item.ItemType.Item);
                        }
                        mItems.add(new Item(obj, Item.ItemType.Recipe));
                    }
                    mItemsAdapter = new RecipesAdapter(mItems, mMainItem, Item.ItemType.Item);
                    mRecyclerView.setAdapter(mItemsAdapter);
                    WikiOfSaviorAPP app = (WikiOfSaviorAPP)getActivity().getApplication();
                    app.adManager.showAd();
                } else {
                    showError("Couldn't find any item named " + recipeName);
                }
            }
        });
    }

    private void getItemsForRecipe()
    {
        mProgress.setVisibility(View.VISIBLE);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("RecipeItem");
        final String recipeName = toTitleCase(mEditField.getText().toString());
        query.whereEqualTo("recipe",recipeName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                mProgress.setVisibility(View.GONE);
                if (e == null && objects.size() > 0) {
                    mMainItem = null;
                    mItems = new ArrayList<>();
                    for (ParseObject obj : objects) {
                        if (mMainItem == null) {
                            mMainItem = new Item(obj, Item.ItemType.Recipe);
                        }
                        mItems.add(new Item(obj, Item.ItemType.Item));
                    }
                    WikiOfSaviorAPP app = (WikiOfSaviorAPP)getActivity().getApplication();
                    app.adManager.showAd();
                    mItemsAdapter = new RecipesAdapter(mItems, mMainItem, Item.ItemType.Recipe);
                    mRecyclerView.setAdapter(mItemsAdapter);
                } else {
                    showError("Couldn't find any recipe named " + recipeName);
                }
            }
        });
    }

    public static String toTitleCase(String givenString)
    {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    private void showError(String error)
    {
        mEditField.setError(error);
    }

    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
