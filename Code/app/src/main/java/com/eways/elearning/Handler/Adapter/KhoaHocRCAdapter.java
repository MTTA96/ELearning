package com.eways.elearning.Handler.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/9/2017.
 */

public class KhoaHocRCAdapter extends RecyclerView.Adapter<KhoaHocRCAdapter.ViewHolder>  {

    ArrayList<CustomModelKhoaHoc> khoaHocArrayList;

    public KhoaHocRCAdapter(ArrayList<CustomModelKhoaHoc> khoaHocArrayList) {
        this.khoaHocArrayList = khoaHocArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View root=layoutInflater.inflate(R.layout.custom_item_khoahoc,parent,false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        ImageHandler imageHandler = new ImageHandler(holder);
    }

    @Override
    public int getItemCount() {
        return khoaHocArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imvAvatar;
        RatingBar rbBaiDang;
        TextView tvTenNguoiDang;
        TextView tvBuoiHoc;
        TextView tvMonHoc;

        public ViewHolder(View itemView) {
            super(itemView);
            imvAvatar = (ImageView) itemView.findViewById(R.id.img_KhoaHoc);
            rbBaiDang = (RatingBar) itemView.findViewById(R.id.rtb_KhoaHoc);
            tvTenNguoiDang = (TextView) itemView.findViewById(R.id.tvTen_KhoaHoc);
            tvBuoiHoc = (TextView) itemView.findViewById(R.id.tvBuoi_KhoaHoc);
            tvMonHoc = (TextView) itemView.findViewById(R.id.tvMon_KhoaHoc);

        }
    }
}
