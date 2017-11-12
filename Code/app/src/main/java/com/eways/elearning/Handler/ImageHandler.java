package com.eways.elearning.Handler;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by zzzzz on 11/12/2017.
 */

public class ImageHandler {
    private Context context;

    public ImageHandler(Context context){
        this.context = context;
    }

    public void loadImage(String url, ImageView imageView){
        Picasso.with(context).load(url).into(imageView);
    }
}
