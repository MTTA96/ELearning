package com.eways.elearning.Utils.Handler;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by ADMIN on 5/26/2018.
 */

public class ImageHandler {
    Context context;

    public ImageHandler(Context context) {
        this.context = context;
    }

    public void LoadImage(ImageView imageView, int res) {
        Picasso.with(context).load(res).into(imageView);
    }
}
