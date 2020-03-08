package stepbystep.co.wikiofsavior.Recipes;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;

import stepbystep.co.wikiofsavior.DataTypes.Item;
import stepbystep.co.wikiofsavior.R;

/**
 * Created by Fraps on 20/05/2016.
 */
public class ItemCell extends RecyclerView.ViewHolder{

    private ImageView mImage;
    private TextView  mName;
    private TextView  mAmmount;

    public ItemCell(View itemView) {
        super(itemView);
        loadChild(itemView);
    }

    private void loadChild(View v){
        mImage = (ImageView) v.findViewById(R.id.item_image);
        mName = (TextView)v.findViewById(R.id.item_name);
        mAmmount = (TextView)v.findViewById(R.id.item_count);
    }

    public void setCell(Item result, Item.ItemType type){
        result.mImage.getDataInBackground(new GetDataCallback()
        {
            @Override
            public void done(byte[] data, ParseException e)
            {
                if (e == null) {
                    Bitmap bmp = BitmapFactory
                            .decodeByteArray(data, 0, data.length);
                    mImage.setImageBitmap(bmp);
                }
            }
        });
        mName.setText(result.mName);
        if (result.mAmount != null && type == Item.ItemType.Recipe) {
            mAmmount.setVisibility(View.VISIBLE);
            mAmmount.setText(result.mAmount);
        } else {
            mAmmount.setVisibility(View.GONE);
        }
    }
}
