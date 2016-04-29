package stepbystep.co.wikiofsavior.Maps;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.revmob.RevMob;
import com.tonicartos.superslim.GridSLM;
import java.util.LinkedHashMap;

import stepbystep.co.wikiofsavior.DataTypes.Map;
import stepbystep.co.wikiofsavior.R;
import stepbystep.co.wikiofsavior.WikiOfSaviorAPP;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by fraps on 4/28/16.
 */
public class MapsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int HEADER_TYPE = 1;
    int CELL_TYPE = 2;

    private LinkedHashMap<Integer,Map> mMapsDict;
    private LinkedHashMap<Integer,Integer> mLevelsHash;
    private LinkedHashMap<Integer,Integer> mPositionsHash;
    private Activity mContext;
    private Dialog mImageDialog;
    private ImageView mImageFullscreen;
    private PhotoViewAttacher mAttacher;

    public MapsRecyclerAdapter(Dialog imageDialog, LinkedHashMap<Integer,Map> mapsDict, LinkedHashMap<Integer,Integer> levels, LinkedHashMap<Integer,Integer> positionsHash, Activity context)
    {
        mImageDialog = imageDialog;
        mLevelsHash = levels;
        mPositionsHash = positionsHash;
        mMapsDict = mapsDict;
        mContext = context;
        mImageFullscreen = (ImageView) mImageDialog.findViewById(R.id.fullScreen_imageView);
        mAttacher = new PhotoViewAttacher(mImageFullscreen);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                mImageDialog.dismiss();
            }
            @Override
            public void onOutsidePhotoTap() {
                mImageDialog.dismiss();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == CELL_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_cell, parent, false);
            MapsCell cell = new MapsCell(view);
            return cell;
        } else if (viewType == HEADER_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_header_cell, parent, false);
            MapsHeaderCell cell = new MapsHeaderCell(view);
            return cell;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isPositionHeader(position)) {
            return HEADER_TYPE;
        } else {
            return CELL_TYPE;
        }
    }

    private void loadImage(Map map)
    {
        map.mImage.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                Bitmap bmp = BitmapFactory
                        .decodeByteArray(data, 0, data.length);
                mImageFullscreen.setImageBitmap(bmp);
                mAttacher.update();
            }
        });
        mImageFullscreen.setImageBitmap(null);
    }

    private boolean isPositionHeader(int position)
    {
        return mLevelsHash.containsKey(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        final View itemView = holder.itemView;
        final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
//        TODO: Fix stick header
        if (holder instanceof MapsHeaderCell) {
            ((MapsHeaderCell) holder).setHeader(mLevelsHash.get(position));
            lp.setFirstPosition(position);
        } else if (holder instanceof MapsCell) {
            final Map map = mMapsDict.get(position);
            ((MapsCell)holder).setMap(map);
            lp.setFirstPosition(mPositionsHash.get(map.mLevel));
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    WikiOfSaviorAPP app = (WikiOfSaviorAPP)mContext.getApplication();
                    app.showAddCount++;
                    if(app.showAddCount==3){
                        RevMob revMob = RevMob.session();
                        revMob.showFullscreen(mContext);
                        app.showAddCount=0;

                    }
                    mImageDialog.show();
                    loadImage(map);
                }
            });
        }

        itemView.setLayoutParams(lp);
    }

    @Override
    public int getItemCount()
    {
        return mMapsDict.size() + mLevelsHash.size();
    }
}
