package com.eways.elearning.Adapter.Course;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by zzzzz on 5/27/2018.
 */

public class CourseViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout vUserInfo, vCourseInfo;
    public ImageView imgDaiDien;
    public TextView tvTen,tvMon,tvBuoi,tvChiTiet,tvHocPhi;
    public RatingBar rtbDanhGia;

    public CourseViewHolder(View itemView) {
        super(itemView);

        vUserInfo = (LinearLayout) itemView.findViewById(R.id.view_UserInfo_DanhSachKhoaHoc);
        vCourseInfo = (LinearLayout) itemView.findViewById(R.id.view_CourseInfo_DanhSachKhoaHoc);
        imgDaiDien = (ImageView)itemView.findViewById(R.id.img_KhoaHoc);
        tvTen = (TextView)itemView.findViewById(R.id.tvTen_KhoaHoc);
        tvMon = (TextView)itemView.findViewById(R.id.tvMon_KhoaHoc);
        tvBuoi = (TextView)itemView.findViewById(R.id.tvBuoi_KhoaHoc);
        tvChiTiet = (TextView)itemView.findViewById(R.id.tvChiTiet_KhoaHoc);
        tvHocPhi = (TextView)itemView.findViewById(R.id.tvGia_KhoaHoc);
        rtbDanhGia = (RatingBar)itemView.findViewById(R.id.rtb_KhoaHoc);
    }
}
