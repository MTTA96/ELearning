package com.eways.elearning.Adapter.Favorite;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 5/17/2018.
 */

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView text;
    CheckBox selected;
    View touch;
    public FavoriteViewHolder(View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.img_favorite);
        text = itemView.findViewById(R.id.text_favorite);
        touch = itemView.findViewById(R.id.touch_favorite);
        selected = itemView.findViewById(R.id.seclect_favorite);
    }

}
