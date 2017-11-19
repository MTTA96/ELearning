package com.eways.elearning.Handler.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/9/2017.
 */

public class KhoaHocRCAdapter extends RecyclerView.Adapter<KhoaHocRCAdapter.ViewHolder>  {

    ArrayList<CustomModelKhoaHoc> khoaHocArrayList;
    ImageHandler imageHandler;

    public KhoaHocRCAdapter(ArrayList<CustomModelKhoaHoc> khoaHocArrayList, ImageHandler imageHandler) {
        this.khoaHocArrayList = khoaHocArrayList;
        this.imageHandler = imageHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View root=layoutInflater.inflate(R.layout.custom_item_khoahoc,parent,false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        imageHandler.loadImageSquare(khoaHocArrayList.get(position).LinkAvatar,holder.imvAvatar);

        float rt = Float.parseFloat(khoaHocArrayList.get(position).Rating);
        holder.rtbBaiDang.setRating(rt);

        String ten = "<b>"+holder.tvTenNguoiDang.getText()+"</b>"+" " +khoaHocArrayList.get(position).TenNguoiDang;
        holder.tvTenNguoiDang.setText(Html.fromHtml(ten));

        ArrayList<String> listMon = khoaHocArrayList.get(position).MonHoc;
        String danhSachMon = "";
        for (String mon : listMon) { danhSachMon = danhSachMon + mon; }
        String mon = "<b>"+holder.tvMonHoc.getText()+"</b>"+" " +danhSachMon;
        holder.tvMonHoc.setText(Html.fromHtml(mon));

        String buoi = "<b>"+holder.tvBuoiHoc.getText()+"</b>"+" " +khoaHocArrayList.get(position).BuoiHoc;
        holder.tvBuoiHoc.setText(Html.fromHtml(buoi));

        String hocPhi = "<b>"+holder.tvHocPhi.getText()+"<b>"+" "+khoaHocArrayList.get(position).HocPhi;
        holder.tvHocPhi.setText(Html.fromHtml(hocPhi));
    }

    @Override
    public int getItemCount() {
        return khoaHocArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imvAvatar;
        RatingBar rtbBaiDang;
        TextView tvTenNguoiDang;
        TextView tvBuoiHoc;
        TextView tvMonHoc;
        TextView tvHocPhi;
        public ViewHolder(View itemView) {
            super(itemView);
            imvAvatar = (ImageView) itemView.findViewById(R.id.img_KhoaHoc);
            rtbBaiDang = (RatingBar) itemView.findViewById(R.id.rtb_KhoaHoc);
            tvTenNguoiDang = (TextView) itemView.findViewById(R.id.tvTen_KhoaHoc);
            tvBuoiHoc = (TextView) itemView.findViewById(R.id.tvBuoi_KhoaHoc);
            tvMonHoc = (TextView) itemView.findViewById(R.id.tvMon_KhoaHoc);
            tvHocPhi = (TextView) itemView.findViewById(R.id.tvGia_KhoaHoc);
        }
    }
}
