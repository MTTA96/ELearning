package com.eways.elearning.Adapter.Circle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.eways.elearning.R;


/**
 * Created by ADMIN on 5/5/2018.
 */

public class CircleViewHolder extends RecyclerView.ViewHolder {
    /* VIEWS */
    ImageView circle;

    public CircleViewHolder(View itemView) {
        super(itemView);

        circle = (ImageView) itemView.findViewById(R.id.circle);
    }
}
