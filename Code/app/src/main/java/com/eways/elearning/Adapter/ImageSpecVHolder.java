package com.eways.elearning.Adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 7/6/2018.
 */

public class ImageSpecVHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    ImageView ivEditTitle;
    ImageView ivEditVideo;
    VideoView video;
    RecyclerView rcImage;

    public ImageSpecVHolder(View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.title);
        ivEditTitle = itemView.findViewById(R.id.btn_edit);
        ivEditVideo = itemView.findViewById(R.id.btn_edit_video);
        video = itemView.findViewById(R.id.video);
        rcImage = itemView.findViewById(R.id.rc_item_subject);
    }
}
