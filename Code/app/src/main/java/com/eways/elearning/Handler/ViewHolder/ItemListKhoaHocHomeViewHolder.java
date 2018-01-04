package com.eways.elearning.Handler.ViewHolder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by zzzzz on 12/1/2017.
 */

public class ItemListKhoaHocHomeViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgUserImage;
    public TextView tvMon, tvGia;

    public ItemListKhoaHocHomeViewHolder(View itemView) {
        super(itemView);
        imgUserImage = (ImageView) itemView.findViewById(R.id.imageView_UserAvatar_ItemKhoaHocHome);
        tvMon = (TextView) itemView.findViewById(R.id.textView_Mon_ItemKhoaHocHome);
        tvGia = (TextView) itemView.findViewById(R.id.textView_Gia_ItemKhoaHocHome);
    }
}
