package com.eways.elearning.Handler;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by zzzzz on 11/12/2017.
 */

public class ImageHandler {
    private Context context;
    private Transformation transformationRound = new RoundedCornersTransformation(5, 0);

    public ImageHandler(Context context){
        this.context = context;
    }

    @TargetApi(16)
    public void loadImageRound(String url, ImageView imageView){
        if (Build.VERSION.SDK_INT > 15)
            imageView.setBackground(null);
        Picasso.with(context).load(url).transform(transformationRound).transform(new CropCircleTransformation()).into(imageView);
    }
}
