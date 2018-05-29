package com.eways.elearning.Utils.Handler;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.eways.elearning.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


/**
 * Created by ADMIN on 5/26/2018.
 */

public class ImageHandler {
    private Context context;
    private Transformation transformationRound = new RoundedCornersTransformation(5, 0);
    private Transformation transformationSquared = new CropSquareTransformation();

    public ImageHandler(Context context){
        this.context = context;
    }

    @TargetApi(16)
    public void loadImageRound(String url, ImageView imageView){
        if (Build.VERSION.SDK_INT > 15)
            imageView.setBackground(null);

        if (url != null) {
            if (url.compareTo("null") != 0)
                Picasso.with(context).load(url).transform(transformationRound).transform(new CropCircleTransformation()).into(imageView);
            else
                imageView.setBackgroundResource(R.drawable.default_avatar);
        }
        else
            imageView.setBackgroundResource(R.drawable.default_avatar);
    }

    @TargetApi(16)
    public void loadImageSquare(String url, ImageView imageView){
        if (Build.VERSION.SDK_INT > 15)
            imageView.setBackground(null);
        if (url != null) {
            if (url.compareTo("null") != 0 && url.compareTo("") !=0)
                Picasso.with(context).load(url).transform(transformationSquared).resize(180,180).centerCrop().into(imageView);
            else
                imageView.setBackgroundResource(R.drawable.default_avatar);
        }
        else
            imageView.setBackgroundResource(R.drawable.default_avatar);
    }
}
