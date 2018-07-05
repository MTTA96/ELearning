package com.eways.elearning.Adapter.SpecialAdapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 7/2/2018.
 */

public class SpecialVHolder extends RecyclerView.ViewHolder {

    ImageView ivImage;
    TextView tvName;

    public SpecialVHolder(View itemView) {
        super(itemView);

        ivImage = itemView.findViewById(R.id.iv_image_spec);
        tvName = itemView.findViewById(R.id.tv_name_spec);
    }
}
