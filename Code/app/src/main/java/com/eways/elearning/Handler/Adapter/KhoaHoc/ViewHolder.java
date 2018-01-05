package com.eways.elearning.Handler.Adapter.KhoaHoc;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eways.elearning.R;

public class ViewHolder extends RecyclerView.ViewHolder{
    LinearLayout vUserInfo, vCourseInfo;
    ImageView imvAvatar;
    RatingBar rtbBaiDang;
    TextView tvTenNguoiDang;
    TextView tvBuoiHoc;
    TextView tvMonHoc;
    TextView tvHocPhi;
    public ViewHolder(View itemView) {
        super(itemView);
        vUserInfo = (LinearLayout) itemView.findViewById(R.id.view_UserInfo_DanhSachKhoaHoc);
        vCourseInfo = (LinearLayout) itemView.findViewById(R.id.view_CourseInfo_DanhSachKhoaHoc);
        imvAvatar = (ImageView) itemView.findViewById(R.id.img_KhoaHoc);
        rtbBaiDang = (RatingBar) itemView.findViewById(R.id.rtb_KhoaHoc);
        tvTenNguoiDang = (TextView) itemView.findViewById(R.id.tvTen_KhoaHoc);
        tvBuoiHoc = (TextView) itemView.findViewById(R.id.tvBuoi_KhoaHoc);
        tvMonHoc = (TextView) itemView.findViewById(R.id.tvMon_KhoaHoc);
        tvHocPhi = (TextView) itemView.findViewById(R.id.tvGia_KhoaHoc);
    }
}
